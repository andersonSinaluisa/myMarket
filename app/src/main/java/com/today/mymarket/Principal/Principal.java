package com.today.mymarket.Principal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.today.mymarket.Activities.FullscreenPhoto;
import com.today.mymarket.Activities.NuevoProducto;
import com.today.mymarket.DB.Firebase;
import com.today.mymarket.DB.Persona;
import com.today.mymarket.DB.Usuario;
import com.today.mymarket.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.UUID;

import static com.today.mymarket.DB.Preference.apellido;
import static com.today.mymarket.DB.Preference.getID;
import static com.today.mymarket.DB.Preference.getImg;
import static com.today.mymarket.DB.Preference.img;
import static com.today.mymarket.DB.Preference.nombre;
import static com.today.mymarket.DB.Preference.persona;
import static com.today.mymarket.DB.Preference.tienda;
import static com.today.mymarket.DB.Preference.tipo;
import static com.today.mymarket.DB.Preference.usuario;
import static com.today.mymarket.R.drawable.ic_add;
import static com.today.mymarket.R.drawable.ic_map;

public class Principal extends AppCompatActivity {

    private String IMG_URL_DEFAULT = "https://www.nicepng.com/png/full/202-2022264_usuario-annimo-usuario-annimo-user-icon-png-transparent.png";

    private AppBarConfiguration mAppBarConfiguration;

    private ProgressDialog dialog ;
    public static final int PICK_IMAGE_CODE =1;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference refstorage =storage.getReference();
    private       ImageView imageView;

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

        dialog= new ProgressDialog(Principal.this);









        //los fragmentes asociados con el activity principal

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_productos,R.id.nav_mensajes, R.id.nav_repartidor,
                R.id.nav_proveedor, R.id.nav_cliente)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //crea un view para obtener los componentes
        View headerView = navigationView.getHeaderView(0);

        TextView nombre = (TextView)headerView.findViewById(R.id.nombre);
        imageView = (ImageView) headerView.findViewById(R.id.imageView);

        TextView nombre_comp =(TextView)headerView.findViewById(R.id.usuario);







        //si el tipo de usario es 1 (admin )
        if(tipo(Principal.this)==1){
            //setea los nombres en los textview con los datos guardados anteriormente en el sharedpreference
            nombre.setText(tienda(Principal.this));
            nombre_comp.setText(nombre(Principal.this)+" "+apellido(Principal.this));


        }else{
            //si es tipo 2 (cliente) oculta los menus a los que no deberia tener permiso de acceder
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

        String EDteamImage = getImg(Principal.this);
        Glide.with(Principal.this).load(EDteamImage).into(imageView);
        imageView.setBackground(null);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(getImg(Principal.this).equals(IMG_URL_DEFAULT)){

                    BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(Principal.this);
                    LayoutInflater layoutInflater = getLayoutInflater();

                    final View view = layoutInflater.inflate(R.layout.dialog_imgview, null);
                    mBottomSheetDialog.setContentView(view);

                    mBottomSheetDialog.show();
                    LinearLayout btn_camera = (LinearLayout) view.findViewById(R.id.btn_camera);
                    LinearLayout btn_galeria = (LinearLayout) view.findViewById(R.id.btn_galeria);

                    btn_camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {



                        }
                    });


                    btn_galeria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_PICK);
                            i.setType("image/*");

                            startActivityForResult(i, PICK_IMAGE_CODE) ;


                        }
                    });


                }else{

                    Intent i = new Intent(Principal.this, FullscreenPhoto.class);
                    startActivity(i);


                }







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

            final String id_usuario = getID(Principal.this);


            Uri uri = data.getData();

            final StorageReference foto  = refstorage.child(id_usuario+"/fotos/perfil").child(UUID.randomUUID().toString());

            UploadTask uploadTask =foto.putFile(uri);

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    dialog.setTitle(progress+"%  subiendo");
                    System.out.println("Upload is " + progress + "% done");
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Handle successful uploads on complete
                    // ...


                    foto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            img(Principal.this, url);

                            final Firebase db = new Firebase();


                            obternerUsuario(id_usuario, url);
                            dialog.dismiss();
                            String EDteamImage = getImg(Principal.this);
                            Glide.with(Principal.this).load(EDteamImage).into(imageView);
                        }
                    });
                }
            });


        }
    }

    private void obternerUsuario(final String id_usuario, final String url) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //crea un query para la consulta
        Query q = ref.child("usuarios").orderByChild("id_usuario").equalTo(id_usuario);
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
                        if (u.getId_usuario().equals(id_usuario)) {

                            final Firebase db = new Firebase();
                            DatabaseReference rfu = db.getmDatabase("usuarios");
                            rfu.child(id_usuario).child("img_url").setValue(url);
                        }



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //dentro de este metodo muestra los errores al consultar con la base
            }
        });


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
