package com.today.mymarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.mymarket.DB.Firebase;
import com.today.mymarket.DB.Persona;
import com.today.mymarket.DB.Preference;
import com.today.mymarket.DB.Tienda;
import com.today.mymarket.DB.Usuario;
import com.today.mymarket.Principal.Principal;

import static com.today.mymarket.DB.Preference.datospersona;
import static com.today.mymarket.DB.Preference.datostienda;
import static com.today.mymarket.DB.Preference.lat;
import static com.today.mymarket.DB.Preference.lonlat;

public class MainActivity extends AppCompatActivity {
    private EditText et_usuario, et_clave;
    private Button btn_inicio, btn_registro;
    private TextView tv;
    private ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_usuario=(EditText) findViewById(R.id.usuario);
        et_clave=(EditText) findViewById(R.id.clave);
        btn_inicio=(Button) findViewById(R.id.btn_inicio);
        btn_registro=(Button) findViewById(R.id.btn_registro);

        dialog= new ProgressDialog(MainActivity.this);


        tv= (TextView) findViewById(R.id.msj);
        tv.setVisibility(View.GONE);
        getSupportActionBar().hide();



        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Comprobando credenciales");

                dialog.setCancelable(false);
                dialog.show();
                Firebase bd = new Firebase();

                validacionusuario();

            }
        });


        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registro.class);
                startActivity(i);

            }
        });

    }


    private void validacionusuario(){
        final String usuario= et_usuario.getText().toString().trim();
        final String clave= et_clave.getText().toString().trim();
        System.out.println("valores son"+usuario+clave);
        if(usuario==null || usuario.equals("") && clave==null || clave.equals("")){
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "ingresa tu usuario y contraseña",Toast.LENGTH_LONG).show();


        }else{

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query q = ref.child("usuarios").orderByChild("usuario").equalTo(usuario);
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if(dataSnapshot.exists()) {

                       for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                           Usuario u = objSnaptshot.getValue(Usuario.class);

                           if (u.getUsuario().equals(usuario) && u.getPass().equals(clave) && u.getEstado()==1) {
                               //activity tienda
                               dialog.setTitle("Obteniendo datos");

                               consultarpersona(u.getId_persona());
                               if(u.getTipo()==1){
                                   dialog.setTitle("Obteniendo datos de tu tienda");
                                   datosTienda(u.getId_usuario());

                               }

                               Intent i = new Intent(MainActivity.this, Principal.class);
                               Preference.valores(MainActivity.this,u.getId_usuario(),u.getId_persona(),u.getUsuario(),u.getTipo());

                               startActivity(i);

                                break;

                           }else{
                               if(tv.getVisibility() == View.VISIBLE){ //si es Visible lo pones Gone
                                   tv.setVisibility(View.GONE);
                               }else{ // si no es Visible, lo pones
                                   tv.setVisibility(View.VISIBLE);
                               }
                               Toast.makeText(MainActivity.this,"Usuario y/o contraseña incorrecta",Toast.LENGTH_LONG).show();
                               tv.setText("Usuario y/o contraseña incorrecta");



                           }





                               //repartidor



                       }
                   }else{
                       dialog.dismiss();
                       tv.setVisibility(View.VISIBLE);
                       tv.setText("REGISTRATE AHORA ");
                       Toast.makeText(MainActivity.this,"REGISTRATE AHORA",Toast.LENGTH_LONG).show();
                   }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }


    }

    private void datosTienda(final String id_usuario) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("tiendas").orderByChild("id_usuario").equalTo(id_usuario);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot obj : dataSnapshot.getChildren()){

                        Tienda ti = obj.getValue(Tienda.class);

                        assert ti != null;
                        if(ti.getId_usuario().equals(id_usuario)){
                            dialog.dismiss();
                            datostienda(MainActivity.this,ti.getNombre(),ti.getIdentificacion(),ti.getId_tienda(),ti.getTipo_id(),ti.getEstado());
                            if(ti.getLatitud() !=0  && ti.getLongitud()!=0){
                                String sLon =String.valueOf(ti.getLongitud());
                                String sLat = String.valueOf(ti.getLatitud());

                                lonlat(MainActivity.this, sLon,sLat );

                            }

                            break;
                        }

                    }

                }else{
                    System.out.println("no hay tiendas");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void consultarpersona(final String id_persona) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("personas").orderByChild("id_persona").equalTo(id_persona);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                        Persona p = objSnaptshot.getValue(Persona.class);
                        assert p != null;
                        if(p.getId_persona().equals(id_persona)){
                            dialog.dismiss();
                        datospersona(MainActivity.this,p.getNombre(),p.getApellido(),p.getCedula(),p.getGenero());
                        break;
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
