package app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.UTTCOOLER.Integradora.R;

import java.util.ArrayList;
import java.util.List;

import app.Clase_Carros.Carro;
import app.Clases_adafruit.Hielera;

public class AdaptadorCarro extends RecyclerView.Adapter<AdaptadorCarro.MyViewHolder>{

    private Context context;
    private List<Carro> datoscarros;

    public AdaptadorCarro(Context context,List<Carro> datoscarros)
    {
        this.context=context;
        this.datoscarros=datoscarros;
    }

    @NonNull
    @Override
    public AdaptadorCarro.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_agregarcarrito, parent, false);
        return new AdaptadorCarro.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCarro.MyViewHolder holder, int position)
    {
        holder.setData(datoscarros.get(position));
    }

    @Override
    public int getItemCount() {
        return datoscarros.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView nombrehielera,sensores;
        public MyViewHolder(final View itemView)
        {
            super(itemView);
            nombrehielera=itemView.findViewById(R.id.nombrehielera);
            sensores=itemView.findViewById(R.id.sensores);
        }
       public void setData(Carro carro)
        {
            nombrehielera.setText(carro.getId());
            sensores.setText(carro.getName());
        }
    }
}
