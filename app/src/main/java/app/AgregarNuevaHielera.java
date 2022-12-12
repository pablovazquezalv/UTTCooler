package app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.UTTCOOLER.Integradora.R;
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
import java.util.List;

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



        botonhielera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombrecarro=inputnombre.getText().toString().trim();
                String seleccionado=spinner.getSelectedItem().toString();
                if(seleccionado.equals("Hielera"))
                {
                    int tipo=1;
                }
                if(seleccionado.equals("Aspiradora"))
                {
                    int tipo=2;
                }
                if(seleccionado.equals("Basurero"))
                {
                    int tipo=3;
                }


                String urlgroup="https://gallant-fermat.143-198-158-11.plesk.page/api/group";

                JSONObject jsonbody= new JSONObject();
                try {
                    jsonbody.put("name",inputnombre.getText());

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlgroup, jsonbody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });

                requestQueue.add(request);


            }
        });



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