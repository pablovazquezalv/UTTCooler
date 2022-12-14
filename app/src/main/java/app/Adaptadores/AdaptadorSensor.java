package app.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.UTTCOOLER.Integradora.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.lzyzsd.circleprogress.DonutProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.Clases_adafruit.BlockFeed;
import app.HieleraInformacion;

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

         TextView txtsensor,txtdatos,txtled;
         Button button;
        DonutProgress donutProgress;


        public MyViewHolder(final View itemView) {
            super(itemView);
           txtsensor=itemView.findViewById(R.id.titulosensor);
           txtdatos=itemView.findViewById(R.id.txtdatos);
           donutProgress=itemView.findViewById(R.id.donaprogresosadafruit);

        }

        public void setData(BlockFeed sensores)
        {
            txtsensor.setText(sensores.getName());
            donutProgress.setText(sensores.getValue());
            txtdatos.setText(sensores.getValue());
           String valor=sensores.getValue();
             donutProgress.setDonut_progress(valor);
        }
    }
}
