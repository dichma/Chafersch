package com.example.chafersch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private List<Favoritos> favoriteList = new ArrayList<>();
    private FirebaseFirestore db;
    private AdapterFavorite adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewFavoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterFavorite(getContext(), favoriteList);
        recyclerView.setAdapter(adapter);

        // Inicializar la referencia a la base de datos de Firebase
        db = FirebaseFirestore.getInstance();

        db.collection("Favoritos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String marca = document.getString("marca");
                            String nombre = document.getString("nombre");
                            String precio = document.getString("precio");
                            String urlImagen = document.getString("foto");
                            Favoritos favoritos = new Favoritos(marca, nombre, precio, urlImagen);
                            favoriteList.add(favoritos);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Manejar el error aquí
                        Toast.makeText(getContext(), "No se pudo obtener la lista de favoritos.", Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }
}
