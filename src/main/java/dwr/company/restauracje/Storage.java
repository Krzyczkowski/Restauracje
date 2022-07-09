package dwr.company.restauracje;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Storage {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "amount")
    private int amount;
    @Basic
    @Column(name = "name")
    private String name;

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
        return id == storage.id && amount == storage.amount && Objects.equals(name, storage.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, name);
    }
}
