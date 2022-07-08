package dwr.company.restauracje;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Categories {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "prev")
    private String prev;
    @Basic
    @Column(name = "next")
    private String next;
    @Basic
    @Column(name = "iscategory")
    private boolean iscategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public boolean isIscategory() {
        return iscategory;
    }

    public void setIscategory(boolean iscategory) {
        this.iscategory = iscategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return id == that.id && iscategory == that.iscategory && Objects.equals(prev, that.prev) && Objects.equals(next, that.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prev, next, iscategory);
    }
}
