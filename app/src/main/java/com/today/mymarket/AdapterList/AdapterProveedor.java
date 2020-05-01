package com.today.mymarket.AdapterList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.today.mymarket.DB.Proveedor;
import com.today.mymarket.R;

import java.util.List;

public class AdapterProveedor extends BaseAdapter {
    private List<Proveedor> list;
    private Context c;

    public AdapterProveedor(List<Proveedor> list, Context c) {
        this.list = list;
        this.c = c;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView tv1 = (TextView) convertView.findViewById(android.R.id.text1);
        TextView tv2 = (TextView) convertView.findViewById(android.R.id.text2);


        tv1.setText(list.get(position).getNombre_empresa()+"  "+list.get(position).getRUC());
        tv2.setText(list.get(position).getRepresentante());



        return convertView;
    }
}
