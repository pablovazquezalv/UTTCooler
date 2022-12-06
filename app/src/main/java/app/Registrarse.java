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

public class Registrarse extends AppCompatActivity {

    private RequestQueue requestQueue;
    Button btncrearcuenta;
    EditText inputnombre,inputapellido,inputtelefono,inputcorreo,inputcontrasena,inputusuario,inputred,inputcontraseña,inputiokey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        requestQueue = Singleton.getInstance(Registrarse.this).getRequestQueue();

        inputnombre=(EditText) findViewById(R.id.nombre);
        inputapellido=(EditText) findViewById(R.id.apellidos);
        inputtelefono=(EditText) findViewById(R.id.telefono);
        inputcontrasena=(EditText) findViewById(R.id.contraseña);
        inputcorreo=(EditText) findViewById(R.id.correoelectronico);
        inputusuario=(EditText) findViewById(R.id.usuario);
       // inputred=(EditText) findViewById(R.id.red);
      //  inputcontraseña=(EditText) findViewById(R.id.contraseñared);
       // inputiokey=(EditText) findViewById(R.id.activekey);

        btncrearcuenta=(Button) findViewById(R.id.crearcuenta);
        btncrearcuenta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String nombre=inputnombre.getText().toString().trim();
                String apellido=inputapellido.getText().toString().trim();
                String telefono=inputtelefono.getText().toString().trim();
                String contrasena=inputcontrasena.getText().toString().trim();
                String correo=inputcorreo.getText().toString().trim();
                String usuario=inputusuario.getText().toString().trim();
              //  String nombrered=inputred.getText().toString().trim();
               // String contrared=inputcontraseña.getText().toString().trim();
              //  String iokey=inputiokey.getText().toString().trim();
                if(TextUtils.isEmpty(nombre))
                {
                    inputnombre.setError("El nombre es requerido");
                    return;
                }
                if(TextUtils.isEmpty(apellido))
                {
                    inputapellido.setError("El apellido es requerido");
                    return;
                }
                if(TextUtils.isEmpty(telefono))
                {
                    inputtelefono.setError("El telefeno es requerido");
                    return;
                }
                if(TextUtils.isEmpty(contrasena))
                {
                    inputcorreo.setError("La contraseña es requerido");
                    return;
                }
                if(contrasena.length()<6 && TextUtils.isEmpty(contrasena))
                {
                    inputcontrasena.setError("la contraseña deber ser mayor a 6 caracteres");
                }
                if(TextUtils.isEmpty(correo))
                {
                    inputcorreo.setError("El correo es requerido");
                    return;
                }

                String urllogin="http://192.168.0.9:8000/api/reg";

                JSONObject jsonbody= new JSONObject();
                try {
                    jsonbody.put("name",inputnombre.getText());
                    jsonbody.put("email",inputcorreo.getText());
                    jsonbody.put("phone",inputtelefono.getText());
                    jsonbody.put("password",inputcontrasena.getText());
                    jsonbody.put("rol_id","1");
                    jsonbody.put("red","null");
                    jsonbody.put("contrasena_red","null");
                    jsonbody.put("Username",inputusuario.getText());
                    jsonbody.put("Active_Key","null");
                    jsonbody.put("lastname",inputapellido.getText());

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urllogin,jsonbody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                          int status= Integer.parseInt(response.getString("status"));


                          if(status==201)
                          {
                              Toast.makeText(Registrarse.this, "Cuenta Creada"+status, Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(getApplicationContext(), CodigoTel.class));
                          }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Registrarse.this, "fallo"+error, Toast.LENGTH_SHORT).show();
                    }
                });
                
                requestQueue.add(request);



            }
        });
    }
}