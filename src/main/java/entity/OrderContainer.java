package entity;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.List;

public class OrderContainer {
    private Orders order ;
    private List<Positions> positions;
    private Clients client;

    public OrderContainer(Orders order, List<Positions> positions, Clients client) {
        this.order = order;
        this.positions = positions;
        this.client = client;
    }

    public OrderContainer() {

    }

    public JSONObject toJSON(){
        JSONObject jo = new JSONObject();
        jo.put("order",order.toJSON().toString());
        jo.put("client",client.toJSON().toString());
        JSONObject pozycje = new JSONObject();
        for(int i=0;i<positions.size();i++){
            pozycje.put("position"+i,positions.get(i).toJSON().toString());
        }
        jo.put("positions",pozycje);
        return jo;
    }
    public OrderContainer(JSONObject joe) {
        System.out.println(joe);
        JSONObject jo = (JSONObject) JSONValue.parse(joe.get("orders").toString());
        System.out.println(jo);
        this.order = new Orders((JSONObject) jo.get("order"));
        System.out.println("1");
        this.client = new Clients((JSONObject) jo.get("client"));
        System.out.println("1");
        JSONObject positionsJO = (JSONObject) jo.get("positions");
        System.out.println("1");
        for(int i=0;i< positionsJO.size();i++){
            positions.add(new Positions((JSONObject) positionsJO.get("positions"+i)) );
        }
    }


    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public List<Positions> getPositions() {
        return positions;
    }

    public void setPositions(List<Positions> positions) {
        this.positions = positions;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "OrderContainer{" +
                "order=" + order +
                ", positions=" + positions +
                ", client=" + client +
                '}';
    }
}
