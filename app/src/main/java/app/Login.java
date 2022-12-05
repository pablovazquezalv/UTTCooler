package app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.UTTCOOLER.Integradora.R;
//CLASE DEL LOGEO
public class Login extends AppCompatActivity {

    //VARIABLES MAS QUE OBVIAS
    EditText correo,contraseña;
    Button botoniniciarsesion,botoncrearcuenta,botonsincuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //--------------CASTEO----------------------------
        correo=(EditText) findViewById(R.id.usuario);
        contraseña=(EditText) findViewById(R.id.contraseña);


        //BOTON DE INICIAR SESION
        botoniniciarsesion=(Button) findViewById(R.id.iniciarsesion);
        botoniniciarsesion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean retorno=true;
                String email=correo.getText().toString().trim();
                String password=contraseña.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    correo.setError("El nombre es requerido");
                    retorno=false;
                }
                if(TextUtils.isEmpty(password))
                {
                    contraseña.setError("La contraseña es requerida");
                    retorno=false;
                }

                if( ! TextUtils.isEmpty(email) && ! TextUtils.isEmpty(password))
                {
                    startActivity(new Intent(getApplicationContext(), Menus.class));
                }else
                {
                    Toast.makeText(Login.this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //BOTON PARA CREAR CUENTA LO REDIRIGE AL ACTIVITY DE CREAR UNA
        botoncrearcuenta=(Button) findViewById(R.id.crearcuenta);
        botoncrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Registrarse.class));

            }
        });

        //BOTON SIN CUENTA LO REDIRIGE A MENUS SIN TENER UNA CUENTA
        botonsincuenta=(Button) findViewById(R.id.btnsincuenta);
        botonsincuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Menus.class));

            }
        });
    }
}