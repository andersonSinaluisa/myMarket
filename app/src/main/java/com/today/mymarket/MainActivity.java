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
                dialog.setTitle(getString(R.string.dialog_01));

                dialog.setCancelable(false);
                dialog.show();


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
        //constantes de usuario y contraseña
        final String usuario= et_usuario.getText().toString().trim();
        final String clave= et_clave.getText().toString().trim();
        System.out.println("valores son"+usuario+clave);
        //validacion que no esten vacios
        if(usuario==null || usuario.equals("") && clave==null || clave.equals("")){
            dialog.dismiss();
            Toast.makeText(MainActivity.this, getString(R.string.error_01),Toast.LENGTH_LONG).show();


        }else{
            //obtiene la referencia de la bd
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            //crea un query para la consulta
            Query q = ref.child("usuarios").orderByChild("usuario").equalTo(usuario);
            //ejecuta la consulta
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //valida si existen valores en la consulta
                   if(dataSnapshot.exists()) {
                        //for each para recorrer la lista de usuarios
                       for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                           //crea un obj para obtener los valores de la consulta
                           Usuario u = objSnaptshot.getValue(Usuario.class);

                           //si el usuario y contraseña sean iguales
                           if (u.getUsuario().equals(usuario) && u.getPass().equals(clave) && u.getEstado()==1) {

                               dialog.setTitle(getString(R.string.dialog_02));

                               //obtiene los datos personales
                               consultarpersona(u.getId_persona());

                               //si el tipo de usuario es 1 ( admin o tienda )
                               if(u.getTipo()==1){
                                   dialog.setTitle(getString(R.string.dialog_03));
                                   //obtiene los datos de la tienda
                                   datosTienda(u.getId_usuario());

                               }

                               Intent i = new Intent(MainActivity.this, Principal.class);

                               //guarda los datos en sharedprefence
                               Preference.valores(MainActivity.this,u.getId_usuario(),u.getId_persona(),u.getUsuario(),u.getTipo());
                                //entra al menu principal
                               startActivity(i);

                                break;

                           }else{
                               //si el usario y contraseña no son iguales
                               //muestra un mensaje
                               if(tv.getVisibility() == View.VISIBLE){ //si es Visible lo pones Gone
                                   tv.setVisibility(View.GONE);
                               }else{ // si no es Visible, lo pones
                                   tv.setVisibility(View.VISIBLE);
                               }
                               //Toast.makeText(MainActivity.this,"Usuario y/o contraseña incorrecta",Toast.LENGTH_LONG).show();
                               tv.setText(getString(R.string.error_02));



                           }




                       }
                   }else{
                       //si no hay usuarios registrados muestra un mensaje
                       dialog.dismiss();
                       tv.setVisibility(View.VISIBLE);
                       tv.setText(getString(R.string.error_03));
                       Toast.makeText(MainActivity.this,"REGISTRATE AHORA",Toast.LENGTH_LONG).show();
                   }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //dentro de este metodo muestra los errores al consultar con la base
                }
            });



        }


    }

    private void datosTienda(final String id_usuario) {
        //obtiene la referencia
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //crea el query
        Query q = ref.child("tiendas").orderByChild("id_usuario").equalTo(id_usuario);
        //ejecuta la consulta
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //si existe datos dentro de la consulta
                if(dataSnapshot.exists()){
                        //for each para obtener los datos de la lista
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        //crea un obj
                        Tienda ti = obj.getValue(Tienda.class);
                        // el obj no debe ser nulo
                        assert ti != null;
                        //si el id del usuario es igual al id del usuario ingresado
                        if(ti.getId_usuario().equals(id_usuario)){
                            //cierra la venta de dialogo
                            dialog.dismiss();
                            //guarda los datos obtenidos en sharedpreference
                            datostienda(MainActivity.this,ti.getNombre(),ti.getIdentificacion(),ti.getId_tienda(),ti.getTipo_id(),ti.getEstado(),ti.getDireccion(), ti.getTelefono());
                            //si la latitud y longitud no son 0
                            if(ti.getLatitud() !=0  && ti.getLongitud()!=0){
                                String sLon =String.valueOf(ti.getLongitud());
                                String sLat = String.valueOf(ti.getLatitud());
                                //guarda la ubicacion en el sharedpreference
                                lonlat(MainActivity.this, sLon,sLat );

                            }

                            break;
                        }

                    }

                }else{
                    //si no existen tiendas en la consulta
                    System.out.println("no hay tiendas");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            //error
            }
        });


    }

    private void consultarpersona(final String id_persona) {
        //obtiene referencia
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //crea un query
        Query q = ref.child("personas").orderByChild("id_persona").equalTo(id_persona);
        //ejecuta consulta
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //si existe valoresn en la consulta
                if(dataSnapshot.exists()) {
                    //for each para recorrer la lista
                    for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                        //crea obj
                        Persona p = objSnaptshot.getValue(Persona.class);
                        assert p != null;
                        //si el idpersona es igual al id de persona que va a ingresar
                        if(p.getId_persona().equals(id_persona)){
                            //oculta el dialogo
                            dialog.dismiss();
                            //guarda los valores en sharedpreference
                        datospersona(MainActivity.this,p.getNombre(),p.getApellido(),p.getCedula(),p.getGenero());
                        break;
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //error
            }
        });


    }


}
