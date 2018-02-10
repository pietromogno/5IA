package favaro.matteo.tris;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int j=0;
    Button[][] btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=new Button[3][3];
        btn[0][0] = findViewById(R.id.uno);
        btn[0][1] = findViewById(R.id.due);
        btn[0][2] = findViewById(R.id.tre);
        btn[1][0] = findViewById(R.id.quattro);
        btn[1][1] = findViewById(R.id.cinque);
        btn[1][2] = findViewById(R.id.sei);
        btn[2][0] = findViewById(R.id.sette);
        btn[2][1] = findViewById(R.id.otto);
        btn[2][2] = findViewById(R.id.nove);
        clickListener(btn[0][0]);
        clickListener(btn[0][1]);
        clickListener(btn[0][2]);
        clickListener(btn[1][0]);
        clickListener(btn[1][1]);
        clickListener(btn[1][2]);
        clickListener(btn[2][0]);
        clickListener(btn[2][1]);
        clickListener(btn[2][2]);
    }
    public void clickListener(Button b) {
        b.setOnClickListener((View view) -> {
            if (b.getText().equals("")) {
                if (j % 2 == 0) b.setText("X");
                else b.setText("O");
                j++;
            }
            Intent i = new Intent(this,ActivityTwo.class);
            if(j<9) checkVictory(i);
            else { i.putExtra("winner","none"); startActivity(i);}
        });
    }
    public void checkVictory(Intent i){
        if(btn[0][0].getText().equals(btn[0][1].getText()) && btn[0][0].getText().equals(btn[0][2].getText()) && !(btn[0][0].getText().equals(""))) { i.putExtra("winner",btn[0][0].getText()); startActivity(i); }
        if(btn[1][0].getText().equals(btn[1][1].getText()) && btn[1][0].getText().equals(btn[1][2].getText()) && !(btn[1][0].getText().equals(""))) { i.putExtra("winner",btn[1][0].getText()); startActivity(i); }
        if(btn[2][0].getText().equals(btn[2][1].getText()) && btn[2][0].getText().equals(btn[2][2].getText()) && !(btn[2][0].getText().equals(""))) { i.putExtra("winner",btn[2][0].getText()); startActivity(i); }
        if(btn[0][0].getText().equals(btn[1][0].getText()) && btn[0][0].getText().equals(btn[2][0].getText()) && !(btn[0][0].getText().equals(""))) { i.putExtra("winner",btn[0][0].getText()); startActivity(i); }
        if(btn[0][1].getText().equals(btn[1][1].getText()) && btn[0][1].getText().equals(btn[2][1].getText()) && !(btn[0][1].getText().equals(""))) { i.putExtra("winner",btn[0][1].getText()); startActivity(i); }
        if(btn[0][2].getText().equals(btn[1][2].getText()) && btn[0][2].getText().equals(btn[2][2].getText()) && !(btn[0][2].getText().equals(""))) { i.putExtra("winner",btn[0][2].getText()); startActivity(i); }
        if(btn[0][0].getText().equals(btn[1][1].getText()) && btn[0][0].getText().equals(btn[2][2].getText()) && !(btn[0][0].getText().equals(""))) { i.putExtra("winner",btn[0][0].getText()); startActivity(i); }
        if(btn[0][2].getText().equals(btn[1][1].getText()) && btn[0][2].getText().equals(btn[2][0].getText()) && !(btn[0][2].getText().equals(""))) { i.putExtra("winner",btn[0][2].getText()); startActivity(i); }
    }
}
