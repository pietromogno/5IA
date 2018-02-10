package com.example.jimmy.tris_jimmy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void computerButton(View w){
        startActivity(new Intent(this, Computer.class));
    }

    public void personaButton(View w){
        startActivity(new Intent(this, Locale.class));
    }
}
