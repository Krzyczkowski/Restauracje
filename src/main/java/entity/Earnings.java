package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@SuppressWarnings("unchecked")
@Entity
public class Earnings {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "dates")
    private Date dates;
    @Basic
    @Column(name = "earning")
    private float earning;

    public String getRestuarant() {
        return restuarant;
    }

    public void setRestuarant(String restuarant) {
        this.restuarant = restuarant;
    }

    @Basic
    @Column(name = "restaurant")
    private String restuarant;
    public Earnings(){}
    public Earnings (JSONObject jo){
        this.dates = (Date) jo.get("dates");
        this.earning = (float) jo.get("earning");
        this.restuarant = jo.get("restaurant").toString();
    }
    public JSONObject toJSON (){
        JSONObject jo = new JSONObject();
        jo.put("dates",dates);
        jo.put("earning",earning);
        return jo;
    }

    public Date getDates() { return dates; }

    public void setDates(Date dates) {
        this.dates = dates;
    }

    public float getEarning() {
        return earning;
    }

    public void setEarning(float earning) {
        this.earning = earning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Earnings earnings = (Earnings) o;

        if (Float.compare(earnings.earning, earning) != 0) return false;
        return Objects.equals(dates, earnings.dates);
    }

    @Override
    public int hashCode() {
        int result = dates != null ? dates.hashCode() : 0;
        result = 31 * result + (earning != 0.0f ? Float.floatToIntBits(earning) : 0);
        return result;
    }
}
