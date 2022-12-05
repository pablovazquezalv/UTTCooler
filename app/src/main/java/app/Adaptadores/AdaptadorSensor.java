package app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.UTTCOOLER.Integradora.R;

import java.util.List;

import app.Clases_adafruit.BlockFeed;
import app.Clases_adafruit.Feed;

public class AdaptadorSensor extends RecyclerView.Adapter<AdaptadorSensor.MyViewHolder> {

    private Context context;
    private List<BlockFeed> datossensores;


    public AdaptadorSensor(Context context,List<BlockFeed> datossensores)
    {

        this.datossensores=datossensores;
    }


    @NonNull
    @Override
    public AdaptadorSensor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sensores, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorSensor.MyViewHolder holder, int position) {
        holder.setData(datossensores.get(position));
    }

    @Override
    public int getItemCount() {
        return datossensores.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

         TextView txtsensor;
      //   DonutProgress donutProgress;


        public MyViewHolder(final View itemView) {
            super(itemView);
           txtsensor=itemView.findViewById(R.id.titulosensor);
            //donutProgress=itemView.findViewById(R.id.donaprogresosadafruit);

        }
        public void setData(BlockFeed sensores)
        {

            txtsensor.setText(sensores.getName());
            //donutProgress.setDonut_progress(sensores.getLast_value());
        }
    }
}
