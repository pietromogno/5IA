package com.example.matti.clientchat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Questa classe si occupa del login
 */
public class LoginActivity extends Activity {

    private static EditText nomeUtenteEdit;
    private static EditText passwordEdit;
    private static TextView registratiTxt;
    private static TextView errore;
    private Button accedi;
    private static ServiceClass ser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ser = new ServiceClass();
        ser.start();
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
                errore.setText("");
                registratiTxt.setTextColor(Color.BLACK);
                if (controlloCampi()) {
                    String check[] = ser.accedi(nomeUtenteEdit.getText().toString(), passwordEdit.getText().toString());
                    if (check[1].equals("1")) {
                        errore.setText(check[0]);
                        if (check[0].contains("registrato")) {
                            registratiTxt.setTextColor(Color.RED);
                        }
                    } else {
                        String dati[]={check[0],check[1],passwordEdit.getText().toString()};
                        Intent newChat = new Intent(LoginActivity.this, UserList.class);
                        newChat.putExtra("Utente", dati);
                        startActivity(newChat);
                        finish();
                    }
                }
            }
        });

        registratiTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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
    private boolean controlloCampi() {
        if (nomeUtenteEdit.getText().length() > 0 && passwordEdit.getText().length() > 0) {
            String checkError[] = ser.isRegistered(nomeUtenteEdit.getText().toString());
            if (checkError[1].equals("1")) {
                errore.setText(checkError[0]);
                if (checkError[0].contains("registrato")) {
                    registratiTxt.setTextColor(Color.RED);
                }
                return false;
            } else {
                return true;
            }

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

}