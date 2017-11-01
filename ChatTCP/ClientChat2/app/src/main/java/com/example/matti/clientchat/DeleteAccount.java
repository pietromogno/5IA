package com.example.matti.clientchat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Questa classe si occupa dell'eliminazione di un utente
 */

public class DeleteAccount extends AppCompatActivity {

    private ServiceClass service;
    private String nomeUtente,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        service=new ServiceClass();
        Intent intent = getIntent();
        String dati[]=intent.getExtras().getStringArray("Dati");
        nomeUtente=dati[0];
        password=dati[1];
        final EditText nomeUtenteEdit = (EditText) findViewById(R.id.inUtenteDel);
        nomeUtenteEdit.setEnabled(false);
        nomeUtenteEdit.setText(nomeUtente);
        final EditText passwordEdit = (EditText) findViewById(R.id.passwordDel);
        TextView userNameTxt=(TextView)findViewById(R.id.nomeUtenteDel);
        final TextView errore=(TextView)findViewById(R.id.erroreDel);
        errore.setTextColor(Color.RED);
        userNameTxt.setText(getString(R.string.delete_message)+" "+nomeUtente);
        Button delete=(Button)findViewById(R.id.btnDel);
        final Drawable old = passwordEdit.getBackground();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errore.setText("");
                if(passwordEdit.getText().toString().length()>0){
                    String data=service.deleteAccount(nomeUtente,passwordEdit.getText().toString());
                    if(data.contains("disconnesso")){
                        errore.setText(data);
                    }else if(data.equals("false")){
                        errore.setText(R.string.error_invalid_password);
                    }else{
                        startActivity(new Intent(DeleteAccount.this,LoginActivity.class));
                    }
                }else{
                    passwordEdit.setBackgroundColor(Color.parseColor("#ffe6e6"));
                }
            }
        });
/**
 * se il focus è sul campo allora reimposta il backgroun originale
 */
        passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    passwordEdit.setBackground(old);
                }
            }
        });
    }
}
