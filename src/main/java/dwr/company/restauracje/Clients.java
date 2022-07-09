package dwr.company.restauracje;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Clients {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "phone")
    private String phone;
    @Basic
    @Column(name = "address")
    private String address;

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
        return Objects.equals(phone, clients.phone) && Objects.equals(address, clients.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, address);
    }
}
