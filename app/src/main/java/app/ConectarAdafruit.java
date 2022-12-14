package app;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.Clases_adafruit.Hielera;
import app.singleton.Singleton;


public class ConectarAdafruit extends AppCompatActivity {

    EditText usuariodeadafruitIOKEY,nombredeusuarioadafruit,nombrewifi,contrasenawifi;
    Button buttonconectar;
    private RequestQueue requestQueue;

    Button iokeybutton;
    String urlinfo="https://learn.adafruit.com/welcome-to-adafruit-io/securing-your-io-account";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String SHARE_PREF_KEY="mypref";
    private static final String KEY_ID="id";
    private static final  String KEY_USERADAFRUIT="useradafruit";
    private static final String KEY_IOKEY="iokey";
    private static final String KEY_TOKEN="token";


    String Active_Key;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conectar_adafruit);
        requestQueue = Singleton.getInstance(ConectarAdafruit.this).getRequestQueue();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //CLAVE IOKEY
        usuariodeadafruitIOKEY=findViewById(R.id.ioKey);
        nombredeusuarioadafruit=findViewById(R.id.usuarioadruit);
        nombrewifi=findViewById(R.id.nombredered);
        contrasenawifi=findViewById(R.id.contrared);
        buttonconectar=findViewById(R.id.btnconecta);
        iokeybutton=findViewById(R.id.linkiokeyinfo);

        preferences= getApplicationContext().getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        String id= preferences.getString(KEY_ID,null);
        String token= preferences.getString(KEY_TOKEN,null);
        editor=preferences.edit();

        buttonconectar.setOnClickListener(new View.OnClickListener()
        {
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

                String urlactualizariokeyyuser="https://gallant-fermat.143-198-158-11.plesk.page/api/user/"+id;
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

                        editor.putString(KEY_USERADAFRUIT,nombreadafruit);
                        editor.apply();
                        editor.putString(KEY_IOKEY,nombredeusuarioadafruit.getText().toString());
                        int status= 0;
                        try {
                            status = Integer.parseInt(response.getString("status"));
                            if(status==200)
                            {
                                Toast.makeText(ConectarAdafruit.this, "Se actualizaron sus credenciales", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Hielera.class));
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
                        Toast.makeText( ConectarAdafruit.this, "error al actualizar los datos", Toast.LENGTH_SHORT).show();
                    }

                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError
                    {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization","Bearer "+token);
                        return headers;
                    }
                };
                requestQueue.add(request3);
            }
        });

        //IR A PAGINA WEB
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