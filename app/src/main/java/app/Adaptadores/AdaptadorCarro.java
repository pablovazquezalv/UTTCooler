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

    public AdaptadorCarro(List<Carro> datoscarros)
    {
      //  this.context=context;
        this.datoscarros=datoscarros;
    }

    @NonNull
    @Override
    public AdaptadorCarro.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_agregarcarrito, parent, false);
        return new AdaptadorCarro.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCarro.MyViewHolder holder, int position) {
     //   holder.setData(datoscarros.get(position));
        String name=datoscarros.get(position).getName();
        holder.titulocarro.setText(name);
    }

    @Override
    public int getItemCount() {
        return datoscarros.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        RadioButton tipocarro;
        TextView titulocarro,sensores;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tipocarro=itemView.findViewById(R.id.tipocarro);
            titulocarro=itemView.findViewById(R.id.nombrehielera);
            sensores=itemView.findViewById(R.id.sensores);
        }
      /*  public void setData(Carro carro)
        {
            tipocarro.setText(carro.getType_car());
            sensores.setText(carro.getGroup());
            titulocarro.setText(carro.getName());
        }*/
    }
}
