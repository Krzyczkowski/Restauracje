package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Objects;

@SuppressWarnings("unchecked")
@Entity
public class Clients {

    @Id
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "address")
    private String address;

    public Clients(){}
    public Clients(JSONObject jo){
        this.phone = (String) jo.get("phone");
        this.address = (String) jo.get("address");
    }
    public JSONObject toJSON (){
        JSONObject jo = new JSONObject();
        jo.put("phone", phone);
        jo.put("address", address);
        return jo;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Clients clients = (Clients) o;

        if (!Objects.equals(phone, clients.phone)) return false;
        return Objects.equals(address, clients.address);
    }

    @Override
    public int hashCode() {
        int result = phone != null ? phone.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
