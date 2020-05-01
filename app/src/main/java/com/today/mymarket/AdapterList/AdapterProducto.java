package com.today.mymarket.AdapterList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.today.mymarket.DB.Producto;
import com.today.mymarket.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterProducto extends BaseAdapter {
    private Context c;
    private List<Producto> list;
    private SparseBooleanArray mSelectItemsId;

    public AdapterProducto(Context c, List<Producto> list) {
        this.list=list;
        this.c=c;
        mSelectItemsId = new SparseBooleanArray();
    }



    public void toggleselection(int position){
        selectView(position, !mSelectItemsId.get(position));

    }

    public void removeSelection() {
        mSelectItemsId = new SparseBooleanArray();
        notifyDataSetChanged();
    }


    public SparseBooleanArray getSelectedIds() {
        return mSelectItemsId;
    }

    public void selectView(int position, boolean value) {
        if(value){
            mSelectItemsId.put(position,value);
        }else {
            mSelectItemsId.delete(position);
        }
        notifyDataSetChanged();
    }

    public int getSelectedCount(){
        return mSelectItemsId.size();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.productos_grid, parent, false);
        }
        TextView codigo =(TextView) convertView.findViewById(R.id.cod_producto);
        TextView nombre = (TextView) convertView.findViewById(R.id.nom_producto);
        TextView precio = (TextView) convertView.findViewById(R.id.prec_producto);
        TextView detalle = (TextView) convertView.findViewById(R.id.det_producto);
        ImageView img = (ImageView) convertView.findViewById(R.id.img_producto);




        codigo.setText("codigo: "+list.get(position).getCodigo());
        nombre.setText(list.get(position).getNombre());
        precio.setText("$ "+list.get(position).getPvp());
        detalle.setText(list.get(position).getDetalle());

        String EDteamImage =list.get(position).getUrl_img();

        Glide.with(c).load(EDteamImage).into(img);
        return convertView;
    }
}
