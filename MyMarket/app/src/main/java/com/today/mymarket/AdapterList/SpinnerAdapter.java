package com.today.mymarket.AdapterList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.today.mymarket.DB.Proveedor;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Proveedor> {
    private List<Proveedor> list;
    private Context c;

    public SpinnerAdapter(@NonNull Context context, int resource,List<Proveedor> list) {
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
    public Proveedor getItem(int position) {
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
        text.setText(list.get(position).getNombre_empresa());


        return text;


    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TextView text = (TextView)super.getView(position, convertView, parent);
        text.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        text.setText(list.get(position).getNombre_empresa());

        return text;
    }
}
