package app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import app.singleton.Singleton;

//ESTO ES UN FRAGMENTO ES UN POCO VISUALMENTE DIFERENTE A LOS ACTVIVITS NORMALES PERO FUNCIONA IGUAL
public class PerfilFragment extends Fragment {

    //LA VARIABLE PARA HACER LA PETICION
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        //DONDE HACEMOS LA PETICION
        requestQueue = Singleton.getInstance(getContext()).getRequestQueue();

        //AQUI CON EL VIEW SE CONSIGUE TODA LA INFORMACION DEL LAYOUT CONN EL VIEW SE INFLA UNA VISTA
        View view= inflater.inflate(R.layout.fragment_perfil, container, false);

        //AQUI SE CASTEAN LAS VARIABLES COMO VEN TIENEN VIEW PARA PODER CONSEGUIR YA QUE ASI ES EN  LO FRAGMENTS
        Button btnconectar=view.findViewById(R.id.conectaradafruit);
        TextView nombreuser=view.findViewById(R.id.nombreuser);

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