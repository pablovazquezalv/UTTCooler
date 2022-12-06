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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

//CONECTAR CON ADAFRUIT, PARA ESO ES NECESARIO IOKEY Y EL USERNAME
public class ConectarAdafruit extends AppCompatActivity {
    //EDITEXT DE DONDDE SE AGARRARA LA INFORMACION
    EditText usuariodeadafruitIOKEY,nombredeusuarioadafruit;

    //BOTON DE CONECTAR RECIVIENDO EN IOKEY Y EL NOMBRE DE USUARIO
    Button buttonconectar;

    //URL DE INFORMACION SOBRE EL IOKEY
    //BOTON DE INFORMACION PARA SABER QUE ES EL IOKEY  Y COMO CONSEGUIRLO
    Button iokeybutton;
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

                String urlactualizariokeyyuser ="http://192.168.0.9:8000/api/in";


                JSONObject jsonbody= new JSONObject();
                try {
                    jsonbody.put("Username",nombredeusuarioadafruit.getText());
                    jsonbody.put("Active_Key",usuariodeadafruitIOKEY.getText());
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlactualizariokeyyuser, jsonbody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                     /*  try {
                            int status= Integer.parseInt(response.getString("status"));
                    */
                        int status=200;
                        if(status==200)
                        {
                            Toast.makeText( ConectarAdafruit.this, "Se actualizaron sus credenciales"+response, Toast.LENGTH_SHORT).show();

                        }

                      /*  } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

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