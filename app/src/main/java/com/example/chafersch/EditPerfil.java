package com.example.chafersch;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditPerfil extends AppCompatActivity {
    private EditText mEdtxtUser;
    private EditText mEdtxtCorreo;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private EditText edtxtUser;
    private EditText edtxtCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_perfil);

        // Obtener referencias a los EditText
        edtxtCorreo = findViewById(R.id.edtxt_correo);
        edtxtUser = findViewById(R.id.edtxt_user);

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

        // Obtener el usuario actual de la base de datos Chafesch
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Si el usuario está logeado, establecer su correo electrónico en el EditText correspondiente
            edtxtCorreo.setText(user.getEmail());

            // Recuperar el documento del usuario actual mediante su ID
            db.collection("users").document(user.getEmail()).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Si el documento existe, obtener el valor del campo "usuario" y establecerlo en el EditText correspondiente
                            String username = documentSnapshot.getString("usuario");
                            edtxtUser.setText(username);
                        }
                    })
                    .addOnFailureListener(e -> Log.e(TAG, "Error al recuperar el documento del usuario: " + e));
        }

        // Obtener referencia al botón de "Guardar cambios"
        Button btnGuardarCambios = findViewById(R.id.guardar_cambios);

        // Establecer un listener para el botón de "Guardar cambios"
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos valores de los EditText
                String nuevoUsername = edtxtUser.getText().toString();
                String nuevoCorreo = edtxtCorreo.getText().toString();

                // Crear un mapa con los nuevos valores
                Map<String, Object> DatosActualizados = new HashMap<>();
                DatosActualizados.put("usuario", nuevoUsername);
                DatosActualizados.put("correo", nuevoCorreo);

                // Actualizar los datos del usuario en Firestore
                db.collection("users").document(user.getEmail())
                        .update(DatosActualizados)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Datos actualizados correctamente", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error al actualizar los datos del usuario: " + e);
                                Toast.makeText(getApplicationContext(), "Error al actualizar los datos del usuario", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        Button logoutButton = findViewById(R.id.cerrar_sesion);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(EditPerfil.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}