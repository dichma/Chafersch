package com.example.chafersch;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZapatillasAdapter extends RecyclerView.Adapter<ZapatillasAdapter.ZapatillaViewHolder> {

    // Lista de zapatillas que se mostrarán en el RecyclerView
    private static List<Zapatilla> ZapatillasList;

    // Instancia de la base de datos Firestore
    private FirebaseFirestore db;

    // Contexto de la actividad que usa el adaptador
    private static Context context;

    // Constructor del adaptador
    public ZapatillasAdapter(List<Zapatilla> ZapatillasList, Context context) {
        this.ZapatillasList = ZapatillasList;
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    // Método que crea las vistas del RecyclerView
    @NonNull
    @Override
    public ZapatillaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout de cada elemento del RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, parent, false);
        // Retornar una instancia del ViewHolder con la vista inflada
        return new ZapatillaViewHolder(view);
    }

    // Método que asocia los datos de cada zapatilla con las vistas del ViewHolder correspondiente
    @Override
    public void onBindViewHolder(@NonNull ZapatillaViewHolder holder, int position) {

        // Obtener la zapatilla de la posición actual
        Zapatilla zapatilla = ZapatillasList.get(position);

        Log.i("hola33"+"zapatillanombre", zapatilla.getNombre());

        // Mostrar la marca, el nombre y precio de la zapatilla
        holder.marcaTextView.setText(zapatilla.getMarca());
        holder.nombreTextView.setText(zapatilla.getNombre());
        holder.precioTextView.setText(zapatilla.getPrecio());

        // Obtener la URL de la imagen de la zapatilla
        Uri imgUri = Uri.parse(zapatilla.getFoto());

        // Cargar la imagen en el ImageView utilizando Glide
        Glide.with(holder.imagenView.getContext())
                .load(imgUri)
                .into(holder.imagenView);

    }

    // Método que retorna la cantidad de elementos que hay en la lista de zapatillas
    @Override
    public int getItemCount() {

        return ZapatillasList.size();
    }

    // Método para filtrar la lista de zapatillas
    public void filterList(ArrayList<Zapatilla> filteredList) {
        ZapatillasList = filteredList;
        notifyDataSetChanged();
    }

    // Clase interna que representa el ViewHolder de cada elemento del RecyclerView
    public static class ZapatillaViewHolder extends RecyclerView.ViewHolder {
        // Vistas que se mostrarán en cada elemento del RecyclerView
        TextView marcaTextView;
        TextView nombreTextView;
        TextView precioTextView;
        ImageView imagenView;
        RelativeLayout relativeClick;

        Button buttonSearch;

        // Constructor del ViewHolder
        public ZapatillaViewHolder(@NonNull View itemView) {

            super(itemView);
            marcaTextView = itemView.findViewById(R.id.marca_zapato);
            nombreTextView = itemView.findViewById(R.id.nombre_zapato);
            precioTextView = itemView.findViewById(R.id.precio_zapato);
            imagenView = itemView.findViewById(R.id.imagen_zapato);
            buttonSearch = itemView.findViewById(R.id.btnBuscador);

            buttonSearch.setOnClickListener(new View.OnClickListener() {
                private FirebaseFirestore db = FirebaseFirestore.getInstance();
                @Override
                public void onClick(View v) {

                    int posicion = getAdapterPosition();

                    Zapatilla zapatilla = ZapatillasList.get(posicion);

                    //hacemos click en la imagen y vamos a otra pagina
                    Intent intent = new Intent(context, InfoProducto.class);
                    intent.putExtra("marca", zapatilla.getMarca());
                    intent.putExtra("nombre", zapatilla.getNombre());
                    intent.putExtra("precio", zapatilla.getPrecio());
                    intent.putExtra("foto", zapatilla.getFoto());
                    context.startActivity(intent);
                }
            });
        }
    }
}

