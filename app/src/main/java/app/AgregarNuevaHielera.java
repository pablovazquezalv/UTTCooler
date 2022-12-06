package app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import app.Adaptadores.AdaptadorCarro;
import app.Adaptadores.AdaptadorHielera;
import app.Clase_Carros.Carro;
import app.Clases_adafruit.Hielera;
import app.singleton.Singleton;

public class AgregarNuevaHielera extends AppCompatActivity {

    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private List<Carro> carroList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nueva_hielera);

        requestQueue = Singleton.getInstance(AgregarNuevaHielera.this).getRequestQueue();
        carroList = new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        setRecyclewView();
        setInfo();
    }




    //INSTANCIAR RECYLCEW VIEW CON ADAPTADOR
    private void setRecyclewView()
    {
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        AdaptadorCarro adaptadorCarro = new AdaptadorCarro(carroList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adaptadorCarro);
    }


    public void setInfo()
    {
        carroList.add(new Carro("Hielera","TOYOTA","1","1"));
         carroList.add(new Carro("Hielera","null","1","1"));
    }
}