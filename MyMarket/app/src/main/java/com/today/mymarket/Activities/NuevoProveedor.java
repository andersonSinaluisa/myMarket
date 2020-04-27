package com.today.mymarket.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.today.mymarket.DB.Firebase;
import com.today.mymarket.DB.Proveedor;
import com.today.mymarket.Principal.Principal;
import com.today.mymarket.Principal.ui.proveedores.Proveedores;
import com.today.mymarket.R;
import com.today.mymarket.RegistroTienda;
import com.today.mymarket.Registrousuario;

import java.util.UUID;

import static com.today.mymarket.DB.Preference.id_tienda;
import static com.today.mymarket.Model.Validaciones.validaRucEP;
import static com.today.mymarket.Model.Validaciones.validadorDeCedula;

public class NuevoProveedor extends AppCompatActivity {

    private EditText ruc;
    private EditText empresa;
    private EditText representante;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_proveedor);

        empresa=(EditText) findViewById(R.id.empresa_n_proveedor);
        ruc=(EditText)findViewById(R.id.ruc_n_proveedor);
        representante=(EditText)findViewById(R.id.representante_n_proveedor);
        fab=(FloatingActionButton) findViewById(R.id.fab_save_proveedor);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _empresa = empresa.getText().toString().trim();
                String _ruc = ruc.getText().toString().trim();
                String _representante = representante.getText().toString().trim();

                if(_empresa.equals("") || _ruc.equals("") || _representante.equals("")){
                    Toast.makeText(NuevoProveedor.this, "ingresa los datos completos", Toast.LENGTH_LONG).show();

                }else {
                    if(validadorDeCedula(_ruc)==false){
                        Toast.makeText(NuevoProveedor.this, "identificacion incorrecta", Toast.LENGTH_LONG).show();

                    }else{
                        validardatos(_ruc,_empresa,_representante, id_tienda(NuevoProveedor.this));
                    }

                }

            }
        });




    }

    private void validardatos(final String RUC, final String NOMBRE, final String REP, final String id_tienda) {


        final Firebase db = new Firebase();
        DatabaseReference ref =db.getmDatabase("proveedores");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Proveedor pr = obj.getValue(Proveedor.class);
                        assert pr != null;
                        if(pr.getId_tienda().equals(id_tienda)){

                        if(pr.getRUC().equals(RUC) || pr.getNombre_empresa().equals(NOMBRE) ||
                        pr.getRepresentante().equals(REP)){

                            Toast.makeText(NuevoProveedor.this, "ya exite un proveedor con estos datos", Toast.LENGTH_LONG).show();
                            break;
                        }
                        }else{
                            Proveedor p = new Proveedor();
                            p.setId_proveedor(UUID.randomUUID().toString());
                            p.setId_tienda(id_tienda);
                            p.setRepresentante(REP);
                            p.setNombre_empresa(NOMBRE);
                            p.setRUC(RUC);
                            DatabaseReference rfp = db.getmDatabase("proveedores");
                            rfp.child(p.getId_proveedor()).setValue(p);

                            Intent i = new Intent(NuevoProveedor.this, Principal.class);

                            startActivity(i);
                            break;
                        }




                    }


                }else{
                    Proveedor p = new Proveedor();
                    p.setId_proveedor(UUID.randomUUID().toString());
                    p.setId_tienda(id_tienda);
                    p.setRepresentante(REP);
                    p.setNombre_empresa(NOMBRE);
                    p.setRUC(RUC);
                    DatabaseReference rfp = db.getmDatabase("proveedores");
                    rfp.child(p.getId_proveedor()).setValue(p);
                    Intent i = new Intent(NuevoProveedor.this, Principal.class);
                    startActivity(i);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NuevoProveedor.this,databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });



    }
}
