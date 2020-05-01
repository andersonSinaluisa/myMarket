package com.today.mymarket.AdapterList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.today.mymarket.DB.Factura;
import com.today.mymarket.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterFacturas extends BaseAdapter {
    private Context c;
    private List<Factura> list = new ArrayList<Factura>();

    public AdapterFacturas(Context c, List<Factura> list) {
        this.c = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.list_general, parent, false);
        }
        String des = null;
        ImageView i = (ImageView) convertView.findViewById(R.id.img_general);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombre_lugar);
        TextView descripcion = (TextView) convertView.findViewById(R.id.descripcion);
        TextView fecha = (TextView) convertView.findViewById(R.id.fecha);

        if(list.get(position).getEstado()==1){
            des = "entregado";

        }else{
            des= "en proceso";
        }


        nombre.setText(list.get(position).getNombre_tienda());
        descripcion.setText(des+" "+list.get(position).getTotal());
        fecha.setText(list.get(position).getFecha());
        String EDteamImage = list.get(position).getImg_url();
        Glide.with(c).load(EDteamImage).into(i);


        return convertView;
    }
}
