package app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.UTTCOOLER.Integradora.R;

//CLASE DE CODIGO DE VERIFICACION
public class CodigoTel extends AppCompatActivity {

    Button botoncreocuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_tel);

        botoncreocuenta=(Button) findViewById(R.id.creocuentas);
        //BOTON DE VERIFICAR CODIGO SI ES CORRECTO ACCEDERA A LA PANTALLA PRINCIPAL
        botoncreocuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), Menus.class));
            }
        });
    }
}