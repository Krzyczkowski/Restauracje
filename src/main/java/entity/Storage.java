package entity;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.hibernate.annotations.GenericGenerator;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Objects;

@SuppressWarnings("unchecked")
@Entity
public class Storage {
    @GenericGenerator(name="agent" , strategy="increment")
    @GeneratedValue(generator="agent")
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "amount")
    private int amount;
    @Basic
    @Column(name = "name")
    private String name;

    public Integer getAmountForNewProduct() {
        return amountForNewProduct;
    }

    public void setAmountForNewProduct(Integer amountForNewProduct) {
        this.amountForNewProduct = amountForNewProduct;
    }

    @Transient
    private Integer amountForNewProduct;

    public TextField getAmountOfIngridientInProduct() {
        return amountOfIngridientInProduct;
    }

    public void setAmountOfIngridientInProduct(TextField amountOfIngridientInProduct) {
        this.amountOfIngridientInProduct = amountOfIngridientInProduct;
    }

    @Transient
    @FXML
    TextField amountOfIngridientInProduct; // for fxml table in creation of product


    @Basic
    @Column(name = "restaurant")
    private String restaurant;
    public Storage(){

        amount = 0;
        name = "brak";
        id = 0;
        restaurant="0";
    }
    public Storage(Storage st){
        amount = st.getAmount();
        name = st.getName();
        id = st.getId();
        restaurant = st.getRestaurant();
    }
    public Storage(String name, int amount){this.id=0;this.name=name;this.amount=amount;}

///    public Storage(JSONObject jo,String s) {
//        amount =(int)(long) jo.get("amount");
//        name =jo.get("name").toString();
//    }
    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

        Storage storage = (Storage) o;

        if (id != storage.id) return false;
        if (amount != storage.amount) return false;
        return Objects.equals(name, storage.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + amount;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
    public Storage(JSONObject jo) {
        id =    (int)(long) jo.get("id");
        amount =(int)(long) jo.get("amount");
        name =jo.get("name").toString();
        restaurant = jo.get("restaurant").toString();
    }
    public JSONObject toJSON(){
        JSONObject jo = new JSONObject();
        jo.put("id",id);
        jo.put("name",name);
        jo.put("amount",amount);
        jo.put("restaurant",restaurant);
        return jo;
    }
    public JSONObject toJSONI(){
        JSONObject jo = new JSONObject();
        jo.put("name",name);
        jo.put("amount",amount);
        return jo;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", amountForNewProduct=" + amountForNewProduct +
                ", amountOfIngridientInProduct=" + amountOfIngridientInProduct +
                ", restaurant='" + restaurant + '\'' +
                '}';
    }
}
