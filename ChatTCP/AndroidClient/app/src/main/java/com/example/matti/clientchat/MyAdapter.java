package com.example.matti.clientchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MATTI on 27/10/2017.
 */

public class MyAdapter extends ArrayAdapter {

    private ArrayList<String> utente;

    public MyAdapter(@NonNull Context context, int resource, ArrayList<String> utenti) {
        super(context, resource,utenti);
        this.utente=utenti;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater;
            inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.adapter,null);
        }
        TextView nome=(TextView)view.findViewById(R.id.nomeUtente);
        nome.setText(utente.get(i));
        return view;
    }
}
