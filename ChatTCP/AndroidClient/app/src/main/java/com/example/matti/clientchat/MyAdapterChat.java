package com.example.matti.clientchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MATTI on 29/10/2017.
 */

public class MyAdapterChat extends ArrayAdapter {

    private ArrayList<String[]> chat;

    public MyAdapterChat(@NonNull Context context, int resource, ArrayList<String[]> chat) {
        super(context, resource,chat);
        this.chat=chat;
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
            view=inflater.inflate(R.layout.adapter_hat,null);
        }
        String dati[]=chat.get(i);
        TextView nome=(TextView)view.findViewById(R.id.nomeSorgChat);
        TextView ora=(TextView)view.findViewById(R.id.ora);
        TextView chat=(TextView)view.findViewById(R.id.chatLst);
        nome.setText(dati[0]);
        ora.setText(dati[1]);
        chat.setText(dati[2]);
        return view;
    }
}