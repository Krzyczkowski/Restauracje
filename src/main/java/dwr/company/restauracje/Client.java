package dwr.company.restauracje;


import entity.Employee;
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
    private static final JSONObject message = new JSONObject();

    /**
     * Polaczenie z serwerem obslugujacym baze danych
     * @param host nazwa hosta serwer API
     * @param port numer portu serwera API
     * @param userName nazwa użytkownika API
     * @param userPass hasło użytkownika API
     * @param DBName nazwa bazdy danych (restauracji) do zalogowania
     * @return true - gdy użytkownik istnieje i ma dostep do BD
               false - gdy użytkownik nieistnieje
     * @throws Exception gdy serwer jest nie aktywny
     */
    protected static boolean connect(String host, Integer port, String userName, String userPass, String DBName) throws Exception {
        // establish a connection by providing host and port
        // number
        //debugowanie
        //System.out.println(host+" "+port+" "+userName+" "+userPass+" "+DBName);

        try (Socket socket = new Socket(host, port)) {
            JSONObject message = new JSONObject();
            String str;
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            //wysyłanie do serwera danych
            message.clear();
            message.put("UserName", userName);
            message.put("UserPass", userPass);
            message.put("DBName", DBName);
            out.writeUTF(message.toString());
            // reading from server
            str = in.readUTF();
            message.clear();
            message.put("command", "break");
            out.writeUTF(message.toString());
            message = (JSONObject) JSONValue.parse(str);
            System.out.println(message.get("result"));
            return message.get("result").toString().equals("true");
        }
    }

    protected static boolean connectConsole(String host, Integer port, String userName, String userPass, String DBName) throws Exception {
        // establish a connection by providing host and port
        // number
        // debugowanie
        // System.out.println(host+" "+port+" "+userName+" "+userPass+" "+DBName);
        try (Socket socket = new Socket(host, port)) {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
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

            /**
             *
             *
             * PONIZEJ KOD DO URUCHAMIANIA ZAPYTAN
             *
             *
             **/
            //updateEmployee("Wiktor","K",2);
            //getAllEmployees();
            getEmployeesFullInfo();
            getEmployeesFullInfo();
            // wylaczenie
            message.put("command", "break");
            out.writeUTF(message.toString());

            return true;
        }
    }

    protected static void getAllUsers() throws IOException {
            JSONObject message = new JSONObject();
            String str;
            message.put("command","getAllEmployees");
            message.put("params",""); // params puste bo nie potrzebuje ale gdyby bylo getUser("Wiktor") wtedy params bedzie "Wiktor"
            out.writeUTF(message.toString());
            str = in.readUTF();
            message = (JSONObject) JSONValue.parse(str);
            JSONObject joE = new JSONObject(); // JO for every Employee
            List<Employee> employeeList = new ArrayList<>();
            for(Integer i = 0; i<message.size();i++){
                joE = (JSONObject) message.get(i.toString());
                employeeList.add(new Employee(joE));
            }
            System.out.println(employeeList.size());
        for (Integer j = 0; j<employeeList.size();j++) {
            System.out.println(employeeList.get(j).getId()+" "+employeeList.get(j).getName()+" "+employeeList.get(j).getLastname());
        }
    }
    protected static void getEmployeeById(int id) throws IOException {
        JSONObject message = new JSONObject();
        String str;
        message.put("command","getEmployeeById");
        message.put("params",id);
        out.writeUTF(message.toString());
        str = in.readUTF();
        message = (JSONObject) JSONValue.parse(str);
        printEmployee(message);
    }
    protected static void getEmployeeByName(String Name) throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getUser");
        message.put("params",userName); // params puste bo nie potrzebuje ale gdyby bylo getUser("Wiktor") wtedy params bedzie "Wiktor"
        out.writeUTF(message.toString());
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

    private static JSONObject getQuery() throws IOException {
        String str;
        JSONObject message;
        str = in.readUTF();
        message = (JSONObject) JSONValue.parse(str);
        return message;
    }




}