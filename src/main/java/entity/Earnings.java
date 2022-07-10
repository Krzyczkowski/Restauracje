package entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Earnings {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "dates")
    private Date dates;
    @Basic
    @Column(name = "earning")
    private float earning;

    public Date getDates() {
        return dates;
    }

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
        if (dates != null ? !dates.equals(earnings.dates) : earnings.dates != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dates != null ? dates.hashCode() : 0;
        result = 31 * result + (earning != +0.0f ? Float.floatToIntBits(earning) : 0);
        return result;
    }
}
