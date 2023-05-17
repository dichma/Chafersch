package com.example.chafersch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ZapatillaViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView textViewCodigo;
    private TextView textviewCategoria;
    private TextView textViewNombre;
    private TextView textViewPrecio;
    private TextView textViewStock;
    private TextView textViewTalla;


    public ZapatillaViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        textViewCodigo = itemView.findViewById(R.id.textViewCodigo);
        textviewCategoria = itemView.findViewById(R.id.textviewCategoria) ;
        textViewNombre = itemView.findViewById(R.id.textViewNombre);
        textViewPrecio = itemView.findViewById(R.id.textViewPrecio);
        textViewStock = itemView.findViewById(R.id.textViewStock);
        textViewTalla = itemView.findViewById(R.id.textViewTalla);
    }

    public void bind(Zapatilla zapatilla) {
        textViewCodigo.setText(zapatilla.getCodigo());
        textViewNombre.setText(zapatilla.getNombre());
        textViewPrecio.setText(zapatilla.getPrecio());
        textViewStock.setText(zapatilla.getStock());
        textViewTalla.setText(zapatilla.getTalla());
    }
}
