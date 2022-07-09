package dwr.company.restauracje;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Compositions {
    @Basic
    @Column(name = "idproduct")
    private int idproduct;
    @Basic
    @Column(name = "iditem")
    private int iditem;
    @Basic
    @Column(name = "amount")
    private int amount;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compositions that = (Compositions) o;
        return idproduct == that.idproduct && iditem == that.iditem && amount == that.amount && id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idproduct, iditem, amount, id);
    }
}
