package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

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
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);
        return result;
    }
}
