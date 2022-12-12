package app.Clase_Carros;

import java.util.List;

public class ListaCarro
{
    public String status;
    public List<Carro> data;

    public ListaCarro(String status, List<Carro> data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<Carro> getData()
    {
        return data;
    }

    public void setData(List<Carro> data)
    {
        this.data = data;
    }
}
