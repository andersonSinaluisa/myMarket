package com.today.mymarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.today.mymarket.DB.Firebase;
import com.today.mymarket.DB.Persona;
import com.today.mymarket.DB.Usuario;
import com.today.mymarket.Principal.Principal;

import java.util.UUID;

import static com.today.mymarket.DB.Preference.datospersona;
import static com.today.mymarket.DB.Preference.img;
import static com.today.mymarket.DB.Preference.valores;


public class Registrousuario extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference refstorage = storage.getReferenceFromUrl("gs://mymarket-54d29.appspot.com/");
    private EditText et_usuario, et_clave, et_clave1;
    private Button btn_registro;


    private ProgressDialog dialog ;

    private String nombre;
    private String apellido;
    private String cedula;
    private String genero;
    private  String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrousuario);
        ////////////////////////////////////////////////////////////
        nombre = getIntent().getStringExtra("nombre");
        apellido = getIntent().getStringExtra("apellido");
        cedula = getIntent().getStringExtra("cedula");
        genero=getIntent().getStringExtra("genero");
        check= getIntent().getStringExtra("check");
        /////////////////////////////////////////////////////////
        btn_registro=(Button)findViewById(R.id.btn_nuevo_usuario);
        et_usuario=(EditText)findViewById(R.id.et_nusuario);
        et_clave=(EditText)findViewById(R.id.n_pass1);
        et_clave1=(EditText)findViewById(R.id.n_pass);

        dialog= new ProgressDialog(Registrousuario.this);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario= et_usuario.getText().toString();
                String clave= et_clave.getText().toString();
                String clave1= et_clave1.getText().toString();
                if(usuario.equals("") && clave.equals("") && clave1.equals("") ){
                    //campos vacios
                    Toast.makeText(Registrousuario.this,"ingresa tus credencias", Toast.LENGTH_LONG).show();

                }else{
                    dialog.setTitle(getString(R.string.ru_title_01));
                    dialog.show();
                    if(clave.equals(clave1)){
                        //claves iguales

                        validarnombre();

                    }else{

                        //claves diferentes
                        dialog.dismiss();
                        Toast.makeText(Registrousuario.this,"verifica la contraseña", Toast.LENGTH_LONG).show();

                    }

                }

            }
        });

    }

    private void validarnombre() {

        final String usuario= et_usuario.getText().toString().trim();
        final Firebase db = new Firebase();
        DatabaseReference ref =db.getmDatabase("usuarios");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()) {
                        Usuario u = objSnaptshot.getValue(Usuario.class);
                        assert u != null;
                        if (u.getUsuario().equals(usuario)) {
                            dialog.dismiss();
                            Toast.makeText(Registrousuario.this, "este nombre de usuario ya exite", Toast.LENGTH_LONG).show();
                            break;
                        } else {
                            Persona p = new Persona();
                            p.setId_persona(UUID.randomUUID().toString());
                            p.setNombre(nombre);
                            p.setApellido(apellido);
                            p.setCedula(cedula);
                            p.setGenero(genero);

                            Usuario usuario_n = new Usuario();
                            usuario_n.setId_usuario(UUID.randomUUID().toString());
                            usuario_n.setUsuario(usuario);
                            usuario_n.setPass(et_clave.getText().toString());
                            usuario_n.setEstado(1);
                            usuario_n.setImg_url("https://www.nicepng.com/png/full/202-2022264_usuario-annimo-usuario-annimo-user-icon-png-transparent.png");


                            if (check.equals("seleccionado")) {
                                usuario_n.setTipo(1);  //tienda
                            } else {
                                usuario_n.setTipo(2);  //cliente
                            }


                            usuario_n.setId_persona(p.getId_persona());

                            DatabaseReference rfp = db.getmDatabase("personas");
                            DatabaseReference rfu = db.getmDatabase("usuarios");
                            rfp.child(p.getId_persona()).setValue(p);
                            rfu.child(usuario_n.getId_usuario()).setValue(usuario_n);

                            img(Registrousuario.this, usuario_n.getImg_url());
                            valores(Registrousuario.this, usuario_n.getId_usuario(), usuario_n.getId_persona(), usuario_n.getUsuario(), usuario_n.getTipo());
                            datospersona(Registrousuario.this, p.getNombre(),p.getApellido(),p.getCedula(),p.getGenero());


                            if (usuario_n.getTipo() == 1) {

                                Intent i = new Intent(Registrousuario.this, RegistroTienda.class);
                                i.putExtra("id_usuario", usuario_n.getId_usuario());


                                StorageReference usuario =refstorage.child(usuario_n.getId_usuario()+"/"+usuario_n.getId_persona()+"/");

                                dialog.dismiss();
                                startActivity(i);
                                break;

                            } else {
                                if (usuario_n.getTipo() == 2) {

                                Intent i = new Intent(Registrousuario.this, Principal.class);
                                i.putExtra("id_usuario", usuario_n.getId_usuario());
                                dialog.dismiss();
                                startActivity(i);
                                break;
                                }

                            }

                        }
                    }
                }else{
                    Persona p = new Persona();
                    p.setId_persona(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellido(apellido);
                    p.setCedula(cedula);
                    p.setGenero(genero);

                    Usuario usuario_n = new Usuario();
                    usuario_n.setId_usuario(UUID.randomUUID().toString());
                    usuario_n.setUsuario(usuario);
                    usuario_n.setPass(et_clave.getText().toString());
                    usuario_n.setEstado(1);
                    usuario_n.setImg_url("https://www.nicepng.com/png/full/202-2022264_usuario-annimo-usuario-annimo-user-icon-png-transparent.png");
                    if (check.equals("seleccionado")) {
                        usuario_n.setTipo(1);  //tienda
                    } else {
                        usuario_n.setTipo(2);  //cliente
                    }
                    usuario_n.setId_persona(p.getId_persona());

                    DatabaseReference rfp = db.getmDatabase("personas");
                    DatabaseReference rfu = db.getmDatabase("usuarios");
                    rfp.child(p.getId_persona()).setValue(p);
                    rfu.child(usuario_n.getId_usuario()).setValue(usuario_n);

                    img(Registrousuario.this, usuario_n.getImg_url());
                    valores(Registrousuario.this, usuario_n.getId_usuario(), usuario_n.getId_persona(), usuario_n.getUsuario(), usuario_n.getTipo());
                    datospersona(Registrousuario.this, p.getNombre(),p.getApellido(),p.getCedula(),p.getGenero());

                    if (usuario_n.getTipo() == 1) {

                        Intent i = new Intent(Registrousuario.this, RegistroTienda.class);
                        i.putExtra("id_usuario", usuario_n.getId_usuario());



                        dialog.dismiss();

                        startActivity(i);


                    } else if (usuario_n.getTipo() == 2) {


                        Intent i = new Intent(Registrousuario.this, Principal.class);
                        i.putExtra("id_usuario", usuario_n.getId_usuario());
                        dialog.dismiss();
                        startActivity(i);


                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Registrousuario.this,databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });



    }
}
