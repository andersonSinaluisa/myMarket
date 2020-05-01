package com.today.mymarket.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.today.mymarket.AdapterList.SpinnerAdapter;
import com.today.mymarket.AdapterList.SpinnerAdapterTipo;
import com.today.mymarket.DB.Firebase;
import com.today.mymarket.DB.Producto;
import com.today.mymarket.DB.Proveedor;
import com.today.mymarket.DB.Tipo_Producto;
import com.today.mymarket.Principal.Principal;
import com.today.mymarket.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.today.mymarket.DB.Preference.getID;
import static com.today.mymarket.DB.Preference.id_tienda;

public class NuevoProducto extends AppCompatActivity {

    private SpinnerAdapter adapter;
    private SpinnerAdapterTipo adapter_producto;


    private FloatingActionButton fab_save;
    private FloatingActionButton fab_cancel;
    private FloatingActionButton fab_photo;
    private ImageView img_producto;
    private EditText nombre;
    private EditText codigo;
    private EditText precio;
    private EditText pvp;
    private Spinner unidad_medida;
    private Spinner proveedor;
    private Spinner spiner_tipo;
    private ProgressDialog dialog ;
    public static final int PICK_IMAGE_CODE =1;

    private Date fecha = new Date();
    private String id_proveedor;
    private String id_tipo_producto;
    private String url_img;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference refstorage =storage.getReference();
    private List<Proveedor> lista = new ArrayList<Proveedor>();
    private List<Tipo_Producto> lista_tipo = new ArrayList<Tipo_Producto>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);


        getSupportActionBar().setTitle("Nuevo Producto");

        dialog= new ProgressDialog(NuevoProducto.this);

        fab_cancel = (FloatingActionButton) findViewById(R.id.fab_cancel_producto);
        fab_save= (FloatingActionButton) findViewById(R.id.fab_n_producto);
        fab_photo = (FloatingActionButton) findViewById(R.id.fab_img_n_producto);
        nombre=(EditText) findViewById(R.id.nombre_n_p);
        codigo=(EditText) findViewById(R.id.cod_n_p);
        precio=(EditText) findViewById(R.id.precio_n_p);
        pvp=(EditText) findViewById(R.id.pvp_n_p);
        img_producto=(ImageView) findViewById(R.id.img_n_producto);
        proveedor=(Spinner)findViewById(R.id.spiner_proveedor);
        unidad_medida=(Spinner) findViewById(R.id.spiner_unidad);
        spiner_tipo = (Spinner) findViewById(R.id.spiner_tipo);



        String[] items = new String[]{"Lb", "Kg", "Lt","qq"};
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        unidad_medida.setAdapter(ad);

        //////////////////////////////////////////////////////////

        listarProveedores(id_tienda(NuevoProducto.this));
        listar_tipo();
        fab_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");

                startActivityForResult(i, PICK_IMAGE_CODE) ;



            }
        });


        proveedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Proveedor pr = (Proveedor) parent.getSelectedItem();
                id_proveedor =pr.getId_proveedor();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //tipo de producto
        spiner_tipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tipo_Producto t = (Tipo_Producto) parent.getSelectedItem();
                id_tipo_producto= t.getId_tipo_producto();
            }
        });


        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p = precio.getText().toString().trim();
                String pv = pvp.getText().toString().trim();
                String _nombre=nombre.getText().toString().trim();

                if(_nombre.equals("") || codigo.getText().toString().equals("")  || p.equals("") || pv.equals("") || id_proveedor.equals("") || id_proveedor==null){



                    Toast.makeText(NuevoProducto.this, "ingrese todo los datos", Toast.LENGTH_LONG).show();
                }else{
                    double precio = Double.parseDouble(p);
                    double pvp = Double.parseDouble(pv);
                    validar_nombre(codigo.getText().toString(), _nombre, id_tienda(NuevoProducto.this), precio,pvp, id_proveedor);

                }



            }
        });




    }

    private void validar_nombre(final String cod, final String nombre, final String id_tienda, final double precio, final double pvp, final String id_proveedor) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("productos").orderByChild("id_tienda").equalTo(id_tienda);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot obj : dataSnapshot.getChildren()){
                    Producto p = obj.getValue(Producto.class);

                    if(p.getId_tienda().equals(id_tienda)){
                            String _cod = String.valueOf(p.getCodigo());

                        if(_cod.equals(cod)){
                            Toast.makeText(NuevoProducto.this, "el codigo "+cod+" ya esta en uso",Toast.LENGTH_LONG).show();
                            break;
                        }else{
                            if(p.getNombre().equals(nombre)){
                                Toast.makeText(NuevoProducto.this, "este producto ya esta registrado",Toast.LENGTH_LONG).show();
                                break;
                            }else{

                                precios(cod, nombre, precio,pvp,id_tienda(NuevoProducto.this), id_proveedor);

                            }
                        }
                    }


                }

            }else{
                precios(cod, nombre, precio,pvp,id_tienda(NuevoProducto.this), id_proveedor);

            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK){
            dialog.setTitle("Subiendo foto");
            dialog.setCancelable(false);
            dialog.show();

            String id_usuario =getID(NuevoProducto.this);
            String id_tienda =id_tienda(NuevoProducto.this);
            String anio =fecha.getYear()+"";
            String mes =fecha.getMonth()+"";
            String dia = fecha.getDay()+"";

            Uri uri = data.getData();

            final StorageReference tienda = refstorage.child(id_usuario+"/"+id_tienda+"/"+anio+"/"+mes).child(anio+"_"+dia+"_"+mes+Math.random()*4.669);

            UploadTask uploadTask =tienda.putFile(uri);

            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){

                        Toast.makeText(NuevoProducto.this, "ERROR",Toast.LENGTH_LONG).show();
                    }

                    return tienda.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Uri downloadUri = task.getResult();

                        tienda.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                url_img = uri.toString();
                                img_producto.setBackground(null);
                                String EDteamImage =url_img;
                                System.out.println(url_img);
                                Glide.with(NuevoProducto.this).load(EDteamImage).into(img_producto);
                            }
                        });


                    } else {
                        System.out.println(task.getException()+"");
                    }
                }
            });


        }


    }


    private void precios(String cod, String nombre, double precios, double pvp, String id_tienda, String id_proveedor){
        int _cod = Integer.parseInt(cod);
        if(precios == 0 || precios == 0.0 || precios == 00.00 || precios<0){
            Toast.makeText(NuevoProducto.this, "asigne un precio valido",Toast.LENGTH_LONG).show();
        }else{
            if(pvp== 0 || pvp == 0.0 || pvp==00.00 || pvp<0){
                Toast.makeText(NuevoProducto.this, "asigne un precio valido",Toast.LENGTH_LONG).show();
            }else{
                if(id_proveedor==null || id_proveedor.equals("")) {
                Toast.makeText(NuevoProducto.this, "agrega a tus prooveedores",Toast.LENGTH_LONG).show();

                }else {
                    Producto p = new Producto();
                    p.setId_producto(UUID.randomUUID().toString());
                    p.setCodigo(_cod);
                    p.setNombre(nombre);
                    p.setPvp(pvp);
                    p.setPrecio(precios);
                    p.setId_tienda(id_tienda);
                    p.setId_proveedor(id_proveedor);
                    p.setDetalle(unidad_medida.getSelectedItem().toString());
                    p.setUrl_img(url_img);
                    p.setId_tipo_producto(id_tipo_producto);
                    System.out.println(id_proveedor);
                    final Firebase db = new Firebase();
                    DatabaseReference rfu = db.getmDatabase("productos");
                    rfu.child(p.getId_producto()).setValue(p);

                    Intent i = new Intent(NuevoProducto.this, Principal.class);
                    startActivity(i);
                }
            }

        }


    }

    private void listarProveedores(final String id_tienda) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("proveedores").orderByChild("id_tienda").equalTo(id_tienda);

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Proveedor> list1 = new ArrayList<Proveedor>();
                if(dataSnapshot.exists()){
                    int i = (int) dataSnapshot.getChildrenCount();
                    String[] ps = new String[i];
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Proveedor p = obj.getValue(Proveedor.class);


                        assert p != null;
                        if(p.getId_tienda().equals(id_tienda)) {
                            lista.add(p);

                        }


                        adapter = new SpinnerAdapter(NuevoProducto.this, android.R.layout.simple_spinner_item,lista);
                        proveedor.setAdapter(adapter);
                    }


                }else{
                    Toast.makeText(NuevoProducto.this, "Registra primero un proveedore", Toast.LENGTH_LONG).show();
                    fab_save.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            
            }
        });


    }


    private void listar_tipo(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query q = ref.child("tipo_productos");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Tipo_Producto tp = obj.getValue(Tipo_Producto.class);



                        lista_tipo.add(tp);
                        adapter_producto = new SpinnerAdapterTipo(NuevoProducto.this, android.R.layout.simple_spinner_item, lista_tipo);
                        spiner_tipo.setAdapter(adapter_producto);
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
