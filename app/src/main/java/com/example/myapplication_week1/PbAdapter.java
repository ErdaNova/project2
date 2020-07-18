package com.example.myapplication_week1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class PbAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Phonebook> pbarray;
    private int layout;

    public PbAdapter(Context context, int layout, ArrayList<Phonebook> pbarray){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.pbarray=pbarray;
        this.layout=layout;
    }

    public int getCount(){return pbarray.size();}

    @Override
    public Object getItem(int i) {
        return pbarray.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public View getView(int pos, View convertview, ViewGroup parent){
        if(convertview==null) convertview=inflater.inflate(layout, parent,false);
        Phonebook pb=pbarray.get(pos);

        TextView name=(TextView)convertview.findViewById(R.id.name);
        name.setText(pb.getName());

//        TextView number=(TextView)convertview.findViewById(R.id.number);
//        number.setText(pb.getNumber());

        return convertview;
    }
}
