package com.example.chafersch;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.Query;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private EditText editTextBusqueda;
    private RecyclerView recyclerViewResultados;
    private TextView textViewBusqueda;
    private ZapatillasAdapter adapter;
    private List<Zapatilla> zapatillas = new ArrayList<Zapatilla>();

    private CollectionReference coleccionZapatillas;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Obtener referencia a la colección "Zapatillas" de la base de datos "Chafersch"
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        coleccionZapatillas = db.collection("Zapatillas");

        // Referencias a los elementos de la vista
        editTextBusqueda = view.findViewById(R.id.buscador);
        ImageButton imageButtonBuscar = view.findViewById(R.id.btnSearch);
        recyclerViewResultados = view.findViewById(R.id.rvResultados);
        textViewBusqueda = view.findViewById(R.id.busqueda);

        // Configuración del RecyclerView y el adaptador
        recyclerViewResultados.setLayoutManager(new LinearLayoutManager(getContext()));
        Object Zapatillas = new Object();


        // Acción al hacer clic en el botón de búsqueda
        imageButtonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Obtener el texto de búsqueda
                    String textoBusqueda = editTextBusqueda.getText().toString();

                    // Realizar la consulta a la base de datos
                    Query query = coleccionZapatillas.whereGreaterThanOrEqualTo("Marca", textoBusqueda);//whereGreaterThanOrEqualTo te muestra todos los que contenga la palabra
                    query.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            zapatillas.clear(); // Limpiar los resultados anteriores
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Zapatilla zapatilla = new Zapatilla(document.getString("Codigo"), document.getString("Foto"), document.getString("Id_Categoria"),document.getString("Marca"),document.getString("Nombre"),document.getBoolean("Oferta") ,document.getString("Precio"), document.getString("Stock"), document.getString("Talla"));
                                zapatillas.add(zapatilla); // Agregar los nuevos elementos a la lista

                            }

                            adapter = new ZapatillasAdapter(zapatillas, getContext());
                            recyclerViewResultados.setAdapter(adapter);
                            adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                        }

                    });

                    // Mostrar el texto "Buscado recientemente:"
                    textViewBusqueda.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.e("SearchFragment", "Error: " + e.getMessage());
                }
            }
        });

        return view;
    }
}