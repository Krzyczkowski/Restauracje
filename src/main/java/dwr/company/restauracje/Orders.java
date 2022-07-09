package dwr.company.restauracje;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "idemployee")
    private int idemployee;
    @Basic
    @Column(name = "idclient")
    private String idclient;
    @Basic
    @Column(name = "totalprice")
    private float totalprice;
    @Basic
    @Column(name = "dates")
    private Date dates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdemployee() {
        return idemployee;
    }

    public void setIdemployee(int idemployee) {
        this.idemployee = idemployee;
    }

    public String getIdclient() {
        return idclient;
    }

    public void setIdclient(String idclient) {
        this.idclient = idclient;
    }

    public float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }

    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id == orders.id && idemployee == orders.idemployee && Float.compare(orders.totalprice, totalprice) == 0 && Objects.equals(idclient, orders.idclient) && Objects.equals(dates, orders.dates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idemployee, idclient, totalprice, dates);
    }
}
