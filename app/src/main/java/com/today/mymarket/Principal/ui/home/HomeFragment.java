package com.today.mymarket.Principal.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.mymarket.Activities.ProductosTienda;
import com.today.mymarket.DB.Firebase;
import com.today.mymarket.DB.Tienda;
import com.today.mymarket.MapsActivity;
import com.today.mymarket.Principal.ui.productos.Productos;
import com.today.mymarket.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.today.mymarket.DB.Preference.direccion;
import static com.today.mymarket.DB.Preference.getID;
import static com.today.mymarket.DB.Preference.id_tienda;
import static com.today.mymarket.DB.Preference.identificacion;
import static com.today.mymarket.DB.Preference.lat;
import static com.today.mymarket.DB.Preference.lon;
import static com.today.mymarket.DB.Preference.lonlat;
import static com.today.mymarket.DB.Preference.t_id;
import static com.today.mymarket.DB.Preference.telefono;
import static com.today.mymarket.DB.Preference.tienda;
import static com.today.mymarket.DB.Preference.tipo;
import static com.today.mymarket.DB.Preference.usuario;


public class HomeFragment extends Fragment implements GoogleMap.OnMarkerDragListener,OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private Toolbar toolbar;
    private String newTitle;
    private GoogleMap mMap;

    private MapView mp;
    private Marker marker;

    private List<Marker> lm = new ArrayList<Marker>();

    private int n_tiendas;

    private  double latitud;
    private double longitud;

    private List<Tienda> list = new ArrayList<Tienda>();

    private FloatingActionButton fab;

    private String nombre;


    private int permissionchecked;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root =inflater.inflate(R.layout.fragment_home, container, false);


        fab = (FloatingActionButton) root.findViewById(R.id.fab_map);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);


            }
        });


        lonlat_bd(id_tienda(root.getContext()));


        //esta parte es muy compleja xdxd pero te dejo el enlace de algunos videos que vi
        //https://www.youtube.com/watch?v=b2D21Ke_tWE&list=RDCMUCFWm9y6wXOpPkhg9CT5tbQg&index=2

        //-------------------variable de permisos a la ubicacion-------------------/
        permissionchecked = ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);


        //--------------si tiene el permiso concedido----------//

        if(permissionchecked== PackageManager.PERMISSION_GRANTED) {

            //si la longitud y latitud de las preferencias son vacias
            if(longitud==0 && latitud==0 ){
                LocationManager locationManager = (LocationManager) root.getContext().getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        LatLng sydney;
                        String sNombre =null;
                        //--------ubicacion-------/
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();


                        sydney = new LatLng(lat, lon);
                        lonlat(root.getContext(),String.valueOf(lon),String.valueOf(lat));



                        if(tipo(root.getContext())==1){

                            final Firebase db = new Firebase();
                            sNombre= tienda(root.getContext());
                            Tienda t = new Tienda();
                            t.setId_tienda(id_tienda(root.getContext()));
                            t.setId_usuario(getID(root.getContext()));
                            t.setEstado(1);
                            t.setNombre(tienda(root.getContext()));
                            t.setTipo_id(t_id(root.getContext()));
                            t.setLatitud(lat);
                            t.setLongitud(lon);
                            t.setIdentificacion(identificacion(root.getContext()));
                            t.setTelefono(telefono(root.getContext()));
                            t.setDireccion(direccion(root.getContext()));
                            DatabaseReference rfp = db.getmDatabase("tiendas");
                            rfp.child(t.getId_tienda()).setValue(t);
                            list.clear();
                        }






                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {


                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        Toast.makeText(root.getContext(), "GPS activado",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Toast.makeText(root.getContext(), "GPS desactivado",Toast.LENGTH_LONG).show();
                    }
                };


                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);


            }
        }else{
            //------si no lo tiene-----------//
            //----- DIALOGO PERSONALIZADO----//
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            LayoutInflater layoutInflater = getLayoutInflater();
            final View view = layoutInflater.inflate(R.layout.dialog, null);
            builder.setView(view);
            final AlertDialog dialog = builder.create();


            dialog.show();

            final HomeFragment h = new HomeFragment();


            Button btnyes = (Button) view.findViewById(R.id.btn_yes);
            Button btnno = (Button) view.findViewById(R.id.btn_no);


            btnyes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();


                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            1);



                }
            });

            btnno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    Intent i = new Intent(Intent.ACTION_MAIN);
                    try {
                        finalize();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });


        }




        mp = (MapView) root.findViewById(R.id.map1);
        if(mp!=null){
            mp.onCreate(null);
            mp.onResume();
            mp.getMapAsync(this);

        }




        return root;
    }



    private void listar(final GoogleMap googleMap){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("tiendas");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();

                    n_tiendas =(int) dataSnapshot.getChildrenCount();
                    for (DataSnapshot obj : dataSnapshot.getChildren()){


                        Tienda p = obj.getValue(Tienda.class);
                        assert p != null;

                        list.add(p);
                        System.out.println("---------->"+list.size()+list);
                        LatLng gye = new LatLng(p.getLatitud(), p.getLongitud());
                        marker = googleMap.addMarker(new MarkerOptions().position(gye).title(p.getNombre()).icon(BitmapDescriptorFactory.fromResource(R.drawable.market)));
                        marker.setTag(p);
                        if(p.getId_tienda().equals(id_tienda(getContext()))){

                        }
                        lm.add(marker);




                    }




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapsInitializer.initialize(getContext());

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        list.clear();
        listar(googleMap);




        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);

    }


    @Override
    public boolean onMarkerClick(Marker _marker) {
    for(int i = 0 ; i<lm.size(); i++){
        if(_marker.equals(lm.get(i))){
            final Tienda t = (Tienda) _marker.getTag();
            System.out.println("DATOS-------"+t);



            String longi = Double.toString(_marker.getPosition().longitude);
            String lat = Double.toString(_marker.getPosition().latitude);



            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
            LayoutInflater layoutInflater = getLayoutInflater();
            final View view = layoutInflater.inflate(R.layout.bottom_dialog, null);
            mBottomSheetDialog.setContentView(view);

            mBottomSheetDialog.show();

            TextView textViewtelefono = (TextView) view.findViewById(R.id.d_telefono);
            TextView nombre = (TextView) view.findViewById(R.id.d_nombre);
            TextView direccion = (TextView) view.findViewById(R.id.d_direccion);
            LinearLayout telefono = (LinearLayout) view.findViewById(R.id.ld_telefono);
            Button btn = (Button) view.findViewById(R.id.btn_see_more);


            assert t != null;
            nombre.setText(t.getNombre());
            direccion.setText(t.getDireccion());
            textViewtelefono.setText(t.getTelefono());

            telefono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //realiza una llamada

                    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);;

                    if(MY_PERMISSIONS_REQUEST_CALL_PHONE == PackageManager.PERMISSION_GRANTED){
                        Intent illamar = new Intent();
                        illamar.setAction(Intent.ACTION_CALL);
                        illamar.setData(Uri.parse("tel:"+t.getTelefono()));
                        startActivity(illamar);
                    }else{
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                    }





                }
            });

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent i = new Intent(getActivity(), ProductosTienda.class);
                   i.putExtra("id_tienda", t.getId_tienda());
                   startActivity(i);

                }
            });


        }

    }



        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker _marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker _marker) {
    }

    private void lonlat_bd(final String id_tienda){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //crea el query
        Query q = ref.child("tiendas").orderByChild("id_tienda").equalTo(id_tienda);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Tienda t = obj.getValue(Tienda.class);

                        if(t.getId_tienda().equals(id_tienda)){
                            latitud = t.getLatitud();
                            longitud = t.getLongitud();

                        }


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
