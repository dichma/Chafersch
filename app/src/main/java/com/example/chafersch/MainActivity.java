package com.example.chafersch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.chafersch.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseFirestore db;

    private CollectionReference Zapatillas;

    private String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);

        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        ordenarBotonesNavigation();

        // Inicializa Firebase
        FirebaseApp.initializeApp(this);

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

        // Obtiene una referencia a la colección "Zapatillas"
        Zapatillas = db.collection("Zapatillas");

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){

                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.search:
                    replaceFragment((new SearchFragment()));
                    break;
                case R.id.favorite:
                    replaceFragment((new FavoriteFragment()));
                    break;
                case R.id.shop:
                    replaceFragment((new ShoppingFragment()));
                    break;
            }
            return true;
        });
    }


    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void ordenarBotonesNavigation() {
        //Botón NOVEDADES
        Button btnNovedades = findViewById(R.id.nav_novedades);
        btnNovedades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NovedadesActivity.class);
                startActivity(intent);
            }
        });

        //Botón OFERTAS
        Button btnOfertas = findViewById(R.id.nav_ofertas);
        btnOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OfertasActivity.class);
                startActivity(intent);
            }
        });

        //Botón DEPORTE
        Button btnDeporte = findViewById(R.id.nav_deporte);
        btnDeporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeporteActivity.class);
                startActivity(intent);
            }
        });

        //Botón SANDALIAS
        Button btnSandalias = findViewById(R.id.nav_sandalias);
        btnSandalias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SandaliasActivity.class);
                startActivity(intent);
            }
        });

        //Botón LOG OUT
        ImageButton btnPerfil = findViewById(R.id.nav_perfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditPerfil.class);
                startActivity(intent);
            }
        });
    }
}