package com.today.mymarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.today.mymarket.DB.Firebase;
import com.today.mymarket.DB.Persona;

import static com.today.mymarket.Model.Validaciones.validadorDeCedula;

public class Registro extends AppCompatActivity {

    private EditText et_nombre, et_apellido, et_cedula;
    private CheckBox ch_bool;
    private Spinner spiner;
    private FloatingActionButton img_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        getSupportActionBar().hide();

        et_nombre=(EditText) findViewById(R.id.et_nombre);
        et_apellido=(EditText)findViewById(R.id.et_apellido);
        et_cedula=(EditText)findViewById(R.id.et_cedula);
        ch_bool= (CheckBox)findViewById(R.id.ch_bool);
        img_button= (FloatingActionButton) findViewById(R.id.btn_next);
        spiner = (Spinner)findViewById(R.id.spiner);

        String item1= getString(R.string.masculino);
        String item2 = getString(R.string.femenino);
        String item3 = getString(R.string.otro);
        String[] items = new String[]{item1, item2, item3 };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        spiner.setAdapter(adapter);


        img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = et_nombre.getText().toString().trim();
                final String apellido = et_apellido.getText().toString().trim();
                final String cedula=et_cedula.getText().toString().trim();
                final String genero = spiner.getSelectedItem().toString().trim();



                final String check ;
                if(ch_bool.isChecked()){
                    check="seleccionado";
                }else {
                    check="noseleccionado";
                }

                if (nombre.equals("") || apellido.equals("") || cedula.equals("") || genero.equals("") ) {
                    Toast.makeText(Registro.this,"campos vacios", Toast.LENGTH_LONG).show();
                }else{
                    if(validadorDeCedula(cedula)==true){


                        final Firebase db = new Firebase();
                        final DatabaseReference ref = db.getmDatabase("personas");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot obj : dataSnapshot.getChildren()){

                                        Persona p = obj.getValue(Persona.class);

                                        if(p.getCedula().equals(cedula)){
                                            Toast.makeText(Registro.this, "esta cedula ya esta en uso", Toast.LENGTH_LONG).show();
                                            break;
                                        }else {

                                            Intent i = new Intent(Registro.this, Registrousuario.class );
                                            i.putExtra("nombre",nombre);
                                            i.putExtra("apellido", apellido);
                                            i.putExtra("cedula",cedula);
                                            i.putExtra("genero",genero);
                                            i.putExtra("check",check);

                                            startActivity(i);
                                            break;
                                        }

                                    }


                                }else{
                                    Intent i = new Intent(Registro.this, Registrousuario.class );
                                    i.putExtra("nombre",nombre);
                                    i.putExtra("apellido", apellido);
                                    i.putExtra("cedula",cedula);
                                    i.putExtra("genero",genero);
                                    i.putExtra("check",check);

                                    startActivity(i);
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });








                    }else{
                        Toast.makeText(Registro.this,"cedula incorrecta", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


    }






}
