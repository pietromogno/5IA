package favaro.matteo.tris3;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent i;
    int gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnsingle:
                gameMode=0;
                break;
            case R.id.btnhome:
                gameMode=1;
                break;

        }
        i = new Intent(this,ActivityTwo.class);
        i.putExtra("gameMode",gameMode);
        startActivity(i);
    }
    //ho messo sul design onClick del bottone
    public void onClickStrange(View view){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Under construction");
        dlgAlert.setTitle("comming soon");
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
