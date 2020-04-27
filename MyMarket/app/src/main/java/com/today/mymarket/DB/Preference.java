package com.today.mymarket.DB;

import android.content.Context;
import android.content.SharedPreferences;

import com.today.mymarket.R;

public class Preference {
    public static void valores(Context c, String id_usuario, String id_persona, String usuario, int i){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPref.edit();
        editor.putString("id_usuario",id_usuario);
        editor.putString("usuario",usuario);
        editor.putString("id_persona",id_persona);
        editor.putInt("tipo",i);

        editor.commit();

    }



    public static String getID(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("id_usuario",null);
    }

    public static String usuario(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("usuario","");
    }

    public static String persona(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("id_persona","");
    }
    public static int tipo(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getInt("tipo",0);
    }

    public static void datospersona(Context c, String nombre, String apellido, String cedula, String genero){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPref.edit();
        editor.putString("nombre",nombre);
        editor.putString("apellido",apellido);
        editor.putString("cedula",cedula);
        editor.putString("genero",genero);
        editor.commit();
    }
    public static String nombre(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("nombre","");
    }
    public static String apellido(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("apellido","");
    }
    public static String cedula(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("cedula","");
    }
    public static String genero(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("genero","");
    }

    public static void datostienda(Context c, String nombre, String identificacion, String id_tienda,String t_ident, int estado){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPref.edit();
        editor.putString("tienda",nombre);
        editor.putInt("estado",estado);
        editor.putString("identificacion",identificacion);
        editor.putString("id_tienda",id_tienda);
        editor.putString("tip_id",t_ident);
        editor.commit();


    }


    public static void lonlat(Context c, String lon, String lat){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPref.edit();
        editor.putString("longitud",lon);
        editor.putString("latitud",lat);

        editor.commit();
    }
    public static String lon(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("longitud","");
    }

    public static String lat(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("latitud","");
    }

    public static String tienda(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("tienda","");
    }

    public static String t_id(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("tip_id","");
    }
    public static String identificacion(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("identificacion","");
    }

    public static String id_tienda(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("id_tienda","");
    }





}
