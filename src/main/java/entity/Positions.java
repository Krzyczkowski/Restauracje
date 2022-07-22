package entity;

import org.hibernate.annotations.GenericGenerator;
import org.json.simple.JSONObject;

import javax.persistence.*;
@SuppressWarnings("unchecked")
@Entity
public class Positions {
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idorder")
    private int idorder;
    @Basic
    @Column(name = "idproduct")
    private int idproduct;
    @Basic
    @Column(name = "amount")
    private int amount;

///    public Positions(int id, int idorder, int idproduct, int amount) {
//        this.id = id;
//        this.idorder = idorder;
//        this.idproduct = idproduct;
//        this.amount = amount;
//    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Transient
    private String productName;

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    @Transient
    private float productPrice;

    public Positions(){}

///    public Positions(Integer idorder, Integer idproduct, Integer amount, Integer id){
//        this.idorder=idorder;
//        this.idproduct=idproduct;
//        this.amount=amount;
//        this.id=id;
//    }
    public Positions(JSONObject jo){
        this.idorder=(int)(long) jo.get("idorder");
        this.idproduct=(int)(long) jo.get("idproduct");
        this.amount=(int)(long) jo.get("amount");
        this.id=(int)(long) jo.get("id");
        this.productPrice= (float) (double) jo.get("productPrice");
        this.productName=jo.get("productName").toString();
    }

    public Positions(Integer idorder, Integer idproduct, Integer amount, Integer id, String productName, Float productPrice){
        this.idorder=idorder;
        this.idproduct=idproduct;
        this.amount=amount;
        this.id=id;
        this.productName=productName;
        this.productPrice=productPrice*amount;
    }
    public JSONObject toJSON(){
        JSONObject jo = new JSONObject();
        jo.put("idorder",idorder);
        jo.put("idproduct",idproduct);
        jo.put("amount",amount);
        jo.put("productName",productName);
        jo.put("productPrice",productPrice);
        jo.put("id",id);
        return jo;
    }
    public JSONObject toJSONU(){
        JSONObject jo = new JSONObject();
        jo.put("idorder",idorder);
        jo.put("idproduct",idproduct);
        jo.put("amount",amount);
        jo.put("productName",productName);
        jo.put("productPrice",productPrice);
        jo.put("id",id);
        return jo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdorder() {
        return idorder;
    }

    public void setIdorder(int idorder) {
        this.idorder = idorder;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Positions positions = (Positions) o;

        if (id != positions.id) return false;
        if (idorder != positions.idorder) return false;
        if (idproduct != positions.idproduct) return false;
        return amount == positions.amount;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idorder;
        result = 31 * result + idproduct;
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        return "Positions{" +
                "id=" + id +
                ", idorder=" + idorder +
                ", idproduct=" + idproduct +
                ", amount=" + amount +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }
}
