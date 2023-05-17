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

public class AdapterNovedades extends RecyclerView.Adapter<AdapterNovedades.ImageViewHolder> {

    private Context mContext;
    private List<Zapatilla> mZapatillaList;

    public AdapterNovedades(Context context, List<Zapatilla> zapatillaList) {
        mContext = context;
        mZapatillaList = zapatillaList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.novedades_layout_imagenes, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Zapatilla zapatilla = mZapatillaList.get(position);
        holder.MarcaNovedades.setText(zapatilla.getMarca());
        holder.NombreNovedades.setText(zapatilla.getNombre());
        holder.PrecioNovedades.setText(zapatilla.getPrecio());
        // Obtener la URL de la imagen de la zapatilla
        Uri imgUri = Uri.parse(zapatilla.getFoto());

        // Cargar la imagen en el ImageView utilizando Picasso
        Picasso.get()
                .load(imgUri)
                .into(holder.imagenNovedades);

        // Agrega el evento de clic al botón
        holder.btnNovedades.setOnClickListener(new View.OnClickListener() {
            private FirebaseFirestore db = FirebaseFirestore.getInstance();
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InfoProducto.class);
                intent.putExtra("nombre", zapatilla.getNombre());
                intent.putExtra("precio", zapatilla.getPrecio());
                intent.putExtra("foto", zapatilla.getFoto());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mZapatillaList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView MarcaNovedades;
        public TextView NombreNovedades;
        public TextView PrecioNovedades;
        public ImageView imagenNovedades;
        public Button btnNovedades;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            MarcaNovedades = itemView.findViewById(R.id.marca_novedades);
            NombreNovedades = itemView.findViewById(R.id.nombre_novedades);
            PrecioNovedades = itemView.findViewById(R.id.precio_novedades);
            imagenNovedades = itemView.findViewById(R.id.imagenNovedades);
            btnNovedades = itemView.findViewById(R.id.btnNovedades);
        }
    }
}
