package app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.Adaptadores.AdaptadorSensor;
import app.Clases_adafruit.Hielera;
import app.Clases_adafruit.BlockFeed;
import app.singleton.Singleton;

public class HieleraInformacion extends AppCompatActivity {

    //HIELERA NOMBRE
    private Hielera hielera;
    TextView txtinfo,txtkey;
    Button boton;
    String dashboards,feedkey,sensornombre;
    //PARA SENSORES
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<BlockFeed> sensoresList;
    private RequestQueue requestQueue;
    AdaptadorSensor adapter;

    int n;
    int count = 0;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String SHARE_PREF_KEY="mypref";
    private static final  String KEY_USERADAFRUIT="useradafruit";
    private static final String KEY_IOKEY="iokey";
    private static final String KEY_SENSOR="idsensor";
    private static final String KEY_GROUP="namegroup";
    private static final String KEY_TOKEN="token";


    Button btnON,btnOFF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hielera_informacion);

        requestQueue = Singleton.getInstance(HieleraInformacion.this).getRequestQueue();
        sensoresList = new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        swipeRefreshLayout=findViewById(R.id.swipeRefresh);

        txtinfo=findViewById(R.id.informacion);


        preferences= getApplicationContext().getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        String usernameadafruit= preferences.getString(KEY_USERADAFRUIT,null);
        String iokey= preferences.getString(KEY_IOKEY,null);
        String idsensor= preferences.getString(KEY_SENSOR,null);
        String token= preferences.getString(KEY_TOKEN,null);
        String nombregroup=preferences.getString(KEY_GROUP,null);
        editor=preferences.edit();

        btnON = findViewById(R.id.on);
        btnOFF = findViewById(R.id.off);

        JSONObject x = new JSONObject();

        btnON.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    x.put("value",1);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                String url2="https://io.adafruit.com/api/v2/"+usernameadafruit+"/feeds/hielera.desague/data";
             //   String url = "https://io.adafruit.com/api/v2/"+usernameadafruit+"/feeds/led/data";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url2, x, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(HieleraInformacion.this,"Dato enviado",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(HieleraInformacion.this,"Error al enviar el dato",Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("X-AIO-Key",iokey);
                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });

        btnOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    x.put("value",0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // String url = "https://io.adafruit.com/api/v2/"+usernameadafruit+"/feeds/led/data";
                String url2="https://io.adafruit.com/api/v2/"+usernameadafruit+"/feeds/hielera.desague/data";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url2, x, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(HieleraInformacion.this,"Dato enviado",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HieleraInformacion.this,"Error al enviar el dato",Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("X-AIO-Key",iokey);
                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });


        //ENVIAR INFORMACION ENTRE ACTIVITYS
        hielera= (Hielera) getIntent().getExtras().getSerializable("datos");
        txtinfo.setText(hielera.getName());
         dashboards=hielera.getName();
      //  Toast.makeText(this, "NOMBRE DASHBOARD:"+dashboards+usernameadafruit+iokey, Toast.LENGTH_SHORT).show();
        informacionSobreHieleraEnEspecial(nombregroup,usernameadafruit,dashboards,iokey,idsensor,token);

       // lasData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                sensoresList.clear();
                informacionSobreHieleraEnEspecial(nombregroup,usernameadafruit,dashboards,iokey,idsensor,token);
                Toast.makeText(HieleraInformacion.this, "Se refresco", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void informacionSobreHieleraEnEspecial(String nombregroup,String usernameadafruit,String dashboards,String iokey,String idsensor,String token)
    {
        String url="https://gallant-fermat.143-198-158-11.plesk.page/api/feeds/"+idsensor;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                JSONObject jsonObject;
                for(int i=0;i<response.length()-1;i++)
                {
                    try
                    {

                        jsonObject=response.getJSONObject(i);
                        BlockFeed hielerass = new BlockFeed();
                        hielerass.setName(jsonObject.getString("name"));
                        sensornombre = jsonObject.getString("name");
                        lasData(nombregroup, usernameadafruit, sensornombre, iokey);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                   // setRecyclewView(sensoresList);
                }
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
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

    public void lasData(String nombregroup,String usernameadafruit,String sensornombre,String iokey)
    {
        String urllasdata ="https://io.adafruit.com/api/v2/"+usernameadafruit+"/feeds/hielera."+sensornombre+"/data/last?x-aio-key="+iokey;

        JsonObjectRequest requestdos = new JsonObjectRequest(Request.Method.GET, urllasdata, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    BlockFeed hielerass = new BlockFeed();
                    hielerass.setName(sensornombre);
                    hielerass.setvalue(response.getString("value"));
                   sensoresList.add(hielerass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setRecyclewView(sensoresList);
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
            }
        });
        requestQueue.add(requestdos);
    }


    private void setRecyclewView(List<BlockFeed> sensoresList)
    {
        AdaptadorSensor adaptadorSensor = new AdaptadorSensor(getApplicationContext(),sensoresList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptadorSensor);
    }
}