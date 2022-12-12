package app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    private List<BlockFeed> sensoresList;
    private RequestQueue requestQueue;
    AdaptadorSensor adapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private static final String SHARE_PREF_KEY="mypref";
    private static final  String KEY_USERADAFRUIT="useradafruit";
    private static final String KEY_IOKEY="iokey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hielera_informacion);
        requestQueue = Singleton.getInstance(HieleraInformacion.this).getRequestQueue();

        sensoresList = new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        txtinfo=findViewById(R.id.informacion);


        preferences= getApplicationContext().getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        String usernameadafruit= preferences.getString(KEY_USERADAFRUIT,null);
        String iokey= preferences.getString(KEY_IOKEY,null);
        editor=preferences.edit();

        //ENVIAR INFORMACION ENTRE ACTIVITYS
        hielera= (Hielera) getIntent().getExtras().getSerializable("datos");
        txtinfo.setText(hielera.getName());
         dashboards=hielera.getName();
        Toast.makeText(this, "NOMBRE DASHBOARD:"+dashboards+usernameadafruit+iokey, Toast.LENGTH_SHORT).show();

       informacionSobreHieleraEnEspecial(usernameadafruit,dashboards,iokey);
       // lasData();
    }

    public void informacionSobreHieleraEnEspecial(String usernameadafruit,String dashboards,String iokey)
    {
        String url="https://gallant-fermat.143-198-158-11.plesk.page/api/feedgroup";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                JSONObject jsonObject;
                for(int i=0;i<response.length();i++)
                {
                    try
                    {
                        jsonObject=response.getJSONObject(i);
                        BlockFeed hielerass = new BlockFeed();
                        hielerass.setName(jsonObject.getString("name"));
                        sensornombre = jsonObject.getString("name");
                       // sensoresList.add(hielerass);
                        lasData(usernameadafruit,sensornombre,iokey);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
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
                headers.put("X-AIO-Key", iokey);

                return headers;
            }

        };
        requestQueue.add(request);

    }

    public void lasData(String usernameadafruit,String sensornombre,String iokey)
    {
        String urllasdata ="https://io.adafruit.com/api/v2/"+usernameadafruit+"/feeds/"+sensornombre+"/data/last";

        JsonObjectRequest requestdos = new JsonObjectRequest(Request.Method.GET, urllasdata, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {

                    BlockFeed hielerass = new BlockFeed();
                    hielerass.setName(sensornombre);
                    hielerass.setvalue(response.getString("value"));
                   Toast.makeText(HieleraInformacion.this, "DATOS"+response, Toast.LENGTH_SHORT).show();
                   sensoresList.add(hielerass);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               setRecyclewView(sensoresList);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-AIO-Key", iokey);

                return headers;
            }

        };

        requestQueue.add(requestdos);

    }

    //INSTANCIAR RECYLCEW VIEW CON ADAPTADOR
    private void setRecyclewView(List<BlockFeed> sensoresList)
    {
        AdaptadorSensor adaptadorSensor = new AdaptadorSensor(getApplicationContext(),sensoresList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaptadorSensor);
    }

    public void Array()
    {
        String url="https://io.adafruit.com/api/v2/PVPabloVZ/dashboards/"+dashboards+"/blocks?x-aio-key=aio_MHEd17XhEuDEUSrDrXVMJX6DjXkG";
        JsonArrayRequest jj = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {

                try {
                    // JSONObject jsonObject;
                    JSONObject c= response.getJSONObject(0);
                    JSONArray ca= c.getJSONArray("block_feeds");
                    for(int i=0;i<ca.length();i++)
                    {
                        JSONObject j=response.getJSONObject(i);
                        BlockFeed sensoress = new BlockFeed();
                        sensoress.setName(j.getString("name"));
                        sensoresList.add(sensoress);



                    }
                   /* Gson gson = new Gson();
                    BlockFeed poke= gson.fromJson(response.toString(),BlockFeed.class);
                    adapter = new AdaptadorSensor(poke.getBlock_feeds());*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setRecyclewView(sensoresList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jj);


    }
    public void lastValue()
    {
        String url="https://io.adafruit.com/api/v2/PVPabloVZ/feeds/"+feedkey+"/data/last?x-aio-key=aio_MHEd17XhEuDEUSrDrXVMJX6DjXkG";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    String userid=response.getString("value");
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);



    }
   /* public void getListResult()
    {
        String url="https://io.adafruit.com/api/v2/PVPabloVZ/dashboards/"+name+"/blocks?x-aio-key=aio_MHEd17XhEuDEUSrDrXVMJX6DjXkG";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                recyclerView=findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);

                Gson gson= new Gson();
                BlockFeed winner = gson.fromJson(response.toString(), BlockFeed.class);
                adapter = new AdaptadorSensor(winner.getBlock_feeds());


                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);


    }*/


}