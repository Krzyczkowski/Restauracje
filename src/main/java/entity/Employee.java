package entity;

import org.hibernate.annotations.GenericGenerator;
import org.json.simple.JSONObject;

import javax.persistence.*;


import static java.lang.Integer.parseInt;

@Entity
public class Employee  {
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "lastname")
    private String lastname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;
        if (lastname != null ? !lastname.equals(employee.lastname) : employee.lastname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }
    public JSONObject toJSON() {

        JSONObject jo = new JSONObject();
        jo.put("id", id);
        jo.put("name", name);
        jo.put("lastname", lastname);
        return jo;
    }
    public void JSONtoEmployee( JSONObject jo) {
        this.id =  (int) (long) jo.get("id");
        this.name = (String) jo.get("name");
        this.lastname = (String) jo.get("lastname");
    }

    public Employee(JSONObject jo) {
        this.id =  (int) (long) jo.get("id");
        this.name = (String) jo.get("name");
        this.lastname = (String) jo.get("lastname");
    }
    public Employee() {
    }

    public Employee(String n, String s){
        this.id = 11;
        this.name = n;
        this.lastname = s;
    }
}
