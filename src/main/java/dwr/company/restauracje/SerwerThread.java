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
    private final Configuration privileges;
    private String name;
    private int accesLevel;
    private static JSONObject JSON;
    private static DataOutputStream out;
    private static DataInputStream in;
    private static DatabaseAPI db; // class for database communication
    // Constructor
    public SerwerThread(Socket socket, Configuration conf) {
        this.clientSocket = socket;
        this.privileges = conf;
    }

    public void run() {
        String message;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            db = new DatabaseAPI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {

            // get the outputstream of client
            message=in.readUTF();
            JSON=(JSONObject) JSONValue.parse(message);
            /**
             * AUTORYZACJA
             */
            user = authorization(JSON.get("UserName").toString(),JSON.get("UserPass").toString(),JSON.get("DBName").toString());
            if(user.getId()>0)
            {
                JSON=new JSONObject();
                JSON.put("result","true");
                System.out.println(JSON.get("result"));
                out.writeUTF(JSON.toString());
                communication();
            }
            else{
                JSON=new JSONObject();
                JSON.put("result","false");
                System.out.println(JSON.get("result"));
                out.writeUTF(JSON.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void communication() throws IOException {
        String message;
        while (true) {
            message = in.readUTF();
            JSON = (JSONObject) JSONValue.parse(message);
            message = JSON.get("command").toString();
            if (message.equals("break"))
                break;
            switch (message) {
                case "getAllEmployees":
                    getAllEmployees();
                    break;
                case "getEmployeeById":
                    getEmployeeById( (int)(long) JSON.get("params"));
                    break;
                case "getEmployeeByName":
                    getEmployeeByName( (String) JSON.get("params"));
                    break;
                case "insertEmployee":
                    System.out.println(JSON.get("params"));
                    insertEmployee(new Employee((JSONObject) JSON.get("params")) );
                    break;
                case "deleteEmployee":
                    System.out.println(JSON.get("params"));
                    deleteEmployee((int) (long) JSON.get("params"));
                    break;
                case "updateEmployee":
                    System.out.println(JSON.get("params"));
                    updateEmployee(new Employee((JSONObject) JSON.get("params")) );
                    break;
                case "getEmployeesFullInfo":  // information about employees with salary, access power, login+pasword etc.
                    System.out.println(JSON.get("params"));
                    getEmployeesFullInfo();
                    break;
            }
        }
        db.close();
    }
    private Logins authorization(String userName, String password, String DBname) throws FileNotFoundException {
        return db.authorization(userName,password,db.getDatebaseIdByName(DBname));
    }
    static private void getAllEmployees() throws IOException {
        System.out.println("dostalem funkcje getAllUsers() od klienta");
        JSON.clear();
        JSON = db.getAllEmployee();
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }
    static private void getEmployeeById(Integer id) throws IOException {
        System.out.println("dostalem funkcje getEmplyeeById(id) od klienta");
        System.out.println("id: "+id);
        JSON.clear();
        JSON = db.getEmployeeById(id);
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }
    static private void getEmployeeByName(String name) throws IOException{
        System.out.println("dostalem funkcje getEmplyeeByName(name) od klienta");
        JSON.clear();
        JSON = db.getEmployeeByName(name);
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }
    static private void insertEmployee(Employee e) throws IOException{
        System.out.println("dostalem funkcje insertEmployee od klienta");
        JSON.clear();
        db.insertEmployee(e);
        //System.out.println(JSON.toString());
        //out.writeUTF(JSON.toString());
    }
    static private void deleteEmployee(Integer id) throws IOException{
        System.out.println("dostalem funkcje deleteEmployee od klienta");
        JSON.clear();
        db.deleteEmployee(id);
    }
    static private void updateEmployee(Employee e) throws IOException{
        System.out.println("dostalem funkcje updateEmployee od klienta");
        JSON.clear();
        db.updateEmployee(e);
    }
    static private void getEmployeesFullInfo() throws IOException {
        System.out.println("dostalem funkcje getEmployeesFullInfo od klienta");
        JSON.clear();
        JSON = db.getAllEmployeesFullInfo();
        System.out.println(JSON.toString());
        out.writeUTF(JSON.toString());
    }

}

