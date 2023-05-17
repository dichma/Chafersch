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

public class AdapterCarrito extends RecyclerView.Adapter<AdapterCarrito.ImageViewHolder> {

    private Context mContext;
    private List<Pedido> mPedidoList;

    public AdapterCarrito(Context context, List<Pedido> pedidoList) {
        mContext = context;
        mPedidoList = pedidoList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.carrito_layout_imagenes, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Pedido pedido = mPedidoList.get(position);
        holder.MarcaCarrito.setText(pedido.getMarca());
        holder.NombreCarrito.setText(pedido.getNombre());
        holder.PrecioCarrito.setText(pedido.getPrecio());
        // Obtener la URL de la imagen de la zapatilla
        Uri imgUri = Uri.parse(pedido.getFoto());

        // Cargar la imagen en el ImageView utilizando Picasso
        Picasso.get()
                .load(imgUri)
                .into(holder.ImagenCarrito);

        // Agrega el evento de clic al botón
        holder.btnCarrito.setOnClickListener(new View.OnClickListener() {
            private FirebaseFirestore db = FirebaseFirestore.getInstance();
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Paso1CarritoActivity.class);
                intent.putExtra("marca", pedido.getMarca());
                intent.putExtra("nombre", pedido.getNombre());
                intent.putExtra("precio", pedido.getPrecio());
                intent.putExtra("talla", pedido.getTalla());
                intent.putExtra("cantidad", pedido.getCantidad());
                intent.putExtra("foto", pedido.getFoto());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mPedidoList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView MarcaCarrito;
        public TextView NombreCarrito;
        public TextView PrecioCarrito;
        public ImageView ImagenCarrito;
        public Button btnCarrito;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            MarcaCarrito = itemView.findViewById(R.id.marca_carrito);
            NombreCarrito = itemView.findViewById(R.id.nombre_carrito);
            PrecioCarrito = itemView.findViewById(R.id.precio_carrito);
            ImagenCarrito = itemView.findViewById(R.id.imagenCarrito);
            btnCarrito = itemView.findViewById(R.id.btnCarrito);
        }
    }
}
