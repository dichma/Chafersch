package com.example.chafersch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragment extends Fragment {

    private List<Pedido> pedidoList = new ArrayList<>();
    private FirebaseFirestore db;
    private AdapterCarrito adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterCarrito(getContext(), pedidoList);
        recyclerView.setAdapter(adapter);

        // Inicializar la referencia a la base de datos de Firebase
        db = FirebaseFirestore.getInstance();

        // Obtener todas las zapatillas de la colección "Zapatillas" con la categoría "Deporte"
        db.collection("Pedido")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String marca = document.getString("marca");
                            String nombre = document.getString("nombre");
                            String precio = document.getString("precio");
                            String urlImagen = document.getString("foto");
                            String talla = document.getString("talla");
                            String cantidad = document.getString("cantidad");
                            Pedido pedido = new Pedido(marca, nombre, precio, urlImagen, talla, cantidad);
                            pedidoList.add(pedido);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Manejar el error aquí
                        getActivity().finish();
                    }
                });

        return view;
    }
}
