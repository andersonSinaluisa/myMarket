package com.today.mymarket.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.today.mymarket.R;

public class ProductosTienda extends AppCompatActivity {

    private String id_tienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_tienda);

        id_tienda= getIntent().getStringExtra("id_tienda");







    }
}
