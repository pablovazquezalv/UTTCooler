package app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ramiro174.controlbluetooth.Views.ControlLogic;

import org.json.JSONException;
import org.json.JSONObject;

import app.singleton.Singleton;


public class Login extends AppCompatActivity {

    private RequestQueue requestQueue;

    EditText correo,contraseña;
    Button botoniniciarsesion,botoncrearcuenta,botonsincuenta;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CheckBox checkBox;
    String llave="sesion";

    String nombre;
    String id;
    private static final String SHARE_PREF_KEY="mypref";
    private static final  String KEY_NAME="name";
    private static final String KEY_ID="id";
    private static final String KEY_TOKEN="token";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        requestQueue = Singleton.getInstance(Login.this).getRequestQueue();

        init();

        preferences=this.getSharedPreferences(SHARE_PREF_KEY,MODE_PRIVATE);
        editor=preferences.edit();

        if(revisarSesion())
        {
            startActivity(new Intent(getApplicationContext(), Menus.class));
        }

        botoniniciarsesion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                guardaSesion(checkBox.isChecked());
                boolean retorno=true;

                String email=correo.getText().toString().trim();
                String password=contraseña.getText().toString().trim();
                if(TextUtils.isEmpty(email))
                {
                    correo.setError("El correo es requerido");
                    retorno=false;
                }
                if(TextUtils.isEmpty(password))
                {
                    contraseña.setError("La contraseña es requerida");
                    retorno=false;
                }

                SharedPreferences.Editor editor = preferences.edit();

                String urllogin =  "https://gallant-fermat.143-198-158-11.plesk.page/api/in";

                JSONObject jsoniniciarsesion= new JSONObject();
                try
                {
                    jsoniniciarsesion.put("email",correo.getText());
                    jsoniniciarsesion.put("password",contraseña.getText());
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urllogin, jsoniniciarsesion, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                       try {

                            int status= Integer.parseInt(response.getString("status"));
                            if(status==200)
                            {
                                String nombren =response.getString("user_name");
                                 String id=response.getString("user_id");
                                String token=response.getString("access_token");
                                editor.putString(KEY_NAME,nombren);
                              editor.putString(KEY_ID,id);
                                editor.putString(KEY_TOKEN,token);
                                editor.apply();
                                Toast.makeText( Login.this, "Inicio de sesion correcto", Toast.LENGTH_SHORT).show();
                                guardaSesion(checkBox.isChecked());
                                startActivity(new Intent(getApplicationContext(), Menus.class));
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
                        Toast.makeText( Login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(request);

            }

        });


        botoncrearcuenta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), Registrarse.class));

            }
        });


        botonsincuenta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                //startActivity(new Intent(getApplicationContext(), Menus.class));
                startActivity(new Intent(getApplicationContext(), ControlLogic.class));
            }
        });
    }


    public void guardaSesion(boolean checked)
    {
        editor.putBoolean(llave,checked);
        editor.apply();
    }

    public boolean revisarSesion()
    {   return this.preferences.getBoolean(llave,false);

    }

    private void init()
    {
        correo=findViewById(R.id.usuario);
        contraseña= findViewById(R.id.contraseña);
        checkBox=findViewById(R.id.checkBox);
        botoniniciarsesion= findViewById(R.id.iniciarsesion);
        botoncrearcuenta=findViewById(R.id.crearcuenta);
        botonsincuenta=findViewById(R.id.btnsincuenta);
    }
}