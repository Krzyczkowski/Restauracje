package dwr.company.restauracje;

import entity.Employee;
import entity.Logins;
import entity.Restaurants;
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
        return message.get("result").toString().equals("true");
    }

    protected static boolean connectConsole(String host, Integer port, String userName, String userPass, String DBName) throws Exception {

        try (Socket socket = new Socket(host, port)) {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            List<Restaurants> rest;
//            rest = InitGetRestaurantNames(host,port);
//            System.out.println(rest);
//            for(Restaurants r : rest){
//                System.out.println(r);
//            }
            //wysyłanie do serwera danych
            message.clear();
            message.put("UserName", userName);
            message.put("UserPass", userPass);
            message.put("DBName", DBName);
            out.writeUTF(message.toString());
            message.clear();

            // reading from server
            String str;
            str = in.readUTF();
            message = (JSONObject) JSONValue.parse(str);
            str = "true";

            if (str.equals(message.get("result"))){
            /**
             *
             *
             * PONIZEJ KOD DO URUCHAMIANIA ZAPYTAN
             *
             *
             **/
            getAllEmployees();


            logout();
            }
            return true;
        }
    }
    public static void logout() throws IOException {
        message.clear();
        // wylaczenie
        message.put("command", "break");
        out.writeUTF(message.toString());

    }
    private static List<String>printRestaurants(JSONObject message){
        List<String> list = new ArrayList<>();
        for(Integer i = 0; i<message.size();i++)
            list.add(new Restaurants((JSONObject) message.get(i.toString())).getName());
        return list;
    }
    private  static List<Employee> printEmployee(JSONObject message){
        ArrayList<Employee> list = new ArrayList<>();
        for(Integer i = 0; i<message.size();i++)
            list.add(new Employee((JSONObject) message.get(i.toString())));
        return list;
    }
    private  static List<Logins> printLogins(JSONObject message){
        List<Logins> list= new ArrayList<>();
        for(Integer i = 0; i<message.size();i++)
        {
            System.out.println(message.get(i.toString()));
            list.add(new Logins((JSONObject) message.get(i.toString())));
        }

        for(Integer i = 0; i<message.size();i++)
            System.out.println(list.get(i).toJSON());
        return list;
    }

    protected static List<Employee> getAllEmployees() throws IOException {
            JSONObject message = new JSONObject();
            String str;
            message.put("command","getAllEmployees");
            message.put("params",""); // params puste bo nie potrzebuje ale gdyby bylo getUser("Wiktor") wtedy params bedzie "Wiktor"
            out.writeUTF(message.toString());
            str = in.readUTF();
            message = (JSONObject) JSONValue.parse(str);
            // JO for every Employee
            return printEmployee(message);
    }

    protected static List<Employee> getEmployeeById(Integer id) throws IOException {
        JSONObject message = new JSONObject();
        String str;
        message.put("command","getEmployeeById");
        message.put("params",id);
        out.writeUTF(message.toString());
        str = in.readUTF();
        message = (JSONObject) JSONValue.parse(str);
        return printEmployee(message);
    }
    protected static List<Employee> getEmployeeByName(String Name) throws IOException {
        JSONObject message = new JSONObject();
        String str;
        message.put("command","getEmployeeByName");
        message.put("params",Name);
        out.writeUTF(message.toString());
        str = in.readUTF();
        message = (JSONObject) JSONValue.parse(str);
       return printEmployee(message);
    }
    protected static void insertEmployee(String name, String lastName) throws IOException {
        JSONObject message = new JSONObject();
        Employee emp = new Employee();
        emp.setLastname(lastName);
        emp.setName(name);
        emp.setId(0);
        message.put("command","insertEmployee");
        message.put("params",emp.toJSON());
        out.writeUTF(message.toString());
    }

    protected static void deleteEmployee(Integer id) throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","deleteEmployee");
        message.put("params",id);
        out.writeUTF(message.toString());
    }
    protected static void updateEmployee(String name, String lastName,int id) throws IOException {
        JSONObject message = new JSONObject();
        Employee emp = new Employee();
        emp.setLastname(lastName);
        emp.setName(name);
        emp.setId(id);
        message.put("command","updateEmployee");
        message.put("params",emp.toJSON());
        out.writeUTF(message.toString());
    }

    protected static List<Logins> getEmployeesFullInfo() throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getEmployeesFullInfo");
        message.put("params","");
        out.writeUTF(message.toString());
        String str = in.readUTF();
        message = (JSONObject) JSONValue.parse(str);
        return printLogins(message);
    }


   //DLA DARKA: DZIALANIE PONIZSZEJ FUNKCJI SPRAWDZONE NA LOGPANELCONSOLE (connectConsole)!!!!
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
}