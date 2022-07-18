package entity;

import org.json.simple.JSONObject;

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
        jo.put("order",order.toJSON());
        jo.put("client",client.toJSON());
        JSONObject pozycje = new JSONObject();
        for(int i=0;i<positions.size();i++){
            pozycje.put("position"+i,positions.get(i).toJSON());

        }
        jo.put("positions",pozycje);
        return jo;
    }
    public OrderContainer(JSONObject jo) {
        this.order = (Orders) jo.get("order");
        this.client = (Clients) jo.get("client");
        JSONObject positionsJO = (JSONObject) jo.get("positions");
        for(int i=0;i< positionsJO.size();i++){
            positions.add((Positions) positionsJO.get("positions"+i));
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
