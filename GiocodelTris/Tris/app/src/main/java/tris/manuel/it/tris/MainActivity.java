package tris.manuel.it.tris;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    public void intelliComButton(View w){
        w.setEnabled(false);
        //startActivity(new Intent(this, Computer.class));
    }

    public void personaButton(View w){
        startActivity(new Intent(this, Locale.class));
    }

    public void onlineButton(View w){
        w.setEnabled(false);
        //startActivity(new Intent(this, Computer.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
