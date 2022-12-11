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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import app.singleton.Singleton;


public class PerfilFragment extends Fragment {

    private RequestQueue requestQueue;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final String SHARE_PREF_KEY="mypref";
    private static final  String KEY_NAME="name";
    private static final  String KEY_ID="id";
    private static final String KEY_EMAIL="email";
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

        preferences= getActivity().getSharedPreferences(SHARE_PREF_KEY,Context.MODE_PRIVATE);
        String name= preferences.getString(KEY_NAME,null);

        nombreuser.setText("Hola "+name+" !");
        editor=preferences.edit();

        btncerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("sesion",false);
                editor.apply();
                Toast.makeText(getContext(), "la sesion fue cerrada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(),Login.class));

            }
        });





        String url="https://io.adafruit.com/api/v2/user?x-aio-key=aio_nQAG347kXorcI4m1jTXf3EfwUhUY";

     JsonObjectRequest peticion = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(peticion);



        btnconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),ConectarAdafruit.class);
                startActivity(i);
            }
        });
        return view;
    }

}