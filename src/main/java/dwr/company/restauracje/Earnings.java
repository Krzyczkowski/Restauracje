package dwr.company.restauracje;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

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
        return Float.compare(earnings.earning, earning) == 0 && Objects.equals(dates, earnings.dates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dates, earning);
    }
}
