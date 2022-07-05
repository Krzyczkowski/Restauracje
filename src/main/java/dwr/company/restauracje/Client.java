package dwr.company.restauracje;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;
import java.util.*;

// Client class

/**
 * Klasa odpowiedzialna za komunikacje z serwerem obsługującym BD
 */
class Client {
    private static DataOutputStream out; // writing to server
    private static DataInputStream in; // reading from server
    private static Scanner sc;       // scanner for input
    private static String line;
    private static Socket socket;


    /**
     * Polaczenie z serwerem obslugujacym baze danych
     * @param host nazwa hosta serwer API
     * @param port numer portu serwera API
     * @param userName nazwa użytkownika API
     * @param userPass hasło użytkownika API
     * @param DBName nazwa bazdy danych (restauracji) do zalogowania
     * @return true - gdy użytkownik istnieje i ma dostep do BD
               false - gdy użytkownik nieistnieje
     * @throws IOException gdy serwer jest nie aktywny
     */
    protected static boolean connect(String host, Integer port, String userName, String userPass, String DBName) throws IOException {
        // establish a connection by providing host and port
        // number
        //debugowanie
        //System.out.println(host+" "+port+" "+userName+" "+userPass+" "+DBName);
        JSONObject message = new JSONObject();
        try {   // zwraca błąd w przypadku braku komunikacji z serwerem
            socket = new Socket(host, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            //wysyłanie do serwera danych
            message.put("UserName", userName);
            message.put("UserPass", userPass);
            message.put("DBName", DBName);
            out.writeUTF(message.toString());
            // reading from server
            String str;
            str = in.readUTF();
            getAllUsers();
            getAllUsers();
            getAllUsers();
            message.clear();
            message.put("command","break");
            out.writeUTF(message.toString());
            message = (JSONObject) JSONValue.parse(str);
            System.out.println(message.get("result"));

        } catch (Exception e) {
            System.err.println(e);
        }

        return message.get("result").toString().equals("true");

    }

    protected static void getAllUsers() throws IOException {
            JSONObject message = new JSONObject();
            message.put("command","getAllUsers");
            message.put("params",""); // params puste bo nie potrzebuje ale gdyby bylo getUser("Wiktor") wtedy params bedzie "Wiktor"
            out.writeUTF(message.toString());
            String str;
            str = in.readUTF();
            message = (JSONObject) JSONValue.parse(str);
            System.out.println(message.get("result"));

    }
    protected static void getUserByName(String userName) throws IOException {
        JSONObject message = new JSONObject();
        message.put("command","getUser");
        message.put("params",userName); // params puste bo nie potrzebuje ale gdyby bylo getUser("Wiktor") wtedy params bedzie "Wiktor"
        out.writeUTF(message.toString());
    }


}