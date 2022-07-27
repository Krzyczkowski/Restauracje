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
@SuppressWarnings("unchecked")
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
        for(int i = 0; i<message.size(); i++)
            list.add(new Restaurants((JSONObject) message.get(Integer.toString(i))).getName());
        return list;
    }
    private  static List<Logins> printLogins(JSONObject message){
        List<Logins> list= new ArrayList<>();
        for(int i = 0; i<message.size(); i++)
            list.add(new Logins((JSONObject) message.get(Integer.toString(i))));
        return list;
    }
    private static List<Products> printProducts(JSONObject message) {
        List<Products> list= new ArrayList<>();
        for(int i = 0; i<message.size(); i++) {
            list.add(new Products((JSONObject) message.get(Integer.toString(i))));
        }
        return list;
    }
    private static List<String> printCategories(JSONObject message) {
        List<String> list= new ArrayList<>();
        for(int i = 0; i<message.size(); i++)
            list.add(new Categories((JSONObject) message.get(Integer.toString(i))).getId());
        return list;
    }
    private static List<Storage> printStorage(JSONObject message) {
        List<Storage> list= new ArrayList<>();
        for(int i = 0; i<message.size(); i++)
            list.add(new Storage((JSONObject) message.get(Integer.toString(i))));
        return list;
    }
    private static List<Compositions> printComposition(JSONObject jo) {
        List<Compositions> list= new ArrayList<>();
        for(int i = 0; i<jo.size(); i++)
            list.add(new Compositions((JSONObject) jo.get(Integer.toString(i))));
        return list;
    }
    private static List<Orders> printOrders(JSONObject jo) {
        List<Orders> list= new ArrayList<>();
        for(int i = 0; i<jo.size(); i++)
            list.add(new Orders((JSONObject) jo.get(Integer.toString(i))));
        return list;
    }
    private static List<Positions> printPositions(JSONObject jo) {
        List<Positions> list= new ArrayList<>();
        for(int i = 0; i<jo.size(); i++)
            list.add(new Positions((JSONObject) jo.get(Integer.toString(i))));
        return list;
    }
    public static List<String> getRestaurantNames() throws IOException {
        message.clear();
        message.put("command", "getAllRestaurants");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if (!message.get("0").toString().equals("1"))
                return printRestaurants(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<String>();
            }
        }
        return new ArrayList<String>();
    }
    protected static List<Logins>   getEmployeesFullInfo() throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getEmployeesFullInfo");
        message.put("params","");
        out.writeUTF(message.toString());
        String str = in.readUTF();
        message = (JSONObject) JSONValue.parse(str);
        if(!message.isEmpty()) {
            if (!message.get("0").equals("1"))
                return printLogins(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<Logins>();
            }
        }
        return new ArrayList<Logins>();
    }
    protected static List<Logins>   getEmployeesFullInfo(String s) throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getEmployeesFullInfo");
        message.put("params",s);
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if (!message.get("0").equals("1"))
                return printLogins(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<Logins>();
            }
        }
        return new ArrayList<Logins>();
    }
    public static List<String>      getAllRestaurants() throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getAllRestaurants");
        message.put("params","");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if (!message.get("0").equals("1"))
                return printRestaurants(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<String>();
            }
        }
        return new ArrayList<String>();
    }

    public static List<Products> getProducts() throws IOException {
        message.clear();
        message.put("command", "getProducts");
        message.put("params", "");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if(!message.get("0").equals("1"))
                return printProducts(message);
            else
            {
                System.out.println("brak praw dostępu");
                return new ArrayList<Products>();
            }
        }
        return new ArrayList<Products>();
    }
    public static List<Products> getProducts(String name,String category) throws IOException {
        message.clear();
        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("category", category);
        message.put("command", "getProducts");
        message.put("params", jo.toString());
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if (!message.get("0").equals("1"))
                return printProducts(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<Products>();
            }
        }
        return new ArrayList<Products>();
    }
    public static List<String> getCategories() throws IOException {
        message.clear();
        message.put("command", "getAllCategories");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if (!message.get("0").equals("1"))
                return printCategories(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<String>();
            }
        }
        return new ArrayList<String>();
    }
    public static List<Storage> getStorage() throws IOException {
        message.clear();
        message.put("command", "getStorage");
        out.writeUTF(message.toString());
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if (!message.get("0").equals("1"))
                return printStorage(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<Storage>();
            }
        }
        return new ArrayList<Storage>();
    }
    public static List<Orders> getOrders(String  date) throws IOException {
        message.clear();
        message.put("command", "getOrders");
        message.put("params", date);
        out.writeUTF(message.toString());
        message.clear();
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if (!message.get("0").equals("1"))
                return printOrders(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<Orders>();
            }
        }
        return new ArrayList<Orders>();
    }
    public static List<Positions> getPositions(int idOrder) throws IOException {
        message.clear();
        message.put("command", "getPositions");
        message.put("params", idOrder);
        out.writeUTF(message.toString());
        message.clear();
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if (!message.get("0").equals("1"))
                return printPositions(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<Positions>();
            }
        }
        return new ArrayList<Positions>();
    }
    public static List<Compositions> getComposition(int idProduct) throws IOException {
        message.clear();
        message.put("command", "getComposition");
        message.put("params", idProduct);
        out.writeUTF(message.toString());
        message.clear();
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.isEmpty()) {
            if (!message.get("0").equals("1"))
                return printComposition(message);
            else {
                System.out.println("brak praw dostępu");
                return new ArrayList<Compositions>();
            }
        }
        return new ArrayList<Compositions>();
    }
    protected static void insertEmployee(String name, String lastName, String login, String password, int levelacces, String restaurantname, float salary, int pesel) throws IOException {
        message.clear();
        Logins log = new Logins(0,login,password,levelacces,restaurantname,pesel,salary,name,lastName);
        Employee emp = new Employee(0,name,lastName);
        message.put("command","insertEmployee");
        message.put("logins",log.toJSON());
        message.put("employee",emp.toJSON());
        message.clear();
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.get("0").equals("1"))
            System.out.println("Operacja powiodła sie");
        else
            System.out.println("brak praw dostępu");
        message.clear();
    }
    public static void insertCategory(String name) throws IOException {
        message.clear();
        message.put("command", "insertCategory");
        message.put("params",name );
        out.writeUTF(message.toString());
        message.clear();
        message = (JSONObject) JSONValue.parse(in.readUTF());
        if(!message.get("0").equals("1"))
            System.out.println("Operacja powiodła sie");
        else
            System.out.println("brak praw dostępu");
        message.clear();
    }




    public static void insertStorageItem(Storage s) throws IOException {
        message.clear();
        s.setId(0);
        s.setRestaurant(restaurantName);
        JSONObject jo = s.toJSON();
        message.put("command", "insertStorageItem");
        message.put("params", jo.toString());
        out.writeUTF(message.toString());
        check();
    }
    protected static void updateEmployee(String name, String lastName,int id, String login, String password, int levelacces, String restaurantname,float salary,int pesel) throws IOException {
        JSONObject message = new JSONObject();
        Logins log = new Logins(id,login,password,levelacces,restaurantname,pesel,salary,name,lastName);
        message.put("command","updateEmployee");
        message.put("params",log.toJSON());
        out.writeUTF(message.toString());
        check();
    }
    public static void updateStorageItem(Storage s) throws IOException {
        message.clear();
        JSONObject jo = s.toJSON();
        message.put("command", "updateStorageItem");
        message.put("params", jo);
        out.writeUTF(message.toString());
        check();
    }
    public static void deleteStorage(Storage s) throws IOException {
        message.clear();
        JSONObject jo = s.toJSON();
        //System.out.println(jo.toString());
        message.put("command", "deleteStorageItem");
        message.put("params", jo.toString());
        out.writeUTF(message.toString());
        check();
    }
    public static void makeOrder(OrderContainer orderContainer) throws IOException {
        message.clear();
        JSONObject jo = new JSONObject();
        jo.put("order",orderContainer.toJSON());
        message.put("command", "makeOrder");
        message.put("params", jo.toString());
        out.writeUTF(message.toString());
        check();
    }
    public static void makeProduct(Products p, List<Storage> ing) throws IOException {
        message.clear();
        JSONObject newProduct = new JSONObject();
        newProduct.put("product",p.toJSON().toString());
        JSONObject ingridients = new JSONObject();
        for(int i=0;i<ing.size();i++){
            ingridients.put("ingridient"+i,ing.get(i).toJSON());
        }
        newProduct.put("ingridients",ingridients);
        message.put("command", "makeProduct");
        message.put("params", newProduct);
        out.writeUTF(message.toString());
        check();
    }
    public static void deletePositionFromOrder(Positions p) throws IOException {
        System.out.println();
        message.clear();
        message.put("command", "deletePositionFromOrder");
        message.put("params", p.toJSONU());
        out.writeUTF(message.toString());
        check();
    }

    public static void editPositionFromOrder(Positions p, int newValue) throws IOException {
        System.out.println();
        message.clear();
        message.put("command", "editPositionFromOrder");
        message.put("params", p.toJSONU());
        message.put("newValue",newValue);
        System.out.println(p.getAmount()+":ilosc:"+newValue);
        out.writeUTF(message.toString());
        check();
    }

    public static void deleteOrder(Orders o) throws IOException {
        System.out.println(o);
        message.clear();
        message.put("command", "deleteOrder");
        message.put("params", o.toJSON());
        out.writeUTF(message.toString());
        check();
    }

    public static void updateProduct(Products p, List<Storage> ing) throws IOException {
        message.clear();
        JSONObject newProduct = new JSONObject();
        newProduct.put("product",p.toJSON().toString());
        JSONObject ingridients = new JSONObject();
        for(int i=0;i<ing.size();i++){
            ingridients.put("ingridient"+i,ing.get(i).toJSON());
        }
        newProduct.put("ingridients",ingridients);
        message.put("command", "updateProduct");
        message.put("params", newProduct);
        out.writeUTF(message.toString());
        check();
    }

    public static void deleteProduct(int id) throws IOException {
        message.clear();
        message.put("command", "deleteProduct");
        message.put("params", id);
        out.writeUTF(message.toString());
        check();;
    }

    public static void insertRestaurant(String text) throws IOException {
        message.clear();
        message.put("command", "insertRestaurant");
        message.put("params", text);
        out.writeUTF(message.toString());
        check();
    }

    public static void deleteRestaurant(String text) throws IOException {
        message.clear();
        message.put("command", "deleteRestaurant");
        message.put("params", text);
        out.writeUTF(message.toString());
        check();
    }

    public static void updateRestaurant(String old, String text) throws IOException {
        message.clear();
        message.put("command", "updateRestaurant");
        message.put("param2", text);
        message.put("param1", old);
        out.writeUTF(message.toString());
        check();
    }

    public static void deleteEmployee(Logins s) throws IOException {
        message.clear();
        System.out.println(s);
        message.put("command", "deleteEmployee");
        message.put("params",s.toJSON());
        out.writeUTF(message.toString());
        check();
    }
    public static void check() {
        try {
            message.clear();
            message = (JSONObject) JSONValue.parse(in.readUTF());
            if (!message.get("0").equals("1"))
                System.out.println("Operacja powiodła sie");
            else
                System.out.println("brak praw dostępu");
            message.clear();
        } catch (Exception e){
            System.out.println("Błąd odczytu");
        }
    }

    public static void deleteCategory(String s) throws IOException {
        message.clear();
        System.out.println(s);
        message.put("command","deleteCategory");
        message.put("params",s);
        out.writeUTF(message.toString());
        check();
    }
}