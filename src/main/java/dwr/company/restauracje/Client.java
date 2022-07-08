package dwr.company.restauracje;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;


// Client class

/**
 * Klasa odpowiedzialna za komunikacje z serwerem obsługującym BD
 */

class Client {
    private static DataOutputStream out; // writing to server
    private static DataInputStream in; // reading from server     // scanner for input


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
        //debugowanie
        //System.out.println(host+" "+port+" "+userName+" "+userPass+" "+DBName);
        JSONObject message = new JSONObject();
        try (Socket socket = new Socket(host, port)) {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            //wysyłanie do serwera danych
            message.clear();
            message.put("UserName", userName);
            message.put("UserPass", userPass);
            message.put("DBName", DBName);
            out.writeUTF(message.toString());
            // reading from server
            String str;
            str = in.readUTF();
            getAllUsers();
            message.clear();
            message.put("command", "break");
            out.writeUTF(message.toString());
            message = (JSONObject) JSONValue.parse(str);
            System.out.println(message.get("result"));
            return message.get("result").toString().equals("true");
        }
    }

    protected static void getAllUsers() throws IOException {
            JSONObject message = new JSONObject();
            String str;
            str = in.readUTF();
            message.put("command","getAllUsers");
            message.put("params",""); // params puste bo nie potrzebuje ale gdyby bylo getUser("Wiktor") wtedy params bedzie "Wiktor"
            out.writeUTF(message.toString());
            message = (JSONObject) JSONValue.parse(str);
            System.out.println(message.get("result"));

    }

    protected static void getUserByName(String userName) throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getUser");
        message.put("params",userName); // params puste bo nie potrzebuje ale gdyby bylo getUser("Wiktor") wtedy params bedzie "Wiktor"
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