package com.today.mymarket.AdapterList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.today.mymarket.DB.Tipo_Producto;

import java.util.List;

public class SpinnerAdapterTipo extends ArrayAdapter<Tipo_Producto> {
    private List<Tipo_Producto> list;
    private Context c;

    public SpinnerAdapterTipo(@NonNull Context context, int resource,List<Tipo_Producto> list) {
        super(context, resource, list);
        this.c=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Tipo_Producto getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView text = (TextView)super.getView(position, convertView, parent);
        text.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        text.setText(list.get(position).getNombre());


        return text;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView text = (TextView)super.getView(position, convertView, parent);
        text.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        text.setText(list.get(position).getNombre());

        return text;
    }


}
