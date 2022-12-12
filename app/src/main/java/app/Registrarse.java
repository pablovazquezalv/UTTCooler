package app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String SHARE_PREF_KEY="mypref";
    private static final String KEY_URL="url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        requestQueue = Singleton.getInstance(Registrarse.this).getRequestQueue();

        preferences=this.getSharedPreferences(SHARE_PREF_KEY,MODE_PRIVATE);
        editor=preferences.edit();

        inputnombre=findViewById(R.id.nombre);
        inputapellido=findViewById(R.id.apellidos);
        inputtelefono=findViewById(R.id.telefono);
        inputcontrasena= findViewById(R.id.contraseña);
        inputcorreo= findViewById(R.id.correoelectronico);
        inputusuario=findViewById(R.id.usuario);

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
                    inputtelefono.setError("El telefono es requerido");
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

                String urlregistrarse="https://gallant-fermat.143-198-158-11.plesk.page/api/reg";

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


                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlregistrarse,jsonbody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                          int status= Integer.parseInt(response.getString("status"));
                            String segundaurl = response.getString("url2");
                            String enviarurl = segundaurl.replace("\\/","/");

                          if(status==201)
                          {
                              editor.putString(KEY_URL,enviarurl);
                              editor.apply();
                              Toast.makeText(Registrarse.this, "Cuenta Creada,Requiere Activacion", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(getApplicationContext(), CodigoTel.class));
                          }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(Registrarse.this, "Credenciales fallidas", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(request);
            }
        });
    }
}