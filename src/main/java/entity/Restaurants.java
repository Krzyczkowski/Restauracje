package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Objects;

@SuppressWarnings("unchecked")
@Entity
public class Restaurants {

    @Id
    @Column(name = "name")
    private String name;

public Restaurants(){}
    public Restaurants(JSONObject jo){
        this.name = (String) jo.get("name");
    }
    public JSONObject toJSON (){
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        return jo;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurants that = (Restaurants) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return (name != null ? name.hashCode() : 0);
    }
}
