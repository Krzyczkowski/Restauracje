package dwr.company.restauracje;

import entity.Employee;
import entity.Logins;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.Socket;

/**
 * Klasa odpowiedzialna za obsługe 1 klienta
 */
class SerwerThread implements Runnable {
    private final Socket clientSocket;
    //
    // private final Configuration privileges;
    private String name;
    private static Logins user;
    private static Configuration conf;
    private int clientAccessLevel;
    private static JSONObject JSON;
    private String message;
    private static DataOutputStream out;
    private static DataInputStream in;
    private static DatabaseAPI db; // class for database communication
    // Constructor
    public SerwerThread(Socket socket, Configuration privileges) {
        this.clientSocket = socket;
        this.conf = privileges;
    }
    public void run() {
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            db = new DatabaseAPI();
            connect();
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void connect() throws IOException {
        message = in.readUTF();
        JSON = (JSONObject) JSONValue.parse(message);
        System.out.printf(message);
        // here We operate with client who is not logged and only wants a list of all Restaurants
        if (JSON.get("command".toString()).equals("getAllRestaurantsOnly")) {
            JSON = new JSONObject();
            JSON = db.getAllRestaurants();
            out.writeUTF(JSON.toString());
            authorization();
        }
    }
    private void authorization() throws IOException {
        message = in.readUTF();
        System.out.printf(message);
        JSON = (JSONObject) JSONValue.parse(message);
        try{if(JSON.get("command").equals("break")){
            return;
        }}catch (Exception e ){}
        try{
        user = authorization(JSON.get("UserName").toString(), JSON.get("UserPass").toString(), JSON.get("DBName").toString());
        System.out.println(user.getId());
        if (user.getId() > 0) {
            clientAccessLevel = user.getLevelaccess();
            JSON = new JSONObject();
            JSON.put("result", "true");
            System.out.println(JSON.get("result"));
            out.writeUTF(JSON.toString());
            communication();
        } else {
            JSON = new JSONObject();
            JSON.put("result", "false");
            System.out.println(JSON.get("result"));
            out.writeUTF(JSON.toString());
            authorization();
        }}catch (Exception e){};
    }
    private void communication() throws IOException {
        while (true) {
            message = in.readUTF();
            JSON = (JSONObject) JSONValue.parse(message);
            message = JSON.get("command").toString();

            System.out.println("Serwer otrzymał polecenie:"+message+" od klienta");

            if (message.equals("break"))
                break;

            // sprawdzenie czy w pliku privileges znajduje sie dana funkcja
            if(conf.privileges.containsKey(message) && clientAccessLevel>= conf.privileges.get(message))
            switch (message) {
                case "getAllEmployees":
                    if(clientAccessLevel>= conf.privileges.get("getAllEmployees"))
                    getAllEmployees();
                    break;
                case "getEmployeeById":
                    getEmployeeById( (int)(long) JSON.get("params"));
                    break;
                case "getEmployeeByName":
                    getEmployeeByName( (String) JSON.get("params"));
                    break;
                case "insertEmployee":
                    insertEmployee(new Employee((JSONObject) JSON.get("params")) );
                    break;
                case "deleteEmployee":
                    deleteEmployee((int) (long) JSON.get("params"));
                    break;
                case "updateEmployee":
                    updateEmployee(new Employee((JSONObject) JSON.get("params")) );
                    break;
                case "getEmployeesFullInfo":  // information about employees with salary, access power, login+pasword etc.
                    if (JSON.get("params").equals(""))
                        getEmployeesFullInfo();
                    else
                        getEmployeesFullInfo(JSON.get("params").toString());
                    break;
            }else{
                System.out.println("brak takich uprawnien!!");
                out.writeUTF("brak takich uprawnien!!");
            }
        }
        db.close();
    }
    private Logins authorization(String userName, String password, String DBname) throws FileNotFoundException {
        return db.authorization(userName,password,db.getDatebaseIdByName(DBname));
    }
    static private void getAllEmployees() throws IOException {
        JSON.clear();
        JSON = db.getAllEmployee();
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }
    static private void getEmployeeById(Integer id) throws IOException {
        System.out.println("id: "+id);
        JSON.clear();
        JSON = db.getEmployeeById(id);
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }
    static private void getEmployeeByName(String name) throws IOException{
        JSON.clear();
        JSON = db.getEmployeeByName(name);
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }
    static private void insertEmployee(Employee e) throws IOException{
        JSON.clear();
        db.insertEmployee(e);
    }
    static private void deleteEmployee(Integer id) throws IOException{
        JSON.clear();
        db.deleteEmployee(id);
    }
    static private void updateEmployee(Employee e) throws IOException{
        JSON.clear();
        db.updateEmployee(e);
    }
    static private void getEmployeesFullInfo() throws IOException {
        JSON.clear();
        JSON = db.getAllEmployeesFullInfo();
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }
    static private void getEmployeesFullInfo(String s) throws IOException {
        JSON.clear();
        JSON = db.getAllEmployeesFullInfo(s);
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }
    static private void getAllRestaurants() throws IOException {
        JSON.clear();
        JSON = db.getAllRestaurants();
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }
}

