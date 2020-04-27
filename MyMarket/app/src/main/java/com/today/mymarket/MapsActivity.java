package com.today.mymarket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.today.mymarket.Principal.Principal;

import static com.today.mymarket.DB.Preference.lat;
import static com.today.mymarket.DB.Preference.lonlat;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerDragListener,OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private Button btn;
    private TextView position;
    private LocationManager locManager;
    private Location loc;
    int permissionchecked;
private double lon;
private double lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        btn = (Button) findViewById(R.id.btn_gps);
        position = (TextView) findViewById(R.id.position);


////////////////variable de permisos /////////////
        permissionchecked = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);


        //--------------si tiene el permiso concedido----------//
       if(permissionchecked==PackageManager.PERMISSION_GRANTED){
           LocationManager locationManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);

           LocationListener locationListener = new LocationListener() {
               @Override
               public void onLocationChanged(Location location) {


                   lat = location.getLatitude();
                   lon = location.getLongitude();
                   position.setText(""+location.getLatitude()+"  "+location.getLongitude());
                   LatLng sydney = new LatLng(lat, lon);
                   mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                   mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


               }

               @Override
               public void onStatusChanged(String provider, int status, Bundle extras) {


               }

               @Override
               public void onProviderEnabled(String provider) {
                   Toast.makeText(MapsActivity.this, "GPS desactivado",Toast.LENGTH_LONG).show();
               }

               @Override
               public void onProviderDisabled(String provider) {
                Toast.makeText(MapsActivity.this, "GPS desactivado",Toast.LENGTH_LONG).show();
               }
           };

           int permissioncheck = ContextCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION);
           locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);


        }else{
            //------si no lo tiene-----------//
            dialog();


        }
//------------------------si el permiso se niega---------------//
       //     if(permissionchecked == PackageManager.PERMISSION_DENIED){

         //           dialog();


           // }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });






        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }


    public void dialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();


            dialog.show();




        Button btnyes = (Button) view.findViewById(R.id.btn_yes);
        Button btnno = (Button) view.findViewById(R.id.btn_no);


        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);



            }
        });

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MapsActivity.this, Principal.class);
                startActivity(i);
            }
        });

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }


}
