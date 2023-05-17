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

    public class AdapterDeportes extends RecyclerView.Adapter<AdapterDeportes.ImageViewHolder> {

        private Context mContext;
        private List<Zapatilla> mZapatillaList;

        public AdapterDeportes(Context context, List<Zapatilla> zapatillaList) {
            mContext = context;
            mZapatillaList = zapatillaList;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.deporte_layout_imagenes, parent, false);
            return new ImageViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            Zapatilla zapatilla = mZapatillaList.get(position);
            holder.MarcaDeporte.setText(zapatilla.getMarca());
            holder.NombreDeporte.setText(zapatilla.getNombre());
            holder.PrecioDeporte.setText(zapatilla.getPrecio());
            // Obtener la URL de la imagen de la zapatilla
            Uri imgUri = Uri.parse(zapatilla.getFoto());

            // Cargar la imagen en el ImageView utilizando Picasso
            Picasso.get()
                    .load(imgUri)
                    .into(holder.imagenDeporte);

            // Agrega el evento de clic al botón
            holder.btnDeporte.setOnClickListener(new View.OnClickListener() {
                private FirebaseFirestore db = FirebaseFirestore.getInstance();
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, InfoProducto.class);
                    intent.putExtra("marca", zapatilla.getMarca());
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
            public TextView MarcaDeporte;
            public TextView NombreDeporte;
            public TextView PrecioDeporte;
            public ImageView imagenDeporte;
            public Button btnDeporte;

            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);
                MarcaDeporte = itemView.findViewById(R.id.marca_deporte);
                NombreDeporte = itemView.findViewById(R.id.nombre_deporte);
                PrecioDeporte = itemView.findViewById(R.id.precio_deporte);
                imagenDeporte = itemView.findViewById(R.id.imagenDeportes);
                btnDeporte = itemView.findViewById(R.id.btnDeporte);
            }
        }
    }
