package dwr.company.restauracje;

import javax.persistence.*;
import java.util.Objects;

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
        return id == logins.id && levelaccess == logins.levelaccess && idrestaurant == logins.idrestaurant && Float.compare(logins.salary, salary) == 0 && Objects.equals(login, logins.login) && Objects.equals(password, logins.password) && Objects.equals(pesel, logins.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, levelaccess, idrestaurant, pesel, salary);
    }
}
