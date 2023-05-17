package com.example.chafersch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Paso3CarritoActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private String TAG = "";

    // Variables para guardar los valores seleccionados
    private String metodoEnvioSeleccionado = "";
    private String metodoPagoSeleccionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paso3_carrito);

        // Inicializar la referencia a la base de datos de Firebase
        db = FirebaseFirestore.getInstance();

        // Obtener los datos enviados desde la actividad anterior
        Bundle bundle = getIntent().getExtras();
            String nombre = bundle.getString("nombre");
            String apellidos = bundle.getString("apellidos");
            String email = bundle.getString("email");
            String nif = bundle.getString("nif");
            String direccion = bundle.getString("direccion");
            String codigo_postal = bundle.getString("codigo_postal");
            String pais = bundle.getString("pais");
            String telefono = bundle.getString("telefono");

        // Obtener referencia a los RadioGroups y agregar oyentes para detectar la selección
        RadioGroup metodosEnvio = findViewById(R.id.radio_group_metodos_envio);
        metodosEnvio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_seur:
                        metodoEnvioSeleccionado = "SEUR";
                        break;
                    case R.id.radio_button_mrw:
                        metodoEnvioSeleccionado = "MRW";
                        break;
                }
            }
        });

        RadioGroup metodosPago = findViewById(R.id.radio_group_metodos_pago);
        metodosPago.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_visa:
                        metodoPagoSeleccionada = "Visa";
                        break;
                    case R.id.radio_button_paypal:
                        metodoPagoSeleccionada = "PayPal";
                        break;
                }
            }
        });
        Button btnCompra = findViewById(R.id.btn_compra);
        btnCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí va el código que se ejecutará cuando se haga clic en el botón
                // Obtener instancia de FirebaseFirestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                int selectedId = metodosEnvio.getCheckedRadioButtonId();
                int selectedId1 = metodosPago.getCheckedRadioButtonId();
                RadioButton radioButtonEnvio = findViewById(selectedId);
                String metodoEnvio = radioButtonEnvio.getText().toString();
                RadioButton radioButtonPago = findViewById(selectedId1);
                String metodoPago = radioButtonPago.getText().toString();

                Pago pago = new Pago(nombre, apellidos,email, nif, direccion, codigo_postal, pais, telefono, metodoEnvio, metodoPago);

                CollectionReference pedidosRef = db.collection("Pago").document().getParent();
                Map<String, Object> Pago = new HashMap<>();
                Pago.put("nombre", pago.getNombre_pago());
                Pago.put("apellidos", pago.getApellidos_pago());
                Pago.put("email", pago.getCorreo_pago());
                Pago.put("nif", pago.getNif_pago());
                Pago.put("direccion", pago.getDireccion_pago());
                Pago.put("codigo_postal", pago.getCodigo_postal_pago());
                Pago.put("pais", pago.getPais_pago());
                Pago.put("telefono", pago.getTelefono_pago());
                Pago.put("metodo_envio", pago.getMetodo_envio());
                Pago.put("metodo_pago", pago.getMetodo_pago());
                pedidosRef.add(Pago)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // El objeto se guardó exitosamente
                                Toast.makeText(Paso3CarritoActivity.this, "Tu pedido se ha realizado correctamente", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Ocurrió un error al guardar el objeto
                                Toast.makeText(Paso3CarritoActivity.this, "Ha habido un error al realizar tu pedido", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
