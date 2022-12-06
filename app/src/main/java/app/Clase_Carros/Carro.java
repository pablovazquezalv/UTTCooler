package app.Clase_Carros;

public class Carro {

    public String name;
    public String description;
    public String user;
    public String type_car;
    public String group;

    public Carro(String name, String description, String user, String type_car, String group) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.type_car = type_car;
        this.group = group;
    }

    public Carro(String hielera, String description, String user, String type_car) {

    }


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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType_car() {
        return type_car;
    }

    public void setType_car(String type_car) {
        this.type_car = type_car;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}

