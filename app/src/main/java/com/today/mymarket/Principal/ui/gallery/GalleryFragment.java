package com.today.mymarket.Principal.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.mymarket.Activities.NuevaCompra;
import com.today.mymarket.AdapterList.AdapterFacturas;
import com.today.mymarket.DB.Compras;
import com.today.mymarket.DB.Factura;
import com.today.mymarket.DB.Ventas;
import com.today.mymarket.R;

import java.util.ArrayList;
import java.util.List;

import static com.today.mymarket.DB.Preference.getID;

public class GalleryFragment extends Fragment {
    private AdapterFacturas adapter;
    private ListView lista;
    private List<Factura> list = new ArrayList<Factura>();

    private TextView tv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        lista = (ListView) root.findViewById(R.id.lista_fc);
        tv = (TextView) root.findViewById(R.id.text_c);

        FloatingActionButton fab = root.findViewById(R.id.fab_compras);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), NuevaCompra.class);

                startActivity(i);
            }
        });




        listaComprasporusuario(getID(root.getContext()));


        return root;
    }

    private void listaComprasporusuario(final String id_usuario) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("ventas").orderByChild("id_usuario").equalTo(id_usuario);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot obj : dataSnapshot.getChildren()){

                        Factura f = obj.getValue(Factura.class);
                        Factura fa = new Factura();

                        assert f != null;
                        if(f.getId_usuario().equals(id_usuario)){

                            fa.setId_factura(f.getId_factura());

                            fa.setFecha(f.getFecha());
                            fa.setNombre_tienda(f.getNombre_tienda());
                            fa.setSubtotal(f.getSubtotal());
                            fa.setIva(f.getIva());
                            fa.setTotal(f.getTotal());
                            fa.setImg_url(f.getImg_url());


                            list.add(fa);

                        }


                        adapter = new AdapterFacturas(getContext(),list);
                        lista.setAdapter(adapter);
                    }



                }else{
                    tv.setText(getString(R.string.msj_01));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




}
