package entity;

import javax.persistence.*;

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

        if (id != that.id) return false;
        if (iscategory != that.iscategory) return false;
        if (prev != null ? !prev.equals(that.prev) : that.prev != null) return false;
        if (next != null ? !next.equals(that.next) : that.next != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (prev != null ? prev.hashCode() : 0);
        result = 31 * result + (next != null ? next.hashCode() : 0);
        result = 31 * result + (iscategory ? 1 : 0);
        return result;
    }
}
