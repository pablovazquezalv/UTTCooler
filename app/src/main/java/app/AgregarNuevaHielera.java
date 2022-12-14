package app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.Adaptadores.AdaptadorCarro;
import app.Adaptadores.AdaptadorHielera;
import app.Adaptadores.AdaptadorSensor;
import app.Clase_Carros.Carro;
import app.Clase_Carros.ListaCarro;
import app.Clases_adafruit.BlockFeed;
import app.Clases_adafruit.Hielera;
import app.singleton.Singleton;

public class AgregarNuevaHielera extends AppCompatActivity {

    private RequestQueue requestQueue;

    private Spinner spinner;
    private TextView tv_respuesta;
    String sensornombre,nombresensor,id;
    private RecyclerView recyclerView,recyclerView2;
    private List<Carro> carroList;
    AdaptadorCarro adapter;
    int tipo=0;
    Button botonhielera;
    EditText inputnombre;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String SHARE_PREF_KEY="mypref";
    private static final  String KEY_NAME="name";
    private static final String KEY_ID="id";
    private static final  String KEY_USERADAFRUIT="useradafruit";
    private static final String KEY_IOKEY="iokey";
    private static final String KEY_TOKEN="token";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nueva_hielera);
        requestQueue = Singleton.getInstance(AgregarNuevaHielera.this).getRequestQueue();
        spinner=findViewById(R.id.spinner);
        String [] respuesta= {"Hielera","Aspiradora","Basurero"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,respuesta);
        spinner.setAdapter(adapter);

        inputnombre=findViewById(R.id.nombrehielera);
        botonhielera=findViewById(R.id.botoncrearhielera);

        preferences= getApplicationContext().getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        String id= preferences.getString(KEY_ID,null);
        String usernameadafruit= preferences.getString(KEY_USERADAFRUIT,null);
        String iokey= preferences.getString(KEY_IOKEY,null);
        String token= preferences.getString(KEY_TOKEN,null);
        editor=preferences.edit();

        botonhielera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombrecarro=inputnombre.getText().toString().trim();
                String seleccionado=spinner.getSelectedItem().toString();
                if(seleccionado.equals("Hielera"))
                {
                     tipo=1;
                }
                if(seleccionado.equals("Aspiradora"))
                {
                     tipo=2;
                }
                if(seleccionado.equals("Basurero"))
                {
                     tipo=3;
                }


                String urlgroup="https://gallant-fermat.143-198-158-11.plesk.page/api/car";

                JSONObject jsonbody= new JSONObject();
                try {
                    jsonbody.put("name",inputnombre.getText().toString().toLowerCase());
                    jsonbody.put("description","null");
                    jsonbody.put("user",id);
                    jsonbody.put("type_car",tipo);
                    jsonbody.put("aio_key",iokey);
                    jsonbody.put("username",usernameadafruit);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                 //  Toast.makeText( AgregarNuevaHielera.this, "RESPONSE"+jsonbody, Toast.LENGTH_SHORT).show();

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlgroup, jsonbody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                          //  crearSensores(id,usernameadafruit,iokey);
                        startActivity(new Intent(getApplicationContext(), Hielera.class));
                      int status=0;
                        try {
                            status = Integer.parseInt(response.getString("status"));
                            if(status==200)
                            {
                                Toast.makeText( AgregarNuevaHielera.this, "Carrito Creado", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), CoolerFragment.class));
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
                        Toast.makeText( AgregarNuevaHielera.this, "error al crear", Toast.LENGTH_SHORT).show();
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

                requestQueue.add(request);


            }
        });

    }


    public void crearSensores(String id,String usernameadafruit,String iokey)
    {


        String arreglosensores[]= new String[4];
        arreglosensores[0]="temperatura";
        arreglosensores[1]="distancia";
        arreglosensores[2]="nivelagua";
        arreglosensores[3]="bateria";

        JSONObject jsonbody= new JSONObject();
        try {


                jsonbody.put("name","temperatura");
                jsonbody.put("aio_key",iokey);
                jsonbody.put("username",usernameadafruit);
                jsonbody.put("car",id);
                jsonbody.put("group_key",inputnombre.getText());


        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        String urlsensores="https://gallant-fermat.143-198-158-11.plesk.page/api/feed";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, urlsensores, jsonbody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Toast.makeText( AgregarNuevaHielera.this, "Crear"+response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( AgregarNuevaHielera.this, "ErROR"+error, Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(request);

    }


    /*public void obtenerSensores()
    {
        String url="https://gallant-fermat.143-198-158-11.plesk.page/api/feedgroup";

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Toast.makeText(AgregarNuevaHielera.this, "response"+response, Toast.LENGTH_SHORT).show();

                recyclerView=findViewById(R.id.recyclewsensores);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());

                recyclerView.setLayoutManager(layoutManager);
                Gson gson= new Gson();
                ListaCarro winner= gson.fromJson(response.toString(),ListaCarro.class);
                adapter= new AdaptadorCarro(winner.getData());
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        requestQueue.add(request);

    }*/



}