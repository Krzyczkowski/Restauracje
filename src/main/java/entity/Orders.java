package entity;

import org.hibernate.annotations.GenericGenerator;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@SuppressWarnings("unchecked")
@Entity
public class Orders {
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
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

    public Orders(int id, int idemployee, String idclient, float totalprice, Date dates, String restaurant) {
        this.id = id;
        this.idemployee = idemployee;
        this.idclient = idclient;
        this.totalprice = totalprice;
        this.dates = dates;
        this.restaurant = restaurant;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }
    public Orders(){}

    public Orders(JSONObject jo) {
        id = (int) (long) jo.get("id");
        idemployee =(int) (long) jo.get("idemployee");
        idclient = jo.get("idclient" ).toString();
        totalprice = (float) (double) jo.get("totalprice");
        dates =Date.valueOf(jo.get("dates").toString()) ;
        restaurant=jo.get("restaurant").toString();
    }
    public JSONObject toJSON(){
        JSONObject jo = new JSONObject();
        jo.put("id",id);
        jo.put("idemployee",idemployee);
        jo.put("idclient",idclient);
        jo.put("totalprice",totalprice);
        jo.put("dates",dates.toString());
        jo.put("restaurant",restaurant);

        return jo;
    }

    @Basic
    @Column(name = "restaurant")
    private String restaurant ;


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
        if (!Objects.equals(idclient, orders.idclient)) return false;
        return Objects.equals(dates, orders.dates);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idemployee;
        result = 31 * result + (idclient != null ? idclient.hashCode() : 0);
        result = 31 * result + (totalprice != 0.0f ? Float.floatToIntBits(totalprice) : 0);
        result = 31 * result + (dates != null ? dates.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", idemployee=" + idemployee +
                ", idclient='" + idclient + '\'' +
                ", totalprice=" + totalprice +
                ", dates=" + dates +
                ", restaurant='" + restaurant + '\'' +
                '}';
    }
}
