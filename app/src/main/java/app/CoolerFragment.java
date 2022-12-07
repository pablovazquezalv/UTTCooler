package app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import app.Adaptadores.AdaptadorHielera;
import app.Clases_adafruit.Hielera;
import app.singleton.Singleton;


public class CoolerFragment extends Fragment
{
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private List<Hielera> hieleraList;
    TextView txtdatos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //INFLACION DEL FRAGMENTO PARA PODER TRABAJAR
        View view= inflater.inflate(R.layout.fragment_cooler, container, false);
        Button btnhielera=view.findViewById(R.id.hielera);

        //CREACION DE LA PETICION Y DE LA LISTA
        requestQueue = Singleton.getInstance(view.getContext()).getRequestQueue();
        hieleraList = new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);
        ObtenerHieleras();


        //BOTON DE IR A AGREGAR UNA HIELERA NUEVA
        btnhielera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),AgregarNuevaHielera.class);
                startActivity(i);
            }
        });
        return view;
    }



    //METODO PARA OBTENER HIELERAS
    public void ObtenerHieleras()
    {
        String urldashboard="https://io.adafruit.com/api/v2/PVPabloVZ/dashboards";

        JsonArrayRequest request = new JsonArrayRequest(urldashboard, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                JSONObject jsonObject;
                for(int i=0;i<response.length();i++)
                {
                    try
                    {
                        jsonObject=response.getJSONObject(i);
                        Hielera hielerass = new Hielera();
                        jsonObject.getString("id");
                        hielerass.setName(jsonObject.getString("name"));
                      //  hielerass.setName(jsonObject.getString("visibility"));
                        hieleraList.add(hielerass);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

                setRecyclewView(hieleraList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-AIO-Key", "aio_TXSJ47NEPecX4kB7T4556z8VhCdd");

                return headers;
            }

        };
        requestQueue.add(request);

    }

    //INSTANCIAR RECYLCEW VIEW CON ADAPTADOR
    private void setRecyclewView(List<Hielera> hieleraList)
    {
        AdaptadorHielera adaptadorHielera = new AdaptadorHielera(getContext(),hieleraList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adaptadorHielera);
    }


}

///BASURA
    /*OBTENER HIELERAS
    public  void ObtenerDatosHielera()
    {
        String urldashboard="https://io.adafruit.com/api/v2/PVPabloVZ/dashboards?x-aio-key=aio_eGfl04aPeZtfA5xEUNezljbhwgPk";

        JsonObjectRequest JSON = new JsonObjectRequest(Request.Method.GET, urldashboard, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    String userids=response.getString("username");
                    txtdatos.setText(userids);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtdatos.setText(error.toString());
                //  error.networkResponse()

            }
        });
        requestQueue.add(JSON);


    }*/