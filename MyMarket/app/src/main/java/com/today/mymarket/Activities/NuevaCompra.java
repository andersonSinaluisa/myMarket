package com.today.mymarket.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.today.mymarket.R;

import static com.today.mymarket.DB.Preference.tipo;

public class NuevaCompra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_compra);
        int tipo_usuario= tipo(NuevaCompra.this);

        if(tipo_usuario == 1){
            //tienda

        }else{

            if(tipo_usuario==2){
                //cliente

            }else {
                if(tipo_usuario==3){

                    //repartidor

                }

            }

        }



    }
}
