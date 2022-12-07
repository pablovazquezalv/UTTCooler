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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import app.singleton.Singleton;

//CLASE DEL LOGEO
public class Login extends AppCompatActivity {

    private RequestQueue requestQueue;
    //VARIABLES MAS QUE OBVIAS
    EditText correo,contraseña;
    Button botoniniciarsesion,botoncrearcuenta,botonsincuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Singleton.getInstance(Login.this).getRequestQueue();
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

              String urllogin =  "http://192.168.254.33:8000/api/in";

                JSONObject jsonbody= new JSONObject();
                try
                {
                    jsonbody.put("email",correo.getText());
                    jsonbody.put("password",contraseña.getText());
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urllogin, jsonbody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                       try {
                            int status= Integer.parseInt(response.getString("status"));
                         //  int status=200;
                            if(status==200)
                            {
                                Toast.makeText( Login.this, "Credenciales"+response, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Menus.class));
                            }
                       } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( Login.this, "Necesitas Activar tu cuenta"+error, Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(request);
            }
        });

        //BOTON PARA CREAR CUENTA LO REDIRIGE AL ACTIVITY DE CREAR UNA
        botoncrearcuenta=(Button) findViewById(R.id.crearcuenta);
        botoncrearcuenta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), Registrarse.class));

            }
        });

        //BOTON SIN CUENTA LO REDIRIGE A MENUS SIN TENER UNA CUENTA
        botonsincuenta=(Button) findViewById(R.id.btnsincuenta);
        botonsincuenta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Menus.class));

            }
        });
    }
}