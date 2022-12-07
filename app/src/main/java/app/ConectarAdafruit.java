package app;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

//CONECTAR CON ADAFRUIT, PARA ESO ES NECESARIO IOKEY Y EL USERNAME
public class ConectarAdafruit extends AppCompatActivity {
    //EDITEXT DE DONDDE SE AGARRARA LA INFORMACION
    EditText usuariodeadafruitIOKEY,nombredeusuarioadafruit,nombrewifi,contrasenawifi;
    Button buttonconectar;
    private RequestQueue requestQueue;
    //URL DE INFORMACION SOBRE EL IOKEY
    //BOTON DE INFORMACION PARA SABER QUE ES EL IOKEY  Y COMO CONSEGUIRLO
    Button iokeybutton;
    String urlinfo="https://learn.adafruit.com/welcome-to-adafruit-io/securing-your-io-account";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectar_adafruit);
        requestQueue = Singleton.getInstance(ConectarAdafruit.this).getRequestQueue();

        //CLAVE IOKEY
        usuariodeadafruitIOKEY=findViewById(R.id.ioKey);
        nombredeusuarioadafruit=findViewById(R.id.usuarioadruit);
        nombrewifi=findViewById(R.id.nombredered);
        contrasenawifi=findViewById(R.id.contrared);
        //BOTON DE IOKEY CONECTARSE
        buttonconectar=findViewById(R.id.btnconecta);

        buttonconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                boolean retorno=true;
                String nombreadafruit=nombredeusuarioadafruit.getText().toString().trim();
                String iokey=usuariodeadafruitIOKEY.getText().toString().trim();

                if(TextUtils.isEmpty(nombreadafruit))
                {
                    nombredeusuarioadafruit.setError("El nombre de usuario es requerido");
                    retorno=false;
                }
                if(TextUtils.isEmpty(iokey))
                {
                    usuariodeadafruitIOKEY.setError("El iokey es requerido");
                    retorno=false;
                }
                String urlactualizariokeyyuser ="http://192.168.254.33:8000/api/user/14";
                JSONObject jsonbody= new JSONObject();
                try {
                    jsonbody.put("Active_Key",usuariodeadafruitIOKEY.getText());
                    jsonbody.put("Username",nombredeusuarioadafruit.getText());
                    jsonbody.put("red",nombrewifi.getText());
                    jsonbody.put("contrasena_red",contrasenawifi.getText());
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.PUT, urlactualizariokeyyuser, jsonbody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                            Toast.makeText( ConectarAdafruit.this, "Se actualizaron sus credenciales", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText( ConectarAdafruit.this, "ERROR"+error, Toast.LENGTH_SHORT).show();
                    }

                });
                requestQueue.add(request3);
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