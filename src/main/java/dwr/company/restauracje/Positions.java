package dwr.company.restauracje;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Positions {
    @Basic
    @Column(name = "idorder")
    private int idorder;
    @Basic
    @Column(name = "idproduct")
    private int idproduct;
    @Basic
    @Column(name = "amount")
    private int amount;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

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
        Positions positions = (Positions) o;
        return idorder == positions.idorder && idproduct == positions.idproduct && amount == positions.amount && id == positions.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idorder, idproduct, amount, id);
    }
}
