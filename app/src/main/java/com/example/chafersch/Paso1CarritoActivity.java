package com.example.chafersch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Paso1CarritoActivity extends AppCompatActivity {

    // Referencia a la base de datos de Firebase
    private FirebaseFirestore db;

    private String TAG = "";

    // Adaptador para cargar las zapatillas en la vista de lista

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paso1_carrito);

        // Mostrar la información de la zapatilla seleccionada en los componentes de la interfaz de usuario
        TextView marcaTextView = findViewById(R.id.txt_marca);
        TextView nombreTextView = findViewById(R.id.txt_nombre);
        TextView precioTextView = findViewById(R.id.txt_precio);
        TextView tallaTextView = findViewById(R.id.txt_talla);
        TextView cantidadTextView = findViewById(R.id.txt_cantidad);
        ImageView PedidoImageView = findViewById(R.id.imagen_pedido);
        Button btnSiguiente = findViewById(R.id.btnSiguiente);


        // Actualiza los campos en la interfaz de usuario con la información de la zapatilla seleccionada
        marcaTextView.setText(getIntent().getStringExtra("marca"));
        nombreTextView.setText(getIntent().getStringExtra("nombre"));
        precioTextView.setText(getIntent().getStringExtra("precio"));
        tallaTextView.setText(getIntent().getStringExtra("talla"));
        cantidadTextView.setText(getIntent().getStringExtra("cantidad"));
        // Suponiendo que "producto_delante" es un ImageView que muestra la foto de la zapatilla
        Picasso.get().load(getIntent().getStringExtra("foto")).into(PedidoImageView);

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

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            private FirebaseFirestore db = FirebaseFirestore.getInstance();
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Paso1CarritoActivity.this, Paso2CarritoActivity.class);
                startActivity(intent);
            }
        });
    }
}
