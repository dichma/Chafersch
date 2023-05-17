package com.example.chafersch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

public class DeporteActivity extends AppCompatActivity {

    // Referencia a la base de datos de Firebase
    private FirebaseFirestore db;

    private String TAG = "";
    // Lista para almacenar todas las zapatillas
    private List<Zapatilla> zapatillaList = new ArrayList<>();

    // Adaptador para cargar las zapatillas en la vista de lista
    private AdapterDeportes adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deporte_layout);

        // Obtener la referencia del RecyclerView y establecer el adaptador
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewDeporte);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterDeportes(this, zapatillaList);
        recyclerView.setAdapter(adapter);

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
        // Obtener todas las zapatillas de la colección "Zapatillas" con la categoría "Deporte"
        db.collection("Zapatillas")
                .whereEqualTo("Id_Categoria", "Deporte")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String nombre = document.getString("Nombre");
                            String precio = document.getString("Precio");
                            String foto = document.getString("Foto");
                            String marca = document.getString("Marca");
                            Zapatilla zapatilla = new Zapatilla(nombre, precio, foto, marca);
                            zapatillaList.add(zapatilla);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Manejar el error aquí
                        finish();
                    }
                });
    }
}
