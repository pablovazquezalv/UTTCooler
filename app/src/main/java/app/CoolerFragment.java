package app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.google.gson.Gson;
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
import app.Clases_adafruit.ListaHielera;
import app.singleton.Singleton;


public class CoolerFragment extends Fragment
{
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private List<Hielera> hieleraList;
    private List<ListaHielera> listaHieleras;
    AdaptadorHielera adapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String SHARE_PREF_KEY="mypref";
    private static final  String KEY_USERADAFRUIT="useradafruit";
    private static final String KEY_IOKEY="iokey";


    String iokey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view= inflater.inflate(R.layout.fragment_cooler, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        preferences= getActivity().getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        String usernameadafruit= preferences.getString(KEY_USERADAFRUIT,null);
        String iokey= preferences.getString(KEY_IOKEY,null);
        editor=preferences.edit();

        Toast.makeText(getContext(), "username:"+usernameadafruit+iokey, Toast.LENGTH_SHORT).show();

        Button btnhielera=view.findViewById(R.id.hielera);
        requestQueue = Singleton.getInstance(view.getContext()).getRequestQueue();
        hieleraList = new ArrayList<>();
        listaHieleras= new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false));

        ObtenerHieleras(usernameadafruit,iokey);


        //BOTON DE IR A AGREGAR UNA HIELERA NUEVA
        btnhielera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i= new Intent(getActivity(),AgregarNuevaHielera.class);
                startActivity(i);
            }
        });
        return view;
    }

    //METODO PARA OBTENER HIELERAS
    public void ObtenerHieleras(String usernameadafruit,String iokey)
    {
        String urldashboard="https://gallant-fermat.143-198-158-11.plesk.page/api/cars";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urldashboard, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Gson gson =new Gson();
                ListaHielera winner=gson.fromJson(response.toString(),ListaHielera.class);
                adapter= new AdaptadorHielera(getContext(),winner.getData());
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
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