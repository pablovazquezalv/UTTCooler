package app.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.UTTCOOLER.Integradora.R;

import java.util.List;

import app.Clases_adafruit.Hielera;
import app.ConectarAdafruit;
import app.HieleraInformacion;
import app.Login;

public class AdaptadorHielera extends RecyclerView.Adapter<AdaptadorHielera.MyViewHolder> {

     private Context context;
     private List<Hielera> datoshielera;


    public AdaptadorHielera(Context context,List<Hielera> datoshielera)
    {
        this.context=context;
        this.datoshielera=datoshielera;
    }

    @NonNull
    @Override
    public AdaptadorHielera.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hielera, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorHielera.MyViewHolder holder, int position) {
      final Hielera item=datoshielera.get(position);
        holder.setData(datoshielera.get(position));

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, HieleraInformacion.class);
                 i.putExtra("datos",item);

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datoshielera.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private Button name;
        ConstraintLayout view_container;


        public MyViewHolder(final View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.idnombreadafruit);
            view_container=itemView.findViewById(R.id.contenedor);

        }

        public void setData(Hielera hielera)
        {
            name.setText(hielera.getName());
        }
    }
}

