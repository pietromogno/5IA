package com.example.matti.clientchat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import oggetti.Messaggio;

/**
 * Questa classe si occupa del login
 */
public class LoginActivity extends Activity implements Observer, Serializable {

    private EditText nomeUtenteEdit;
    private EditText passwordEdit;
    private TextView registratiTxt;
    private TextView errore;
    private Button accedi;
    private ServiceClass ser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ser = new ServiceClass();
        ser.addObserver(this);
        ser.tryConnection();
        Log.v("observer set", "enter");
        nomeUtenteEdit = (EditText) findViewById(R.id.nomeUtenteTxt);
        passwordEdit = (EditText) findViewById(R.id.password);
        registratiTxt = (TextView) findViewById(R.id.registratiTxt);
        errore = (TextView) findViewById(R.id.erroreTxt);
        accedi = (Button) findViewById(R.id.accedi);
        final Drawable old = passwordEdit.getBackground();
/**
 * Viene eseguito l'accesso se tutti i campi sono rispettati
 */
        accedi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accedi.getText().equals(getString(R.string.retry_connection))) {
                    Log.v("BUTTON RETRY", "CALLING::::");
                    ser.tryConnection();
                } else {
                    errore.setText("");
                    registratiTxt.setTextColor(Color.BLACK);
                    if (controlloCampi()) {
                        ser.accedi(nomeUtenteEdit.getText().toString(), passwordEdit.getText().toString());
                    }
                }
            }
        });

        registratiTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                registrati();
            }
        });

        nomeUtenteEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    nomeUtenteEdit.setBackground(old);
                }
            }
        });
        passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    passwordEdit.setBackground(old);
                }
            }
        });
    }

    /**
     * Funzione che controlla se tutti i campi sono stati compilati e rispettano determinati criteri,
     * si occupa anche di controllare se l'utente è registrato
     */

    void registrati() {
        final EditText password = new EditText(LoginActivity.this);
        final EditText password2 = new EditText(LoginActivity.this);
        final EditText nomeUtente = new EditText(LoginActivity.this);
        final EditText nome = new EditText(LoginActivity.this);
        final EditText cognome = new EditText(LoginActivity.this);
        final TextView erroreReg = new TextView(LoginActivity.this);
        password.setHint(getString(R.string.prompt_password));
        password.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        password2.setHint(getString(R.string.prompt_password));
        password2.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        nomeUtente.setHint(getString(R.string.prompt_user));
        nome.setHint("Nome");
        cognome.setHint("Cognome");
        erroreReg.setTextColor(Color.RED);
        LinearLayout lp = new LinearLayout(this);
        lp.setOrientation(LinearLayout.VERTICAL);
        lp.addView(nome);
        lp.addView(cognome);
        lp.addView(nomeUtente);
        lp.addView(password);
        lp.addView(password2);
        lp.addView(erroreReg);

        final AlertDialog
                builder = new AlertDialog.Builder(this)
                .setTitle("Registrazione nuovo utente")
                .setMessage("Benvenuto! Registrati qui sotto")
                .setView(lp)
                .setPositiveButton(android.R.string.yes, null)

                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_menu_add)
                .create();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (nome.getText().length() > 0 &&
                                cognome.getText().length() > 0 &&
                                nomeUtente.getText().toString().length() > 0 && nomeUtente.getText().toString().matches("[a-zA-Z0-9]{4,}$") &&
                                password.getText().length() > 0 &&
                                password.getText().toString().equals(password2.getText().toString())) {
                            ser.registerToDB(nome.getText().toString(), cognome.getText().toString(), nomeUtente.getText().toString(), password.getText().toString());
                            builder.dismiss();
                        } else {
                            erroreReg.setText("");
                            if (!password.getText().toString().equals(password2.getText().toString())) {
                                erroreReg.setText(R.string.error_invalid_password2);
                            }
                            if (!nomeUtente.getText().toString().matches("[a-zA-Z0-9]{4,}$")) {
                                erroreReg.setText(R.string.user_not_match);
                            }
                            if (nome.getText().length() <= 0) {
                                erroreReg.setText(R.string.error_field_required);
                            }
                            if (cognome.getText().length() <= 0) {
                                erroreReg.setText(R.string.error_field_required);
                            }
                            if (nomeUtente.getText().length() <= 0) {
                                erroreReg.setText(R.string.error_field_required);
                            }
                            if (password.getText().length() <= 0) {
                                erroreReg.setText(R.string.error_field_required);
                            }
                            if (password2.getText().length() <= 0) {
                                erroreReg.setText(R.string.error_field_required);
                            }
                        }
                    }
                });
            }
        });
        builder.show();
    }

    private boolean controlloCampi() {
        if (nomeUtenteEdit.getText().length() > 0 && passwordEdit.getText().length() > 0) {
            return true;
        } else {
            errore.setText(R.string.error_field_required);
            if (nomeUtenteEdit.getText().length() <= 0) {
                nomeUtenteEdit.setBackgroundColor(Color.parseColor("#ffe6e6"));
            }
            if (passwordEdit.getText().length() <= 0) {
                passwordEdit.setBackgroundColor(Color.parseColor("#ffe6e6"));
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void update(Observable observable, Object o) {
        ServiceClass my = (ServiceClass) (observable);
        Messaggio msg = my.msg;
        if (msg != null) {
            switchFunction(msg);
        }
    }

    void switchFunction(final Messaggio msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (msg.getFunzione()) {
                    case Messaggio.ACCESSO:
                        Object dati[] = (Object[]) msg.getMessaggio();
                        if (!dati[0].equals("1")) {
                            ser.deleteObservers();
                            Intent newChat = new Intent(LoginActivity.this, ChatActivity.class);
                            UtilForActivity u = new UtilForActivity(ser, msg);
                            newChat.putExtra("Utente", u);
                            startActivity(newChat);
                            finish();
                        } else {
                            errore.setText(((String[]) msg.getMessaggio())[1]);
                        }
                        break;
                    case Messaggio.ERRORECONNESSIONE:
                        accedi.setText(getString(R.string.retry_connection));
                        errore.setText((String) msg.getMessaggio());
                        break;
                    case Messaggio.CONNESSO:
                        accedi.setText(getString(R.string.action_log_in));
                        errore.setText("");
                        break;
                    case Messaggio.REGISTRAZIONE:
                        String registered[] = (String[]) msg.getMessaggio();
                        if (registered[0].equals("1")) {
                            Toast.makeText(LoginActivity.this, ((String[]) msg.getMessaggio())[1], Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Registrato con successo", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        errore.setText(getString(R.string.unknow_error));
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}