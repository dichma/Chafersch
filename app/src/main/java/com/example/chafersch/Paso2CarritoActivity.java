package com.example.chafersch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Paso2CarritoActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextEmail;

    private EditText editTextNif;
    private EditText editTextDireccion;

    private EditText editTextCodigoPostal;
    private EditText editTextPais;
    private EditText editTextTelefono;
    private FirebaseFirestore db;
    private String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paso2_carrito);

        Button btnSiguiente = findViewById(R.id.btn_Siguiente);

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

            // Obtener referencias a los componentes del formulario
            editTextNombre = findViewById(R.id.f_nombre);
            editTextApellidos = findViewById(R.id.f_apellidos);
            editTextEmail = findViewById(R.id.edtxt_email);
            editTextNif = findViewById(R.id.edtxt_nif);
            editTextDireccion = findViewById(R.id.f_direccion_envio);
            editTextCodigoPostal = findViewById(R.id.f_codigo_postal);
            editTextPais = findViewById(R.id.f_pais);
            editTextTelefono = findViewById(R.id.f_telefono);

            // Configurar el botón de envío del formulario
            Button buttonSiguiente = findViewById(R.id.btn_Siguiente);
            buttonSiguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombre = editTextNombre.getText().toString();
                    String apellidos = editTextApellidos.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String nif = editTextNif.getText().toString();
                    String direccion = editTextDireccion.getText().toString();
                    String codigoPostal = editTextCodigoPostal.getText().toString();
                    String pais = editTextPais.getText().toString();
                    String telefono = editTextTelefono.getText().toString();

                    Intent intent = new Intent(Paso2CarritoActivity.this, Paso3CarritoActivity.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("apellidos", apellidos);
                    intent.putExtra("email", email);
                    intent.putExtra("nif", nif);
                    intent.putExtra("direccion", direccion);
                    intent.putExtra("codigoPostal", codigoPostal);
                    intent.putExtra("pais", pais);
                    intent.putExtra("telefono", telefono);
                    startActivity(intent);

                }
            });
    }
}
