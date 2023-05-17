package com.example.chafersch;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InfoProducto extends AppCompatActivity {

    private boolean isFavorite = false;
    private ImageButton favVacioButton;
    private ImageButton favRellenoButton;
    private String TAG = "";
    private TextView marcaTextView;
    private TextView nombreTextView;
    private ImageView ZapatillaImageView;
    private TextView precioTextView;
    private FirebaseFirestore db;

    private int cantidadSeleccionada = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.info_producto_layout);

            db = FirebaseFirestore.getInstance();

            // Mostrar la información de la zapatilla seleccionada en los componentes de la interfaz de usuario
            marcaTextView = findViewById(R.id.marcaTextView);
            nombreTextView = findViewById(R.id.nombreTextView);
            ZapatillaImageView = findViewById(R.id.producto_delante);
            precioTextView = findViewById(R.id.precioTextView);
            RadioGroup radioGroup = findViewById(R.id.radioGroup);
            Spinner spinner_pedido = findViewById(R.id.cantidadSpinner);

            // Inicializar la referencia a la base de datos de Firebase
            db = FirebaseFirestore.getInstance();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                String email = currentUser.getEmail();
                // Obtiene una instancia de la base de datos Firestore
                db = FirebaseFirestore.getInstance();

                TextView mostrar_usuario = findViewById(R.id.m_usuario);

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "DocumentSnapshot successfully written!" + user);
                Log.d(TAG, "DocumentSnapshot successfully written!" + email);

                DocumentReference docRef = db.collection("users").document(email);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                mostrar_usuario.setText(document.get("usuario").toString());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            } else {
                // Si el usuario no ha iniciado sesión, iniciar la actividad LoginActivity
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish(); // asegurarse de que el usuario no pueda volver a MainActivity sin iniciar sesión
            }

            // Convertir el precio a un valor numérico
            String precioString = getIntent().getStringExtra("precio");
            String precioSinEuro = precioString.replaceAll("[^\\d,.-]", ""); // Quits all characters except numbers, comma, dot and minus sign.
            double precio = Double.parseDouble(precioSinEuro.replace(",", ".")); // Replaces comma with dot and parses to double.

            spinner_pedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    cantidadSeleccionada = Integer.parseInt(adapterView.getSelectedItem().toString());
                    double precioTotal = precio * cantidadSeleccionada;
                    precioTextView.setText(String.format("%.2f", precioTotal) + "€");
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    cantidadSeleccionada = 1;
                    double precioTotal = precio * cantidadSeleccionada;
                    precioTextView.setText(String.format("%.2f", precioTotal) + "€");
                }
            });
            // Actualiza los campos en la interfaz de usuario con la información de la zapatilla seleccionada
            marcaTextView.setText(getIntent().getStringExtra("marca"));
            nombreTextView.setText(getIntent().getStringExtra("nombre"));
            precioTextView.setText(getIntent().getStringExtra("precio"));
            // Suponiendo que "producto_delante" es un ImageView que muestra la foto de la zapatilla
            Picasso.get().load(getIntent().getStringExtra("foto")).into(ZapatillaImageView);

            Button agregarButton = findViewById(R.id.agregarButton);
            agregarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Aquí irá el código para guardar los valores
                    String nombre =nombreTextView.getText().toString() ;
                    String marca = marcaTextView.getText().toString();
                    String precio = precioTextView.getText().toString();
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(selectedId);
                    String talla = radioButton.getText().toString();
                    String urlImagen = getIntent().getStringExtra("foto");

                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(InfoProducto.this, R.array.cantidad_array, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_pedido.setAdapter(adapter);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Picasso.get().load(urlImagen).get();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Picasso.get().load(urlImagen).get();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    String cantidad = spinner_pedido.getSelectedItem().toString();

                    Pedido pedido = new Pedido(marca, nombre, precio, urlImagen, talla, String.valueOf(cantidadSeleccionada));

                    CollectionReference pedidosRef = db.collection("Pedido");
                    Map<String, Object> Pedido = new HashMap<>();
                    Pedido.put("cantidad", pedido.getCantidad());
                    Pedido.put("foto", pedido.getFoto());
                    Pedido.put("marca", pedido.getMarca());
                    Pedido.put("nombre", pedido.getNombre());
                    Pedido.put("precio", pedido.getPrecio());
                    Pedido.put("talla", pedido.getTalla());



                    pedidosRef.add(Pedido)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                // El objeto se guardó exitosamente
                                    Toast.makeText(InfoProducto.this, "El producto se ha añadido al carrito correctamente", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Ocurrió un error al guardar el objeto
                                    Toast.makeText(InfoProducto.this, "Se ha producido un error al añadir el producto al carrito", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

            favVacioButton = findViewById(R.id.fav_vacio);
            favRellenoButton = findViewById(R.id.fav_relleno);

            favVacioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFavorite = !isFavorite;
                    favVacioButton.setVisibility(View.GONE);
                    favRellenoButton.setVisibility(View.VISIBLE);

                    String marca = marcaTextView.getText().toString();
                    String nombre = nombreTextView.getText().toString();
                    String precio = precioTextView.getText().toString();
                    String urlImagen = getIntent().getStringExtra("foto");

                    Favoritos favoritos = new Favoritos(marca, nombre, precio, urlImagen);

                    CollectionReference pedidosRef = db.collection("Favoritos");
                    Map<String, Object> Favoritos = new HashMap<>();
                    Favoritos.put("foto", favoritos.getFoto());
                    Favoritos.put("marca", favoritos.getMarca());
                    Favoritos.put("nombre", favoritos.getNombre());
                    Favoritos.put("precio", favoritos.getPrecio());
                    pedidosRef.add(Favoritos)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    // El objeto se guardó exitosamente
                                    Toast.makeText(InfoProducto.this, "El producto se ha añadido a favoritos correctamente", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Ocurrió un error al guardar el objeto
                                    Toast.makeText(InfoProducto.this, "El producto no se ha podido añadir a favoritos", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            });
            favRellenoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFavorite = !isFavorite;
                    favVacioButton.setVisibility(View.VISIBLE);
                    favRellenoButton.setVisibility(View.GONE);
                }
            });
        }
    }