package com.today.mymarket.Principal.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.mymarket.DB.Firebase;
import com.today.mymarket.DB.Tienda;
import com.today.mymarket.MapsActivity;
import com.today.mymarket.Principal.Principal;
import com.today.mymarket.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.today.mymarket.DB.Preference.getID;
import static com.today.mymarket.DB.Preference.id_tienda;
import static com.today.mymarket.DB.Preference.identificacion;
import static com.today.mymarket.DB.Preference.lat;
import static com.today.mymarket.DB.Preference.lon;
import static com.today.mymarket.DB.Preference.lonlat;
import static com.today.mymarket.DB.Preference.t_id;
import static com.today.mymarket.DB.Preference.tienda;
import static com.today.mymarket.DB.Preference.tipo;
import static com.today.mymarket.DB.Preference.usuario;


public class HomeFragment extends Fragment implements GoogleMap.OnMarkerDragListener,OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private Toolbar toolbar;
    private String newTitle;
    private GoogleMap mMap;

    private MapView mp;
    private Marker marker;
    private int n_tiendas;
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
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);

        fab = (FloatingActionButton) root.findViewById(R.id.fab_map);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);


            }
        });




        //-------------------variable de permisos -------------------/
        permissionchecked = ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);


        if(lon(root.getContext()).equals("") || lat(root.getContext()).equals("")){

            //--------------si tiene el permiso concedido----------//
            if(permissionchecked== PackageManager.PERMISSION_GRANTED){
                LocationManager locationManager = (LocationManager) root.getContext().getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        LatLng sydney;
                        String sNombre =null;
                        //--------ubicacion-------/
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();
                        //------------sharedpreference---------//
                        double la = Double.parseDouble(lat(root.getContext()));
                        double lo = Double.parseDouble(lon(root.getContext()));

                        if(lat(root.getContext()).equals("") || lon(root.getContext()).equals("")){
                             sydney = new LatLng(lat, lon);
                            lonlat(root.getContext(),String.valueOf(lon),String.valueOf(lat));

                        }else{
                            sydney = new LatLng(la, lo);

                        }


                        if(tipo(root.getContext())==1){


                            sNombre= tienda(root.getContext());
                            Tienda t = new Tienda();
                            t.setId_tienda(id_tienda(root.getContext()));
                            t.setId_usuario(getID(root.getContext()));
                            t.setEstado(1);
                            t.setNombre(tienda(root.getContext()));
                            t.setTipo_id(t_id(root.getContext()));
                            t.setLatitud(la);
                            t.setLongitud(lo);
                            t.setIdentificacion(identificacion(root.getContext()));

                        }else{
                            sNombre = usuario(root.getContext());
                        }



                        mMap.addMarker(new MarkerOptions().position(sydney).title(sNombre));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


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

                int permissioncheck = ContextCompat.checkSelfPermission(root.getContext(),Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);


            }else{
                //------si no lo tiene-----------//
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
        }




        mp = (MapView) root.findViewById(R.id.map1);
        if(mp!=null){
            mp.onCreate(null);
            mp.onResume();
            mp.getMapAsync(this);

        }




        return root;
    }
//////////////////////////conversion a btimap///////////
private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
    Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_person_pin);
    background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
    Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
    vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
    Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    background.draw(canvas);
    vectorDrawable.draw(canvas);
    return BitmapDescriptorFactory.fromBitmap(bitmap);
}


    private List<Tienda> listar(final GoogleMap googleMap){
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
                            //if(p.getId_tienda().equals(id_tienda(getContext()))) {

                              //  LatLng gye = new LatLng(p.getLatitud(), p.getLongitud());
                                //marker = googleMap.addMarker(new MarkerOptions().position(gye).title(p.getNombre()));
                                //marker.setTag(p);

                               // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gye, 14));
                            //}else{

                                 LatLng gye = new LatLng(p.getLatitud(), p.getLongitud());
                                 marker = googleMap.addMarker(new MarkerOptions().position(gye).title(p.getNombre()));
                                 marker.setTag(p);
                            //}





                    }




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        System.out.println(list+"------------------->");
        return list;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapsInitializer.initialize(getContext());

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);



                // Add a marker in Sydney and move the camera
        list.clear();
        listar(googleMap);

                if(lat(getContext()).equals("") || lat(getContext())==null || lon(getContext()).equals("") || lon(getContext())==null) {

                    LatLng gye = new LatLng(-2.075144784574326, 79.91745796054602);
                    marker =googleMap.addMarker(new MarkerOptions().position(gye).draggable(true).title("GUAYAQUIL").snippet("guayaquil"));

                }



       googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMarkerDragListener(this);

    }


    @Override
    public boolean onMarkerClick(Marker _marker) {

        if(_marker.equals(marker)){


            Tienda t = (Tienda) _marker.getTag();
            System.out.println("DATOS-------"+t);


            String longi = Double.toString(_marker.getPosition().longitude);
            String lat = Double.toString(_marker.getPosition().latitude);
            Toast.makeText(getContext(), "posicion"+longi+" "+lat,Toast.LENGTH_LONG ).show();
        }

        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Toast.makeText(getContext(),"start",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onMarkerDrag(Marker _marker) {
        if(_marker.equals(marker)){
            newTitle = String.format(Locale.getDefault(),getString(R.string.market_detail),
                    _marker.getPosition().latitude,_marker.getPosition().longitude);
            nombre = newTitle;





        }


    }

    @Override
    public void onMarkerDragEnd(Marker _marker) {

        Toast.makeText(getContext(),"finish",Toast.LENGTH_LONG).show();

        Tienda t = new Tienda();
        t.setId_tienda(id_tienda(getContext()));
        t.setId_usuario(getID(getContext()));
        t.setEstado(1);
        t.setNombre(tienda(getContext()));
        t.setTipo_id(t_id(getContext()));
        t.setLatitud(_marker.getPosition().latitude);
        t.setLongitud(_marker.getPosition().longitude);
        t.setIdentificacion(identificacion(getContext()));



        lonlat(getContext(),String.valueOf(t.getLongitud()),String.valueOf(t.getLatitud()));

        final Firebase db = new Firebase();
        DatabaseReference rfp = db.getmDatabase("tiendas");
        rfp.child(t.getId_tienda()).setValue(t);

    }

    public void dialog(){



    }
}
