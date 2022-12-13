package app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.singleton.Singleton;


public class PerfilFragment extends Fragment {

    private RequestQueue requestQueue;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String SHARE_PREF_KEY="mypref";
    private static final  String KEY_NAME="name";
    private static final String KEY_ID="id";
    private static final  String KEY_USERADAFRUIT="useradafruit";
    private static final String KEY_IOKEY="iokey";
    private static final String KEY_TOKEN="token";


    String nombren;
    String telefono;
    String Active_Key;
    String Username;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        requestQueue = Singleton.getInstance(getContext()).getRequestQueue();

        View view= inflater.inflate(R.layout.fragment_perfil, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Button btnconectar=view.findViewById(R.id.conectaradafruit);
        Button btncerrarsesion = view.findViewById(R.id.cerrarsesion);

        TextView nombreuser=view.findViewById(R.id.nombreuser);
        TextView iokey=view.findViewById(R.id.ioKeyuser);
        TextView user=view.findViewById(R.id.usuarioada);

        preferences= getActivity().getSharedPreferences(SHARE_PREF_KEY,Context.MODE_PRIVATE);
        String id= preferences.getString(KEY_ID,null);
        String token= preferences.getString(KEY_TOKEN,null);
        editor=preferences.edit();


        //DATOS DEL USUARIO
        String url="https://gallant-fermat.143-198-158-11.plesk.page/api/users/"+id;
     JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
     {
            @Override
            public void onResponse(JSONObject response)
            {
                try {

                     nombren =response.getJSONObject("data").getString("name");
                    nombreuser.setText("Hola "+nombren+" !");

                     Active_Key =response.getJSONObject("data").getString("Active_Key");
                    iokey.setText(""+Active_Key);
                     Username =response.getJSONObject("data").getString("Username");
                    user.setText(""+Username);

                    editor.putString(KEY_USERADAFRUIT,Username);
                    editor.putString(KEY_IOKEY,Active_Key);
                    editor.apply();
                  //  Toast.makeText(getContext(), "datos"+Username, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
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

        requestQueue.add(peticion);



        btnconectar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),ConectarAdafruit.class);
                startActivity(i);
            }
        });

        btncerrarsesion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editor.putBoolean("sesion",false);
                editor.apply();
//                Toast.makeText(getContext(), "la sesion fue cerrada", Toast.LENGTH_SHORT).show();


                String urlcerraresion="https://gallant-fermat.143-198-158-11.plesk.page/api/out";

                JsonObjectRequest peticion= new JsonObjectRequest(Request.Method.POST, urlcerraresion, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        int status= 0;
                        try {
                            status = Integer.parseInt(response.getString("status"));
                            if(status==200)
                                Toast.makeText(getContext(), "adios", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getActivity(),Login.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERORR"+error, Toast.LENGTH_SHORT).show();

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
                requestQueue.add(peticion);
            }
        });
        return view;
    }

}