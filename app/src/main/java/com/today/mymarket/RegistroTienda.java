package com.today.mymarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.today.mymarket.DB.Firebase;
import com.today.mymarket.DB.Tienda;
import com.today.mymarket.Principal.Principal;

import java.util.UUID;

import static com.today.mymarket.DB.Preference.datostienda;
import static com.today.mymarket.Model.Validaciones.validaRucEP;
import static com.today.mymarket.Model.Validaciones.validadorDeCedula;


public class RegistroTienda extends AppCompatActivity {
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private Button btn;
    private EditText et_nombre_tienda, et_identificacion, et_telefono, et_direccion;
    private Spinner spiner_id;
    private String id_usuario;
    private String tipo_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_tienda);


        btn = (Button) findViewById(R.id.btn_map);
        et_nombre_tienda = (EditText) findViewById(R.id.et_n_tienda);
        et_identificacion = (EditText) findViewById(R.id.et_ident);
        spiner_id = (Spinner) findViewById(R.id.spiner_id);
        et_direccion = (EditText) findViewById(R.id.et_direccion);
        et_telefono = (EditText) findViewById(R.id.et_telefono);


        final String item1 = getString(R.string.item_1);
        String item2 = getString(R.string.item_2);

        String[] items = new String[]{item1, item2};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        spiner_id.setAdapter(adapter);

        id_usuario = getIntent().getStringExtra("id_usuario");
        tipo_id = spiner_id.getSelectedItem().toString().trim();

        ////////////////////////////////////////////////////
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String identificacion = et_identificacion.getText().toString().trim();
                if (et_nombre_tienda.equals("") && et_identificacion.equals("")) {
                    Toast.makeText(RegistroTienda.this, "ingresa los datos", Toast.LENGTH_LONG).show();
                    /////////campos vacios//////////////
                } else {
                    ///////////spiner elegir tipo id////////////////////
                    if (tipo_id.equals(item1)) {
                        ////////////////CEDULA///////////////////////////////////////
                        if (validadorDeCedula(identificacion) == true) {
                            ////////////validar repeticion identificacion/////////////////////////
                            validate_identificacion(identificacion);

                        } else {
                            Toast.makeText(RegistroTienda.this, "identificacion incorrecta", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        /////////////////RUC//////////////////////////////
                        if (validaRucEP(identificacion) == true) {
                            validate_identificacion(identificacion);

                        } else {
                            Toast.makeText(RegistroTienda.this, "identificacion incorrecta", Toast.LENGTH_LONG).show();
                        }
                    }

                }


            }
        });
        ///////////////////////////////////////////////

    }



    private void validate_identificacion(final String identificacion) {
        /////////////valida si la identificacion ya existe///////////////////


        final Firebase db = new Firebase();
        final DatabaseReference ref = db.getmDatabase("tiendas");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                        Tienda u = objSnaptshot.getValue(Tienda.class);
                        assert u != null;
                        if (u.getIdentificacion().equals(identificacion)) {
                            Toast.makeText(RegistroTienda.this, "este nombre de usuario ya exite", Toast.LENGTH_LONG).show();
                            break;
                        } else {
                            Tienda p = new Tienda();
                            p.setId_tienda(UUID.randomUUID().toString());
                            p.setNombre(et_nombre_tienda.getText().toString().trim());
                            p.setTipo_id(tipo_id);
                            p.setIdentificacion(et_identificacion.getText().toString().trim());
                            p.setId_usuario(id_usuario);
                            p.setEstado(1);
                            p.setLatitud(0);
                            p.setLongitud(0);
                            p.setTelefono(et_telefono.getText().toString().trim());
                            p.setDireccion(et_direccion.getText().toString().trim());

                            DatabaseReference rfp = db.getmDatabase("tiendas");
                            datostienda(RegistroTienda.this,p.getNombre(),p.getIdentificacion(),p.getId_tienda(),p.getTipo_id(),p.getEstado(), p.getTelefono(), p.getDireccion());
                            //crea referencia de tienda



                            rfp.child(p.getId_tienda()).setValue(p);
                            Intent i = new Intent(RegistroTienda.this, Principal.class);
                            startActivity(i);
                            break;
                        }

                    }

                }else{


                    Tienda p = new Tienda();
                    p.setId_tienda(UUID.randomUUID().toString());
                    p.setNombre(et_nombre_tienda.getText().toString().trim());
                    p.setTipo_id(tipo_id);
                    p.setIdentificacion(et_identificacion.getText().toString().trim());
                    p.setId_usuario(id_usuario);
                    p.setEstado(1);
                    p.setLatitud(0);
                    p.setLongitud(0);
                    p.setTelefono(et_telefono.getText().toString().trim());
                    p.setDireccion(et_direccion.getText().toString().trim());


                    DatabaseReference rfp = db.getmDatabase("tiendas");
                    datostienda(RegistroTienda.this,p.getNombre(),p.getIdentificacion(),p.getId_tienda(),p.getTipo_id(),p.getEstado(), p.getTelefono(), p.getDireccion());
                    //crea referencia de tienda


                    rfp.child(p.getId_tienda()).setValue(p);
                    Intent i = new Intent(RegistroTienda.this, Principal.class);
                    startActivity(i);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    }
