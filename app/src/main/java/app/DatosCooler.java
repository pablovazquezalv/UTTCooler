package app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import app.Clases_adafruit.Hielera;
import app.singleton.Singleton;

public class DatosCooler extends AppCompatActivity {

    private RequestQueue requestQueue;

    Hielera user;
    String nombre;
    TextView sensorid, adafruituser;
    ProgressBar pb;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cooler);

        requestQueue = Singleton.getInstance(DatosCooler.this).getRequestQueue();
       JSONobject();
        sensoresRequest();
    }

    //OBTENER DE UN JSONREQUEST SOBRE USER
    public void JSONobject()
    {
        adafruituser=findViewById(R.id.txtada);
        String url = "https://io.adafruit.com/api/v2/user?x-aio-key=aio_MHEd17XhEuDEUSrDrXVMJX6DjXkG";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
               adafruituser.setText(response.toString());
                try {
                    String userid=response.getJSONObject("user").getString("name");
                    adafruituser.setText(userid);
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

    //OBTENER VALOR DE LOS SENSOR3S
    public void sensoresRequest()
    {
        String url = "https://io.adafruit.com/api/v2/PVPabloVZ/feeds/distancia/data/last?x-aio-key=aio_wHFL24lf1BNbZGgDSyXykR6aZr13";
        sensorid=findViewById(R.id.sensorid);
        pb=(ProgressBar)findViewById(R.id.progressBar);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {

                try {
                    String userid=response.getString("value");
                    sensorid.setText(userid);
                    Timer t= new Timer();
                    TimerTask tt = new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            counter= Integer.parseInt(userid);
                            pb.setProgress(counter);
                        }

                    };
                    t.schedule(tt,0,100);
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


























    ///EXTRAS
    /***
     *
     *
     *                Gson gson=new Gson();
     *                 Root respuesta=gson.fromJson(response.toString(),Root.class);
     *                 adapter = new AdaptadorHielera(respuesta.getUser());
     *                 recyclewview.setAdapter(adapter);
     *                  adafruituser.setText(respuesta.getUsername());
      *                 nombre=respuesta.getUsername();
      *
      *                  Toast.makeText(DatosCooler.this, ""+nombre, Toast.LENGTH_SHORT).show();
      *                   Properties properties =gson.fromJson(response.toString(),Properties.class);
      *                   adafruituser.setText(properties.getProperty("name"));
      *                 Toast.makeText(getApplicationContext(), "I am OK !" + response.toString(), Toast.LENGTH_LONG).show();
      *                  Hielera winner = gson.fromJson(response.toString(), Hielera.class);
      *                // adafruituser.setText(winner.getName());
      *
      */

    /*public void jsonRequest()
    {
        String url = "https://io.adafruit.com/api/v2/user?x-aio-key=aio_alPW01JJyo8JDI4hCrNcCuQpnDwN";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {

                recyclewview=findViewById(R.id.recyclew);
                recyclewview.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                //recyclewvieww.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclewview.setLayoutManager(layoutManager);

                Gson gson= new Gson();
                Root winner = gson.fromJson(response.toString(), Root.class);
              //  adapter = new AdaptadorHielera(winner.getName());



                recyclewview.setAdapter(adapter);
              }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);

    }*/
  /*  public void datosAdafruit()
    {
        String url = "https://io.adafruit.com/api/v2/user?x-aio-key=aio_wHFL24lf1BNbZGgDSyXykR6aZr13";
        adafruituser= findViewById(R.id.txtada);

        JsonArrayRequest peticion= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                recyclewview=findViewById(R.id.recyclew);
                recyclewview.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                //recyclewvieww.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclewview.setLayoutManager(layoutManager);

                Gson gson=new Gson();
                Root respuesta=gson.fromJson(response.toString(),Root.class);
                adapter = new AdaptadorHielera(respuesta.getUser());
                recyclewview.setAdapter(adapter);
                //adafruituser.setText(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                adafruituser.setText(error.toString());

            }
        });
        requestQueue.add(peticion);

    }
*/















    /*public void stringrequest()
    {

        String url = "https://io.adafruit.com/api/v2/user?x-aio-key=aio_nQAG347kXorcI4m1jTXf3EfwUhUY";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                adafruituser.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }*/

}




    /*


    public void progr()
    {
      pb=(ProgressBar)findViewById(R.id.progressBar);

        Timer t= new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run()
            {
             counter++;
             pb.setProgress(counter);
            }

        };
        t.schedule(tt,0,100);
    }*/