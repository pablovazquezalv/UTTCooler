package app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.UTTCOOLER.Integradora.R;

public class Splash extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.azulogo)));
        findViewById(R.id.contado).setOnClickListener(this);
    }


   @Override
    public void onClick(View view) {
        startActivity(new Intent(this, Login.class));
    }
    CountDownTimer tiempo= new CountDownTimer(5000, 1000)
    {
        public void onTick(long milisUntilFinished)
        {
            TextView tiempo=findViewById(R.id.contado);
            tiempo.setText(""+milisUntilFinished /1000);
        }

        public void onFinish()
        {
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

    }.start();
}