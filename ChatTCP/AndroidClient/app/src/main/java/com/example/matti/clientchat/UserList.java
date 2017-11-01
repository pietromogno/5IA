package com.example.matti.clientchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Questa classe si occupa della gestione di tutti gli utenti registrati e di mostrarli all'utente
 */

public class UserList extends Activity {

    private String nomeUtenteAttuale, idUtente, password;
    private ServiceClass service;
    private ArrayList<String> utenti;
    private MyAdapter adapter;
    private ListView listaUtenti;
    private FloatingActionButton update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        listaUtenti = (ListView) findViewById(R.id.userListLst);
        update = (FloatingActionButton) findViewById(R.id.aggiornaLista);
        service = new ServiceClass();
        Intent i = getIntent();
        String[] dati = i.getExtras().getStringArray("Utente");
        nomeUtenteAttuale = dati[1];
        idUtente = dati[0];
        password = dati[2];
        insertUsers();
        listaUtenti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] temp = {utenti.get(i), nomeUtenteAttuale, idUtente, password};
                Intent newActivity = new Intent(UserList.this, ChatActivity.class);
                newActivity.putExtra("Utente", temp);
                startActivity(newActivity);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUsers();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * Questa procedura si occupa di popolare la lista
     */
    private void insertUsers() {
        utenti = service.getUtenti(nomeUtenteAttuale);
        if (utenti != null) {
            if (utenti.isEmpty() || utenti.get(0).contains("Errore")) {
                utenti.clear();
                utenti.add("C'è stato un problema, ci dispiace :/");
                listaUtenti.setEnabled(false);
            } else {
                listaUtenti.setEnabled(true);
                update.setVisibility(View.INVISIBLE);
            }
            adapter = new MyAdapter(this, R.id.userListLst, utenti);
            listaUtenti.setAdapter(adapter);
        }
    }
}
