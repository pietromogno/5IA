package com.example.matti.clientchat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import oggetti.Messaggio;

/**
 * Questa classe si occupa della gestione della chat e del menù laterale di navigazione
 */
public class ChatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Observer, Serializable {

    private SubMenu sub;
    private ServiceClass service;
    private String nomeUtenteSorg, nomeUtenteDest;
    private ArrayList<String> utenti;
    private ListView chat;
    private MyAdapterChat adapter;
    private ArrayList<String[]> chatContainer;
    private EditText input;
    private Button send;
    private ImageView imageUser;
    private Bitmap bitmapImage;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent i = getIntent();
        UtilForActivity u = (UtilForActivity) i.getExtras().getSerializable("Utente");
        service = u.doConnection();
        service.addObserver(this);
        service.tryConnection();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        nomeUtenteDest = "";
        nomeUtenteSorg = u.getMessaggio().getNomeUtenteSorg();
        send = (Button) findViewById(R.id.sendChatBtn);
        input = (EditText) findViewById(R.id.input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View vi = navigationView.getHeaderView(0);
        TextView nomeUtenteTxt = (TextView) vi.findViewById(R.id.nomeUtenteNavTxt);
        nomeUtenteTxt.setText(nomeUtenteSorg);
        imageUser = (ImageView) vi.findViewById(R.id.imageUser);
        if (((Object[]) u.getMessaggio().getMessaggio())[1] != null) {
            byte[] imageByte=((byte[]) ((Object[]) u.getMessaggio().getMessaggio())[1]);
            bitmapImage = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            if (bitmapImage != null) {
                imageUser.setImageBitmap(bitmapImage);
            }
        }
        chatContainer = new ArrayList<>();
        adapter = new MyAdapterChat(ChatActivity.this, R.id.listaChat, chatContainer,bitmapImage,nomeUtenteSorg);
        chat = (ListView) findViewById(R.id.listaChat);
        chat.setAdapter(adapter);
        Menu menu = navigationView.getMenu();
        sub = menu.addSubMenu("Utenti registrati");
        service.getUtenti(nomeUtenteSorg);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (input.getText().toString().length() > 0 && input.getText().toString().length() < 500) {
                    service.invioChat(input.getText().toString(), nomeUtenteSorg, nomeUtenteDest);
                } else {
                    Toast.makeText(ChatActivity.this, getString(R.string.invalid_input), Toast.LENGTH_SHORT).show();
                }
            }
        });
        homePage();
    }

    /**
     * Invocata quando l'utente preme un'opzione dal menù a comparsa
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.action_log_out))) {
            startLogin();
        } else if (item.getTitle().equals(getString(R.string.action_delete))) {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
            final EditText input = new EditText(ChatActivity.this);
            input.setHint(getString(R.string.prompt_password));
            input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);

            builder.setTitle("Eliminazione account")
                    .setMessage(getString(R.string.delete_account))
                    .setView(input)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            service.deleteAccount(input.getText().toString(), nomeUtenteSorg);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (item.getTitle().equals(getString(R.string.action_update_chat)) && !getTitle().equals(getString(R.string.home_page))) {
            service.showChat(nomeUtenteSorg, nomeUtenteDest);
        } else if (item.getTitle().equals(getString(R.string.change_picture))) {
            selectImage();
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
        if (nome.equals(getString(R.string.home_page))) {
            homePage();
        } else {
            nomeUtenteDest = nome;
            setTitle(nomeUtenteDest);
            service.showChat(nomeUtenteSorg, nomeUtenteDest);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Procedura che mostra all'utente la chat con un altro utente
     */
    private void showChat() {
        input.setVisibility(View.VISIBLE);
        send.setVisibility(View.VISIBLE);
        input.setText("");
        this.setTitle(nomeUtenteDest);

        if (chatContainer.isEmpty() || chatContainer.get(0)[0].equals("0")) {
            String noChat[] = {"", "", getString(R.string.no_chat)};
            chatContainer.clear();
            chatContainer.add(noChat);
        } else if (chatContainer.get(0)[0].equals("Disconnesso")) {
            String noChat[] = {"", "", getString(R.string.error_connection)};
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
    private void addUsers() {
        if (utenti.isEmpty() || utenti.get(0).contains("Errore")) {
            sub.clear();
        } else {
            sub.clear();
            for (int i = 0; i < utenti.size(); i++) {
                sub.add(utenti.get(i)).setIcon(R.drawable.contact);
            }
        }
    }

    private void homePage() {
        setTitle(getString(R.string.home_page));
        String[] start = {"", "", getString(R.string.home_page_display)};
        chatContainer.clear();
        chatContainer.add(start);
        adapter.clear();
        adapter.addAll(chatContainer);
        adapter.notifyDataSetChanged();
        chat.setSelection(chat.getCount() - 1);
        input.setVisibility(View.INVISIBLE);
        send.setVisibility(View.INVISIBLE);
    }

    private void startLogin() {
        service.deleteObservers();
        Intent newChat = new Intent(ChatActivity.this, LoginActivity.class);
        UtilForActivity u = new UtilForActivity(service, null);
        newChat.putExtra("Utente", u);
        startActivity(newChat);
        finish();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && data != null) {
            Uri selectedimg = data.getData();
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg);
                imageUser.setImageBitmap(bitmapImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteImage = stream.toByteArray();
                service.setImage(nomeUtenteSorg, byteImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void update(Observable observable, Object o) {
        ServiceClass my = (ServiceClass) (observable);
        Messaggio msg = my.msg;
        if (msg != null) {
            switchFunction(msg);
        }
    }

    private void switchFunction(final Messaggio msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (msg.getFunzione()) {
                    case Messaggio.OTTIENIUTENTI:
                        utenti = (ArrayList<String>) msg.getMessaggio();
                        addUsers();
                        break;
                    case Messaggio.MOSTRAMESSAGGIO:
                        if(!((ArrayList<String[]>) msg.getMessaggio()).get(0)[0].equals("1")) {
                            if (getTitle().equals(msg.getNomeUtenteDest()) || !getTitle().equals(getString(R.string.home_page))) {
                                chatContainer.clear();
                                chatContainer = (ArrayList<String[]>) msg.getMessaggio();
                                showChat();
                            } else {
                                NotificationCompat.Builder b = new NotificationCompat.Builder(ChatActivity.this);
                                b.setAutoCancel(true)
                                        .setDefaults(Notification.DEFAULT_ALL)
                                        .setWhen(System.currentTimeMillis())
                                        .setSmallIcon(R.drawable.new_message)
                                        .setContentTitle("Hai una notifica")
                                        .setContentText(msg.getNomeUtenteDest() + " ti sta messaggiando!")
                                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
                                NotificationManager notificationManager = (NotificationManager) ChatActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.notify(45556, b.build());
                            }
                        }else{
                            Toast.makeText(ChatActivity.this,((ArrayList<String[]>) msg.getMessaggio()).get(0)[1] , Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case Messaggio.SALVACONVERSAZIONE:
                        String ris = String.valueOf(msg.getMessaggio());
                        if (ris.equals("true")) {
                            String temp[] = {"Tu: ", new SimpleDateFormat("dd/MM/yyyy-HH:mm").format(Calendar.getInstance().getTime()), input.getText().toString()};
                            input.setText("");
                            chatContainer.add(temp);
                            adapter.add(temp);
                            adapter.notifyDataSetChanged();
                            input.setText("");
                        } else if (ris.contains("disconnesso")) {
                            input.setText("Il messaggio non è stato inviato\nServer non disponibile");
                        } else {
                            input.setText("Il messaggio non è stato inviato\nErrore sconosciuto");
                        }
                        break;
                    case Messaggio.ERRORECONNESSIONE:
                        Toast.makeText(ChatActivity.this, "Server non disponibile", Toast.LENGTH_SHORT).show();
                        startLogin();
                        break;
                    case Messaggio.CONNESSO:
                        Toast.makeText(ChatActivity.this, "Connesso", Toast.LENGTH_SHORT).show();
                        break;
                    case Messaggio.CANCELLAZIONE:
                        if (String.valueOf(msg.getMessaggio()).equals("true")) {
                            Toast.makeText(ChatActivity.this, getString(R.string.account_deleted), Toast.LENGTH_SHORT).show();
                            startLogin();
                        } else {
                            Toast.makeText(ChatActivity.this, "Errore, riprova", Toast.LENGTH_SHORT).show();
                        }
                    case Messaggio.IMMAGINE:
                        if (String.valueOf(msg.getMessaggio()).equals("true")) {
                            Toast.makeText(ChatActivity.this, getString(R.string.succ_image_change), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChatActivity.this, getString(R.string.changing_image_error), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        Toast.makeText(ChatActivity.this, getString(R.string.unknow_error), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            homePage();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        service.closeConnection(nomeUtenteSorg);
    }
}
