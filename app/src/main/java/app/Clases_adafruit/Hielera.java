package app.Clases_adafruit;

import android.content.Intent;

import java.io.Serializable;

public class Hielera implements Serializable
{
    public int id;
    public String name;
    public String key;
    public String visibility;
    private Intent accion;

    public Intent getAccion() {
        return accion;
    }

    public void setAccion(Intent accion) {
        this.accion = accion;
    }

    public Hielera(int id, String name,String key,String visibility)
    {
        this.id=id;
        this.name=name;
        this.key=key;
        this.visibility=visibility;
    }

    public Hielera() {

    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
