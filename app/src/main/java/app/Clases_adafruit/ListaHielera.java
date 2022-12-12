package app.Clases_adafruit;

import java.util.List;

public class ListaHielera {

    public String status;
    public List<Hielera> data;

    public ListaHielera(String status, List<Hielera> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Hielera> getData() {
        return data;
    }

    public void setData(List<Hielera> data) {
        this.data = data;
    }
}
