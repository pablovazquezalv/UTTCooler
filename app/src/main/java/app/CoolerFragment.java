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
import app.Clases_adafruit.BlockFeed;
import app.Clases_adafruit.Hielera;
import app.Clases_adafruit.ListaHielera;
import app.singleton.Singleton;


public class CoolerFragment extends Fragment
{
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private List<Hielera> hieleraList;
    AdaptadorHielera adapter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String SHARE_PREF_KEY="mypref";
    private static final String KEY_ID="id";
    private static final  String KEY_USERADAFRUIT="useradafruit";
    private static final String KEY_IOKEY="iokey";
    private static final String KEY_GROUP="namegroup";
    private static final String KEY_SENSOR="idsensor";
    private static final String KEY_TOKEN="token";



    String iokey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view= inflater.inflate(R.layout.fragment_cooler, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        preferences= getActivity().getSharedPreferences(SHARE_PREF_KEY, Context.MODE_PRIVATE);
        String usernameadafruit= preferences.getString(KEY_USERADAFRUIT,null);
        String iokey= preferences.getString(KEY_IOKEY,null);
        String id= preferences.getString(KEY_ID,null);
        String token= preferences.getString(KEY_TOKEN,null);
        editor=preferences.edit();

        //Toast.makeText(getContext(), "username:"+usernameadafruit+iokey, Toast.LENGTH_SHORT).show();

        Button btnhielera=view.findViewById(R.id.hielera);
        requestQueue = Singleton.getInstance(view.getContext()).getRequestQueue();
        hieleraList = new ArrayList<>();

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false));

        ObtenerHieleras(usernameadafruit,iokey,id,token);


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
    public void ObtenerHieleras(String usernameadafruit,String iokey,String id,String token)
    {
        String urldashboard="https://gallant-fermat.143-198-158-11.plesk.page/api/gruposs/"+id;

       // Toast.makeText(getContext(), "ID"+id, Toast.LENGTH_SHORT).show();
        JsonArrayRequest request= new JsonArrayRequest(Request.Method.GET, urldashboard, null, new Response.Listener<JSONArray>() {
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
                        hielerass.setName(jsonObject.getString("name"));
                        String nombregrupo=jsonObject.getString("name");
                        String idsensor=jsonObject.getString("id");
                        editor.putString(KEY_SENSOR,idsensor);
                        editor.putString(KEY_GROUP,nombregrupo);
                        editor.apply();
                        hieleraList.add(hielerass);
                        setRecyclewView(hieleraList);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
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
                headers.put("Authorization","Bearer "+token);
                return headers;
            }
        };
        requestQueue.add(request);
    }
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