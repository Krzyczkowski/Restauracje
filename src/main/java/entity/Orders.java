package entity;

import javax.persistence.*;
import java.sql.Date;

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

        if (id != orders.id) return false;
        if (idemployee != orders.idemployee) return false;
        if (Float.compare(orders.totalprice, totalprice) != 0) return false;
        if (idclient != null ? !idclient.equals(orders.idclient) : orders.idclient != null) return false;
        if (dates != null ? !dates.equals(orders.dates) : orders.dates != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idemployee;
        result = 31 * result + (idclient != null ? idclient.hashCode() : 0);
        result = 31 * result + (totalprice != +0.0f ? Float.floatToIntBits(totalprice) : 0);
        result = 31 * result + (dates != null ? dates.hashCode() : 0);
        return result;
    }
}
