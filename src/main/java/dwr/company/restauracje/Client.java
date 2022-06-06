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



    /**
     *
     * @param host nazwa hosta serwer
     * @param port numer portu serwera
     * @param userName nazwa użytkownika
     * @param userPass hasło użytkownika
     * @param DBName nazwa bazdy danych do zalogowania
     * @return true - gdy użytkownik istnieje i ma dostep do BD
    false - gdy użytkownik nieistnieje
     * @throws IOException gdy serwer jest nie aktywny
     */
    protected static boolean connect(String host, Integer port, String userName, String userPass, String DBName) throws IOException {
        // establish a connection by providing host and port
        // number
        //debugowanie
        //System.out.println(host+" "+port+" "+userName+" "+userPass+" "+DBName);
        try (Socket socket = new Socket(host, port))// zwraca błąd w przypadku braku komunikacji z serwerem
        {
            JSONObject message = new JSONObject();
            out=new DataOutputStream(socket.getOutputStream());
            in =new DataInputStream (socket.getInputStream());
            //wysyłanie do serwera danych
            message.put("UserName",userName);
            message.put("UserPass",userPass);
            message.put("DBName",DBName);
            out.writeUTF(message.toString());
            // reading from server
            String str;
            str=in.readUTF();
            message=(JSONObject) JSONValue.parse(str);
            System.out.println(message.get("result"));
            //warunki

            return message.get("result").toString().equals("true");
        }


    }
}