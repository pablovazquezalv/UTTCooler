package app;

import android.os.AsyncTask;

import java.util.List;

import app.Clases_adafruit.BlockFeed;

public class Actualizar extends AsyncTask<Void, Void, List<BlockFeed>> {

    static final int DURACION = 3 * 1000; // 3 segundos de carga

    @Override
    protected List<BlockFeed> doInBackground(Void... voids)
    {
        return null;
    }
}
