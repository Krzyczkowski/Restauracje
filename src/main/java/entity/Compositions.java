package entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Compositions {
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idproduct")
    private int idproduct;
    @Basic
    @Column(name = "iditem")
    private int iditem;
    @Basic
    @Column(name = "amount")
    private int amount;

    public Compositions(int id, int idproduct, int iditem, int amount) {
        this.id = id;
        this.idproduct = idproduct;
        this.iditem = iditem;
        this.amount = amount;
    }

    public Compositions() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
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

        Compositions that = (Compositions) o;

        if (id != that.id) return false;
        if (idproduct != that.idproduct) return false;
        if (iditem != that.iditem) return false;
        if (amount != that.amount) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idproduct;
        result = 31 * result + iditem;
        result = 31 * result + amount;
        return result;
    }
}
