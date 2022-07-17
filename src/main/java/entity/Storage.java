package entity;

import org.hibernate.annotations.GenericGenerator;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
public class Storage {
    @GenericGenerator(name="agent" , strategy="increment")
    @GeneratedValue(generator="agent")
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "amount")
    private int amount;
    @Basic
    @Column(name = "name")
    private String name;

    public Storage(){

        amount = 0;
        name = "brak";
        id = 0;
    }
    public Storage(String name, int amount){this.id=0;this.name=name;this.amount=amount;}
    public Storage(JSONObject jo) {
        id =0;
        amount =(int)(long) jo.get("amount");
        name =jo.get("name").toString();
    }
    public Storage(JSONObject jo,String s) {
        id=
        amount =(int)(long) jo.get("amount");
        name =jo.get("name").toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Storage storage = (Storage) o;

        if (id != storage.id) return false;
        if (amount != storage.amount) return false;
        if (name != null ? !name.equals(storage.name) : storage.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + amount;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
    public JSONObject toJSON(){
        JSONObject jo = new JSONObject();
        jo.put("id","0");
        jo.put("name",name);
        jo.put("amount",amount);
        return jo;
    }
    public JSONObject toJSONI(){
        JSONObject jo = new JSONObject();
        jo.put("name",name);
        jo.put("amount",amount);
        return jo;
    }
}
