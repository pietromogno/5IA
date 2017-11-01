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
import android.widget.Toast;

/**
 * Questa classe si occupa della registrazione di un nuovo utente
 */
public class RegisterActivity extends Activity {

    private EditText nomeUtenteEdit, passwordEdit, passwordRipEdit, cognomeUtenteEdit, nomeEdit;
    private TextView accediActivity, errore;
    private Button accedi;
    private ServiceClass ser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ser = new ServiceClass();
        nomeUtenteEdit = (EditText) findViewById(R.id.nomeUtenteRegTxt);
        passwordEdit = (EditText) findViewById(R.id.passwordReg);
        passwordRipEdit = (EditText) findViewById(R.id.passwordRipetutoReg);
        nomeEdit = (EditText) findViewById(R.id.nomeRegTxt);
        cognomeUtenteEdit = (EditText) findViewById(R.id.cognomeRegTxt);
        accediActivity = (TextView) findViewById(R.id.accediRedTxt);
        errore = (TextView) findViewById(R.id.erroreRegTxt);
        accedi = (Button) findViewById(R.id.registratiBtn);
        final Drawable old=passwordEdit.getBackground();

        accedi.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                errore.setText("");
                if (control()) {
                    String[] registered = ser.registerToDB(nomeUtenteEdit.getText().toString(), cognomeUtenteEdit.getText().toString(), nomeUtenteEdit.getText().toString(), passwordEdit.getText().toString());
                    if (registered[1].equals("1")) {
                        errore.setText(registered[0]);
                        nomeUtenteEdit.setBackgroundColor(Color.parseColor("#ffe6e6"));
                    } else {
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            }
        });
        /**
         * Queste procedure reimpostano il precedente background appena ricevono il focus...
         * in caso di errore il background divenda rosato per attirare l'attenzione dell'utente
         */
        nomeEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    nomeEdit.setBackground(old);
                }
            }
        });
        cognomeUtenteEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    cognomeUtenteEdit.setBackground(old);
                }
            }
        });
        nomeUtenteEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    nomeUtenteEdit.setBackground(old);
                }
            }
        });
        passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    passwordEdit.setBackground(old);
                }
            }
        });
        passwordRipEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    passwordRipEdit.setBackground(old);
                }
            }
        });
        accediActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    /**
     * Funzione che controlla se tutti i campi sono compilati e rispettano determinati criteri
     */
    boolean control() {
        if (nomeEdit.getText().length() > 0 &&
                cognomeUtenteEdit.getText().length() > 0 &&
                nomeUtenteEdit.getText().toString().length() > 0 &&nomeUtenteEdit.getText().toString().matches("[a-zA-Z0-9]{4,}$")&&
                passwordEdit.getText().length() > 0 &&
                passwordEdit.getText().toString().equals(passwordRipEdit.getText().toString())) {

                return true;

        }
        if(!passwordEdit.getText().toString().equals(passwordRipEdit.getText().toString())){
            errore.setText(R.string.error_invalid_password2);
        }
        if (!nomeUtenteEdit.getText().toString().matches("[a-zA-Z0-9]{4,}$")) {
            errore.setText(R.string.user_not_match);
        }
        if (nomeEdit.getText().length() <= 0) {
            nomeEdit.setBackgroundColor(Color.parseColor("#ffe6e6"));
            errore.setText(R.string.error_field_required);
        }
        if (cognomeUtenteEdit.getText().length() <= 0) {
            cognomeUtenteEdit.setBackgroundColor(Color.parseColor("#ffe6e6"));
            errore.setText(R.string.error_field_required);
        }
        if (nomeUtenteEdit.getText().length() <= 0) {
            nomeUtenteEdit.setBackgroundColor(Color.parseColor("#ffe6e6"));
            errore.setText(R.string.error_field_required);
        }
        if (passwordEdit.getText().length() <= 0) {
            passwordEdit.setBackgroundColor(Color.parseColor("#ffe6e6"));
            errore.setText(R.string.error_field_required);
        }
        if (passwordRipEdit.getText().length() <= 0) {
            passwordRipEdit.setBackgroundColor(Color.parseColor("#ffe6e6"));
            errore.setText(R.string.error_field_required);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

