package com.today.mymarket.Principal.ui.proveedores;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.mymarket.Activities.NuevoProducto;
import com.today.mymarket.Activities.NuevoProveedor;
import com.today.mymarket.AdapterList.AdapterProveedor;
import com.today.mymarket.DB.Proveedor;
import com.today.mymarket.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.today.mymarket.DB.Preference.id_tienda;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Proveedores#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Proveedores extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listView;
    private TextView textView;
    private List<Proveedor> list = new ArrayList<Proveedor>() ;
    private AdapterProveedor arrayAdapter;
    private String mParam1;
    private String mParam2;

    public Proveedores() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Proveedores.
     */
    // TODO: Rename and change types and number of parameters
    public static Proveedores newInstance(String param1, String param2) {
        Proveedores fragment = new Proveedores();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_proveedores, container, false);
        // Inflate the layout for this fragment

        textView = (TextView) root.findViewById(R.id.tv_proveedores);
        listView=(ListView) root.findViewById(R.id.l_proveedores);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab_n_proveedor);

        listarProveedores(id_tienda(root.getContext()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(root.getContext(), NuevoProveedor.class);
                startActivity(i);
            }
        });



        return root;
    }
    private void listarProveedores(final String id_tienda) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("proveedores");

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Proveedor p = obj.getValue(Proveedor.class);

                        if(p.getId_tienda().equals(id_tienda)) {

                            list.add(p);
                        }
                        arrayAdapter = new AdapterProveedor(list,getContext());
                        listView.setAdapter(arrayAdapter);
                    }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
