package entity;

import javax.persistence.*;

@Entity
public class Logins {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "levelaccess")
    private int levelaccess;
    @Basic
    @Column(name = "idrestaurant")
    private int idrestaurant;
    @Basic
    @Column(name = "pesel")
    private Integer pesel;
    @Basic
    @Column(name = "salary")
    private float salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevelaccess() {
        return levelaccess;
    }

    public void setLevelaccess(int levelaccess) {
        this.levelaccess = levelaccess;
    }

    public int getIdrestaurant() {
        return idrestaurant;
    }

    public void setIdrestaurant(int idrestaurant) {
        this.idrestaurant = idrestaurant;
    }

    public Integer getPesel() {
        return pesel;
    }

    public void setPesel(Integer pesel) {
        this.pesel = pesel;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Logins logins = (Logins) o;

        if (id != logins.id) return false;
        if (levelaccess != logins.levelaccess) return false;
        if (idrestaurant != logins.idrestaurant) return false;
        if (Float.compare(logins.salary, salary) != 0) return false;
        if (login != null ? !login.equals(logins.login) : logins.login != null) return false;
        if (password != null ? !password.equals(logins.password) : logins.password != null) return false;
        if (pesel != null ? !pesel.equals(logins.pesel) : logins.pesel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + levelaccess;
        result = 31 * result + idrestaurant;
        result = 31 * result + (pesel != null ? pesel.hashCode() : 0);
        result = 31 * result + (salary != +0.0f ? Float.floatToIntBits(salary) : 0);
        return result;
    }
}
