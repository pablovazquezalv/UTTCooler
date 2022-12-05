package app;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.UTTCOOLER.Integradora.R;

//CONECTAR CON ADAFRUIT, PARA ESO ES NECESARIO IOKEY Y EL USERNAME
public class ConectarAdafruit extends AppCompatActivity {
    //EDITEXT DE DONDDE SE AGARRARA LA INFORMACION
    EditText usuariodeadafruitIOKEY,nombredeusuarioadafruit;
    //BOTON DE INFORMACION PARA SABER QUE ES EL IOKEY  Y COMO CONSEGUIRLO
    Button iokeybutton;
    //BOTON DE CONECTAR RECIVIENDO EN IOKEY Y EL NOMBRE DE USUARIO
    Button buttonconectar;

    //URL DE INFORMACION SOBRE EL IOKEY
    String urlinfo="https://learn.adafruit.com/welcome-to-adafruit-io/securing-your-io-account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectar_adafruit);

        //CLAVE IOKEY
        usuariodeadafruitIOKEY=findViewById(R.id.ioKey);
        //NOMBRE DE USUARIO IOKEY
        nombredeusuarioadafruit=findViewById(R.id.usuarioadruit);
        //BOTON DE IOKEY CONECTARSE
        buttonconectar=findViewById(R.id.btnconecta);

        buttonconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String usuarioIOKEY=usuariodeadafruitIOKEY.getText().toString();
             String nameAdafruit=nombredeusuarioadafruit.getText().toString();
                makeText(ConectarAdafruit.this, "gg"+usuarioIOKEY, Toast.LENGTH_SHORT).show();
            }
        });


        //BOTON PARA IR A LA INFORMACION SOBRE EL IOKEY
        iokeybutton=findViewById(R.id.linkiokeyinfo);
        iokeybutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Uri _link= Uri.parse(urlinfo);
                Intent i=new Intent(Intent.ACTION_VIEW,_link);
                startActivity(i);
            }
        });


    }
}