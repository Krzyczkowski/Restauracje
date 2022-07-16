package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
public class Products {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "category")
    private String category;
    @Basic
    @Column(name = "price")
    private float price;
    public Products(){}
    public Products(JSONObject jo) {
        id = (int) (long) jo.get("id");
        name = jo.get("name").toString();
        category = jo.get("category").toString();
        price = (float) (double) jo.get("price");
    }
    public JSONObject toJSON(){
        JSONObject jo = new JSONObject();
        jo.put("id",id);
        jo.put("category",category);
        jo.put("price",price);
        jo.put("name",name);
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Products products = (Products) o;

        if (id != products.id) return false;
        if (category != products.category) return false;
        if (price != products.price) return false;
        if (name != null ? !name.equals(products.name) : products.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category !=null ? category.hashCode():0);
        return result;
    }
}
