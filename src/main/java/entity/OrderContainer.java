package entity;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unchecked")
public class OrderContainer {
    private Orders order ;
    private List<Positions> positions =new ArrayList<>();
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
        //System.out.println(joe);
        JSONObject jo = (JSONObject) JSONValue.parse(joe.get("order").toString());
        joe.clear();
        joe = (JSONObject) JSONValue.parse(jo.get("order").toString()) ;
        this.order = new Orders(joe);
        joe.clear();
        joe = (JSONObject) JSONValue.parse(jo.get("client").toString()) ;
        //System.out.println(joe);
        this.client = new Clients(joe);
        JSONObject positionsJO = (JSONObject) JSONValue.parse(jo.get("positions").toString()) ;
        System.out.println(positionsJO);
        for(int i=0;i< positionsJO.size();i++){
            joe.clear();
            joe = (JSONObject) JSONValue.parse(positionsJO.get("position"+i).toString()) ;
            Positions pos = new Positions(joe);
            positions.add(pos);
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
