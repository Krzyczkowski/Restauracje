package dwr.company.restauracje;

import entity.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
// Client class
/**
 * Klasa odpowiedzialna za komunikacje z serwerem obsługującym BD
 */

class Client {
    private static DataOutputStream out; // writing to server
    private static DataInputStream in; // reading from server     // scanner for input
    private static JSONObject message = new JSONObject();
    public static String restaurantName;
    public static String usrName;

    public static int getLevelacces() {
        return levelacces;
    }

    private static int levelacces;

    public static int id;
    /**
     * Polaczenie z serwerem obslugujacym baze danych
     * @param userName nazwa użytkownika API
     * @param userPass hasło użytkownika API
     * @param DBName nazwa bazdy danych (restauracji) do zalogowania
     * @return true - gdy użytkownik istnieje i ma dostep do BD
               false - gdy użytkownik nieistnieje
     * @throws IOException gdy serwer jest nie aktywny
     */

    public static boolean login(String userName, String userPass, String DBName) throws IOException {
        JSONObject message = new JSONObject();
        message.put("UserName", userName);
        message.put("UserPass", userPass);
        message.put("DBName", DBName);
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        levelacces = (int) (long) message.get("result");
        id = (int) (long) message.get("empID");
        usrName = message.get("name").toString();
        restaurantName = DBName;
        return levelacces>0;
    }
    public static List<String> InitGetRestaurantNames(String host, Integer port) throws IOException {
        Socket socket = new Socket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        message.clear();
        message.put("command", "getAllRestaurantsOnly");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        return printRestaurants(message);
    }
    public static void logout() throws IOException {
        message.clear();
        message.put("command", "break");
        out.writeUTF(message.toString());
    }
    private static List<String>printRestaurants(JSONObject message){
        List<String> list = new ArrayList<>();
        for(Integer i = 0; i<message.size();i++)
            list.add(new Restaurants((JSONObject) message.get(i.toString())).getName());
        return list;
    }
    private  static List<Logins> printLogins(JSONObject message){
        List<Logins> list= new ArrayList<>();
        for(Integer i = 0; i<message.size();i++)
            list.add(new Logins((JSONObject) message.get(i.toString())));
        return list;
    }
    private static List<Products> printProducts(JSONObject message) {
        List<Products> list= new ArrayList<>();
        for(Integer i = 0; i<message.size();i++) {
            list.add(new Products((JSONObject) message.get(i.toString())));
        }
        return list;
    }
    private static List<String> printCategories(JSONObject message) {
        List<String> list= new ArrayList<>();
        for(Integer i = 0; i<message.size();i++)
            list.add(new Categories((JSONObject) message.get(i.toString())).getId());
        return list;
    }
    private static List<Storage> printStorage(JSONObject message) {
        List<Storage> list= new ArrayList<>();
        for(Integer i = 0; i<message.size();i++)
            list.add(new Storage((JSONObject) message.get(i.toString())));
        return list;
    }
    private static List<Orders> printOrders(JSONObject jo) {
        List<Orders> list= new ArrayList<>();
        for(Integer i = 0; i<jo.size();i++)
            list.add(new Orders((JSONObject) jo.get(i.toString())));
        return list;
    }
    private static List<Positions> printPositions(JSONObject jo) {
        List<Positions> list= new ArrayList<>();
        for(Integer i = 0; i<jo.size();i++)
            list.add(new Positions((JSONObject) jo.get(i.toString())));
        return list;
    }
    protected static List<Logins>   getEmployeesFullInfo() throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getEmployeesFullInfo");
        message.put("params","");
        out.writeUTF(message.toString());
        String str = in.readUTF();
        message = (JSONObject) JSONValue.parse(str);
        return printLogins(message);
    }
    protected static List<Logins>   getEmployeesFullInfo(String s) throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getEmployeesFullInfo");
        message.put("params",s);
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        return printLogins(message);
    }
    public static List<String>      getAllRestaurants() throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getAllRestaurants");
        message.put("params","");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        return printRestaurants(message);
    }

    public static List<Products> getProducts() throws IOException {
        message.clear();
        message.put("command", "getProducts");
        message.put("params", "");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        return printProducts(message);
    }
    public static List<Products> getProducts(String name,String category) throws IOException{
        message.clear();
        JSONObject jo = new JSONObject();
        jo.put("name",name);
        jo.put("category",category);
        message.put("command", "getProducts");
        message.put("params", jo.toString());
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        return printProducts(message);
    }
    public static List<String> getCategories() throws IOException {
        message.clear();
        message.put("command", "getAllCategories");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        return printCategories(message);
    }
    public static List<Storage> getStorage() throws IOException {
        message.clear();
        message.put("command", "getStorage");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        return printStorage(message);
    }
    public static List<Orders> getOrders(String  date) throws IOException {
        message.clear();
        message.put("command", "getOrders");
        message.put("params", date);
        out.writeUTF(message.toString());
        message.clear();
        message = (JSONObject) JSONValue.parse(in.readUTF());
        return printOrders(message);
    }
    public static List<Positions> getPositions(int idOrder) throws IOException {
        message.clear();
        message.put("command", "getPositions");
        message.put("params", idOrder);
        out.writeUTF(message.toString());
        message.clear();
        message = (JSONObject) JSONValue.parse(in.readUTF());
        return printPositions(message);
    }
    protected static void insertEmployee(String name, String lastName,int id, String login, String password, int levelacces, String restaurantname,float salary,int pesel) throws IOException {
        JSONObject message = new JSONObject();
        Logins log = new Logins(id,login,password,levelacces,restaurantname,pesel,salary,name,lastName);
        message.put("command","insertEmployee");
        message.put("params",log.toJSON());
        out.writeUTF(message.toString());
    }
    public static void insertCategory(String name) throws IOException {
        message.clear();
        message.put("command", "insertCategory");
        message.put("params",name );
        out.writeUTF(message.toString());
        message.clear();
    }

    public static void insertStorageItem(Storage s) throws IOException {
        message.clear();
        s.setId(0);
        JSONObject jo = s.toJSONI();
        message.put("command", "insertStorageItem");
        message.put("params", jo);
        out.writeUTF(message.toString());
    }
    protected static void updateEmployee(String name, String lastName,int id, String login, String password, int levelacces, String restaurantname,float salary,int pesel) throws IOException {
        JSONObject message = new JSONObject();
        Logins log = new Logins(id,login,password,levelacces,restaurantname,pesel,salary,name,lastName);
        message.put("command","updateEmployee");
        message.put("params",log.toJSON().toString());
        out.writeUTF(message.toString());
    }
    public static void updateStorageItem(Storage s) throws IOException {
        message.clear();
        JSONObject jo = s.toJSON();
        message.put("command", "updateStorageItem");
        message.put("params", jo);
        out.writeUTF(message.toString());
    }
    public static void deleteStorage(Storage s) throws IOException {
        message.clear();
        JSONObject jo = s.toJSON();
        //System.out.println(jo.toString());
        message.put("command", "deleteStorageItem");
        message.put("params", jo.toString());
        out.writeUTF(message.toString());
    }
    public static void makeOrder(OrderContainer orderContainer) throws IOException {
        message.clear();
        JSONObject jo = new JSONObject();
        jo.put("order",orderContainer.toJSON());
        message.put("command", "makeOrder");
        message.put("params", jo.toString());
        out.writeUTF(message.toString());
    }
    public static void makeProduct(Products p, List<Storage> ing) throws IOException {
        message.clear();
        JSONObject newProduct = new JSONObject();
        newProduct.put("product",p.toJSON().toString());
        JSONObject ingridients = new JSONObject();
        for(int i=0;i<ing.size();i++){
            ingridients.put("ingridient"+i,ing.get(i).toJSON().toString());
        }
        newProduct.put("ingridients",ingridients);
        message.put("command", "makeProduct");
        message.put("params", newProduct.toString());
        out.writeUTF(message.toString());
    }
}