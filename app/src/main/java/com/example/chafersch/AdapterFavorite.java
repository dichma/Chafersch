package com.example.chafersch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.ImageViewHolder> {

    private Context mContext;
    private List<Favoritos> mFavoritosList;

    public AdapterFavorite(Context context, List<Favoritos> favoritosList) {
        mContext = context;
        mFavoritosList = favoritosList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.favorite_layout_imagenes, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Favoritos favoritos = mFavoritosList.get(position);
        holder.MarcaFavorito.setText(favoritos.getMarca());
        holder.NombreFavorito.setText(favoritos.getNombre());
        holder.PrecioFavorito.setText(favoritos.getPrecio());
        // Obtener la URL de la imagen de la zapatilla
        Uri imgUri = Uri.parse(favoritos.getFoto());

        // Cargar la imagen en el ImageView utilizando Picasso
        Picasso.get()
                .load(imgUri)
                .into(holder.imagenFavorito);

        // Agrega el evento de clic al botón
        holder.btnFavorito.setOnClickListener(new View.OnClickListener() {
            private FirebaseFirestore db = FirebaseFirestore.getInstance();
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InfoProducto.class);
                intent.putExtra("marca", favoritos.getMarca());
                intent.putExtra("nombre", favoritos.getNombre());
                intent.putExtra("precio", favoritos.getPrecio());
                intent.putExtra("foto", favoritos.getFoto());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mFavoritosList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView MarcaFavorito;
        public TextView NombreFavorito;
        public TextView PrecioFavorito;
        public ImageView imagenFavorito;
        public Button btnFavorito;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            MarcaFavorito = itemView.findViewById(R.id.marca_favorito);
            NombreFavorito = itemView.findViewById(R.id.nombre_favorito);
            PrecioFavorito = itemView.findViewById(R.id.precio_favorito);
            imagenFavorito = itemView.findViewById(R.id.imagenFavorito);
            btnFavorito = itemView.findViewById(R.id.btnFavorito);
        }
    }
}
