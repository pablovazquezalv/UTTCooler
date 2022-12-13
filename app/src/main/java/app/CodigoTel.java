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


public class CodigoTel extends AppCompatActivity {

    private RequestQueue requestQueue;

    Button botoncreocuenta;
    EditText codigoverificacion;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String SHARE_PREF_KEY="mypref";
    private static final String KEY_URL="url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo_tel);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        requestQueue = Singleton.getInstance(CodigoTel.this).getRequestQueue();

        preferences= getApplicationContext().getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        String url= preferences.getString(KEY_URL,null);
        editor=preferences.edit();
        //Toast.makeText(this, "url"+url, Toast.LENGTH_SHORT).show();
        botoncreocuenta=(Button) findViewById(R.id.creocuentas);
        codigoverificacion=findViewById(R.id.codigodeverificacion);

        //BOTON DE VERIFICAR CODIGO SI ES CORRECTO ACCEDERA A LA PANTALLA PRINCIPAL
        botoncreocuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String codigo=codigoverificacion.getText().toString().trim();

                if(TextUtils.isEmpty(codigo))
                {
                    codigoverificacion.setError("El codigo es requerido");
                    return;
                }


                JSONObject jsonbody= new JSONObject();
                try
                {
                    jsonbody.put("Code",codigoverificacion.getText());
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonbody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            int status= Integer.parseInt(response.getString("status"));
                            if(status==200)
                            {
                                Toast.makeText( CodigoTel.this, "TU CUENTA FUE ACTIVADA, INICIA SESION", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( CodigoTel.this, "ERROR AL ACTIVAR LA CUENTA", Toast.LENGTH_SHORT).show();

                    }
                });
                requestQueue.add(request);

            }
        });
    }
}