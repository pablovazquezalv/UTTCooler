package app.Clases_adafruit;

import android.content.Intent;

import java.io.Serializable;

public class Hielera implements Serializable
{
    public String name;
    public String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
