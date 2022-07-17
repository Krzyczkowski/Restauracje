package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
public class Categories {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "restaurant")
    private String restaurant;
    public Categories(JSONObject jo) {
        id = jo.get("id").toString();
        restaurant = jo.get("restaurant").toString();
    }

    public Categories() {
    }

    public String getId() {
        return id;
    }
    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categories that = (Categories) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    public JSONObject toJSON(){
        JSONObject jo = new JSONObject();
        jo.put("id",id);
        jo.put("restuarant",restaurant);
        return jo;
    }
}
