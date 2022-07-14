package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
public class Restaurants {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;

public Restaurants(){}
    public Restaurants(JSONObject jo){
        this.id = (int) (long) jo.get("id");
        this.name = (String) jo.get("name");
    }
    public JSONObject toJSON (){
        JSONObject jo = new JSONObject();
        jo.put("id", id);
        jo.put("name", name);
        return jo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurants that = (Restaurants) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
