package com.example.matti.clientchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Questa classe si occupa della gestione della chat e del menù laterale di navigazione
 */
public class ChatActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SubMenu sub;
    private ServiceClass service;
    private String nomeUtenteSorg, nomeUtenteDest, idUtente,password;
    private ArrayList<String> utenti;
    private ListView chat;
    private MyAdapterChat adapter;
    private ArrayList<String[]> chatContainer;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        service = new ServiceClass();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vi = inflater.inflate(R.layout.nav_header_chat, null);
        Intent intent = getIntent();
        String temp[] = intent.getExtras().getStringArray("Utente");
        nomeUtenteDest = temp[0];
        nomeUtenteSorg = temp[1];
        idUtente = temp[2];
        password = temp[3];
        chatContainer = service.showChat(nomeUtenteDest, nomeUtenteSorg);

        TextView nomeUtenteTxt = (TextView) vi.findViewById(R.id.nomeUtenteTxt);
        nomeUtenteTxt.setText(nomeUtenteSorg);
        chat = (ListView) findViewById(R.id.listaChat);
        adapter = new MyAdapterChat(this, R.id.listaChat, chatContainer);
        chat.setAdapter(adapter);
        Button send = (Button) findViewById(R.id.sendChatBtn);
        input = (EditText) findViewById(R.id.input);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        sub = menu.addSubMenu("Utenti registrati");
        addUsers();
        showChat();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().length() > 0&&input.getText().toString().length()<500) {
                    String check = service.invioChat(input.getText().toString(), nomeUtenteSorg, nomeUtenteDest, idUtente);
                    if (check.equals("true")) {
                        String temp[] = {"Tu: ", new SimpleDateFormat("dd/MM/yyyy-HH:mm").format(Calendar.getInstance().getTime()), input.getText().toString()};
                        chatContainer.add(temp);
                        adapter.add(temp);
                        adapter.notifyDataSetChanged();
                        input.setText("");
                    } else if (check.contains("disconnesso")) {
                        Toast.makeText(ChatActivity.this, "Il messaggio non è stato inviato\nServer non disponibile", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ChatActivity.this, "Il messaggio non è stato inviato\nErrore sconosciuto", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ChatActivity.this, "Inserisci almeno 1 carattere\nal massimo 500 caratteri", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Procedura che mostra all'utente la chat con un altro utente
     */

    void showChat() {
        input.setText("");
        this.setTitle(nomeUtenteDest);
        chatContainer.clear();
        chatContainer = service.showChat(nomeUtenteDest, nomeUtenteSorg);
        if (chatContainer.isEmpty() || chatContainer.get(0)[0].equals("0")) {
            String noChat[] = {"", "", "Nessuna conversazione momentaneamente"};
            chatContainer.clear();
            chatContainer.add(noChat);
        } else if (chatContainer.get(0)[0].equals("Disconnesso")) {
            String noChat[] = {"", "", "Oh no, sembra che il server sia disconnesso :/"};
            chatContainer.clear();
            chatContainer.add(noChat);
        }
        adapter.clear();
        adapter.addAll(chatContainer);

        adapter.notifyDataSetChanged();
        chat.setSelection(chat.getCount() - 1);
    }

    /**
     * Procedura che aggiunge gli utenti registrati sulla barra laterale di navigazione
     */
    void addUsers() {
        utenti = service.getUtenti(nomeUtenteSorg);
        if (utenti.isEmpty() || utenti.get(0).contains("Errore")) {
            sub.clear();
        } else {
            sub.clear();
            for (int i = 0; i < utenti.size(); i++) {
                sub.add(utenti.get(i)).setIcon(R.drawable.contact);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent newChat = new Intent(this, UserList.class);
            String temp[] = {idUtente, nomeUtenteSorg,password};
            newChat.putExtra("Utente", temp);
            startActivity(newChat);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     *Invocata quando l'utente preme un'opzione dal menù a comparsa
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.action_log_out))) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if(item.getTitle().equals(getString(R.string.action_delete))) {
            Intent delete=new Intent(this,DeleteAccount.class);
            String dati[]={nomeUtenteSorg,password};
            delete.putExtra("Dati",dati);
            startActivity(delete);
        }else{
            showChat();
        }
        return true;
    }

    /**
     * Invocata quando l'utente preme un'opzione sul menù laterale di navigazione
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String nome = item.getTitle().toString();
        if (nome.equals("Mostra")) {
            Intent i = new Intent(this, UserList.class);
            String intn[] = {idUtente, nomeUtenteSorg};
            i.putExtra("Utente", intn);
            startActivity(i);
            finish();
        } else {
            nomeUtenteDest = nome;
            showChat();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
