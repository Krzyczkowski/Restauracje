package entity;

import org.hibernate.annotations.GenericGenerator;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Objects;

@SuppressWarnings("unchecked")
@Entity
public class Logins {
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
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
    @Column(name = "restaurantname")
    private String restaurantname;
    @Basic
    @Column(name = "pesel")
    private Integer pesel;
    @Basic
    @Column(name = "salary")
    private float salary;
    @OneToOne
    @JoinColumn(name = "id")
    private Employee emp;
    public Logins(int id, String login, String password, int levelaccess, String restaurantname, Integer pesel, float salary,String name, String lastName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.levelaccess = levelaccess;
        this.restaurantname = restaurantname;
        this.pesel = pesel;
        this.salary = salary;
        emp=new Employee();
        this.emp.setId(id);
        this.emp.setLastname(lastName);
        this.emp.setName(name);
    }

    public Logins(){}






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

    public Integer getLevelaccess() {
        return levelaccess;
    }

    public void setLevelaccess(int levelaccess) {
        this.levelaccess = levelaccess;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
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

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public String getName(){return emp.getName();}
    public String getLastname(){return emp.getLastname();}
    public void setName(String s){
        emp.setName(s);
    }
    public void setLastName(String s){
        emp.setLastname(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Logins logins = (Logins) o;

        if (id != logins.id) return false;
        if (levelaccess != logins.levelaccess) return false;
        if (!Objects.equals(restaurantname, logins.restaurantname)) return false;
        if (Float.compare(logins.salary, salary) != 0) return false;
        if (!Objects.equals(login, logins.login)) return false;
        if (!Objects.equals(password, logins.password)) return false;
        return Objects.equals(pesel, logins.pesel);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + levelaccess;
        result = 31 * result +  (restaurantname != null ? restaurantname.hashCode() : 0);
        result = 31 * result + (pesel != null ? pesel.hashCode() : 0);
        result = 31 * result + (salary != 0.0f ? Float.floatToIntBits(salary) : 0);
        return result;
    }
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("id", id);
        jo.put("name", emp.getName());
        jo.put("lastname", emp.getLastname());
        jo.put("login",login);
        jo.put("password", password);
        jo.put("pesel", pesel);
        jo.put("levelaccess", levelaccess);
        jo.put("salary", salary);
        jo.put("restaurantname", restaurantname);
        return jo;
    }
    public Logins(JSONObject jo) {
        this.id =  (int) (long) jo.get("id");
        this.login = (String) jo.get("login");
        this.password = (String) jo.get("password");
        this.levelaccess = (int) (long) jo.get("levelaccess");
        this.restaurantname = jo.get("restaurantname").toString();
        this.pesel  = (int) (long) jo.get("pesel");
        this.salary  = (float)(double) jo.get("salary");
        emp=new Employee();
        this.emp.setId(id);
        this.emp.setLastname((String) jo.get("lastname"));
        this.emp.setName((String) jo.get("name"));
    }


    @Override
    public String toString() {
        return "Logins{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", levelaccess=" + levelaccess +
                ", restaurantname='" + restaurantname + '\'' +
                ", pesel=" + pesel +
                ", salary=" + salary +
                ", emp=" + emp +
                '}';
    }
}
