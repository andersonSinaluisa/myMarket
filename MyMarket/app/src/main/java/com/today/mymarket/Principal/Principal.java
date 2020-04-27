package com.today.mymarket.Principal;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.mymarket.DB.Persona;
import com.today.mymarket.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.today.mymarket.DB.Preference.apellido;
import static com.today.mymarket.DB.Preference.getID;
import static com.today.mymarket.DB.Preference.nombre;
import static com.today.mymarket.DB.Preference.persona;
import static com.today.mymarket.DB.Preference.tienda;
import static com.today.mymarket.DB.Preference.tipo;
import static com.today.mymarket.DB.Preference.usuario;
import static com.today.mymarket.R.drawable.ic_add;
import static com.today.mymarket.R.drawable.ic_map;

public class Principal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  FloatingActionButton fab = findViewById(R.id.fab);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        FragmentManager fragmentManager = getSupportFragmentManager(); //Desde Activity
        Fragment fragmento = fragmentManager.findFragmentById(R.id.nav_host_fragment);





        MenuItem mapa = navigationView.getMenu().getItem(0);
        MenuItem compras = navigationView.getMenu().getItem(1);
        MenuItem ventas = navigationView.getMenu().getItem(2);
        MenuItem productos = navigationView.getMenu().getItem(3);
        MenuItem mensajes = navigationView.getMenu().getItem(4);







        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_productos,R.id.nav_mensajes, R.id.nav_repartidor,
                R.id.nav_proveedor, R.id.nav_cliente)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);

        TextView nombre = (TextView)headerView.findViewById(R.id.nombre);

        TextView nombre_comp =(TextView)headerView.findViewById(R.id.usuario);




        if(tipo(Principal.this)==1){

            nombre.setText(tienda(Principal.this));
            nombre_comp.setText(nombre(Principal.this)+" "+apellido(Principal.this));


        }else{
            if(tipo(Principal.this)==2){
                nombre.setText(usuario(Principal.this));
                nombre_comp.setText(nombre(Principal.this)+" "+apellido(Principal.this));
                navigationView.getMenu().getItem(2).setVisible(false);
                navigationView.getMenu().getItem(3).setVisible(false);
                navigationView.getMenu().getItem(5).setVisible(false);
                navigationView.getMenu().getItem(6).setVisible(false);
                navigationView.getMenu().getItem(7).setVisible(false);
            }

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
