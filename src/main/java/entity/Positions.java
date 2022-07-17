package entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private String productName;

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    private float productPrice;

    public Positions(){}

    public Positions(Integer idorder, Integer idproduct, Integer amount, Integer id){
        this.idorder=idorder;
        this.idproduct=idproduct;
        this.amount=amount;
        this.id=id;
    }
    public Positions(Integer idorder, Integer idproduct, Integer amount, Integer id, String productName, Float productPrice){
        this.idorder=idorder;
        this.idproduct=idproduct;
        this.amount=amount;
        this.id=id;
        this.productName=productName;
        this.productPrice=productPrice*amount;
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
        if (amount != positions.amount) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idorder;
        result = 31 * result + idproduct;
        result = 31 * result + amount;
        return result;
    }
}
