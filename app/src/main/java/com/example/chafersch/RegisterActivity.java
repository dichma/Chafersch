package com.example.chafersch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombre, etApellidos, etCorreo, etUsuario, etContraseña;
    private Button btnRegistrar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        // Inicializar Firebase Authentication y Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etNombre = findViewById(R.id.nombre);
        etApellidos = findViewById(R.id.apellido);
        etCorreo = findViewById(R.id.correo);
        etUsuario = findViewById(R.id.usuario);
        etContraseña = findViewById(R.id.contrasena);
        btnRegistrar = findViewById(R.id.registrarse_registro);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        final String nombre = etNombre.getText().toString().trim();
        final String apellidos = etApellidos.getText().toString().trim();
        final String correo = etCorreo.getText().toString().trim();
        final String usuario = etUsuario.getText().toString().trim();
        String contraseña = etContraseña.getText().toString().trim();

        // Validar que todos los campos estén llenos
        if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || usuario.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos", Toast.LENGTH_LONG).show();
        } else {
            // Crear usuario en Firebase Authentication
            mAuth.createUserWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Obtener ID de usuario generado por Firebase Authentication
                                String userId = mAuth.getCurrentUser().getUid();

                                // Crear un mapa con los datos del usuario
                                Map<String, Object> usuarioMap = new HashMap<>();
                                usuarioMap.put("nombre", nombre);
                                usuarioMap.put("apellidos", apellidos);
                                usuarioMap.put("correo", correo);
                                usuarioMap.put("usuario", usuario);

                                // Guardar datos del usuario en Firestore
                                db.collection("users").document(correo)
                                        .set(usuarioMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente", Toast.LENGTH_LONG).show();
                                                    // Redirigir a la pantalla de inicio de sesión
                                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Error al registrar el usuario en Firestore", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                            } else {
                                Toast.makeText(getApplicationContext(), "Error al registrar el usuario en Firebase Authentication", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            }
        }


    }
