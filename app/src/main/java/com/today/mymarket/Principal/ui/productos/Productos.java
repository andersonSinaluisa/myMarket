package com.today.mymarket.Principal.ui.productos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.today.mymarket.AdapterList.AdapterProducto;
import com.today.mymarket.DB.Compras;
import com.today.mymarket.DB.Producto;
import com.today.mymarket.Principal.ui.gallery.GalleryFragment;
import com.today.mymarket.R;

import java.util.ArrayList;
import java.util.List;

import static com.today.mymarket.DB.Preference.id_tienda;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Productos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Productos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView listView;
    private List<Producto> list = new ArrayList<Producto>();
    private AdapterProducto myadapter;
    private String mParam1;
    private String mParam2;


    public Productos() {
        // Required empty public constructor
    }


    public static Productos newInstance(String param1, String param2) {
        Productos fragment = new Productos();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_productos, container, false);


        listView = (ListView) view.findViewById(R.id.g_productos);


        FloatingActionButton fab = view.findViewById(R.id.fab_productos);

        ListarProductos(id_tienda(view.getContext()));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NuevoProducto.class);
                startActivity(i);

            }
        });



        return view;

    }

    private void ListarProductos(final String id_tienda) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("productos").orderByChild("id_tienda").equalTo(id_tienda);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();
                    for (DataSnapshot obj : dataSnapshot.getChildren()){


                        Producto p = obj.getValue(Producto.class);

                        assert p != null;
                        if(p.getId_tienda().equals(id_tienda)) {

                            list.add(p);
                        }

                        myadapter = new AdapterProducto(getContext(),list);
                        listView.setAdapter(myadapter);
                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
