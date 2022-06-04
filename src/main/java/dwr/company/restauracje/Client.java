package dwr.company.restauracje;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {
    private static DataOutputStream out; // writing to server
    private static DataInputStream in; // reading from server
    private static Scanner sc;       // scanner for input
    private static String line;

    public static void Client()
    {

       //connect("localhost",1234,"user","password");




    }
    protected static boolean connect(String host, Integer port, String userName, String userPass, String DBName){
        // establish a connection by providing host and port
        // number
        System.out.println(host+" "+port+" "+userName+" "+userPass+" "+DBName);
        try (Socket socket = new Socket(host, port)) {
            JSONObject message = new JSONObject();
            out=new DataOutputStream(socket.getOutputStream());
            in=new DataInputStream (socket.getInputStream());
            //wysyłanie do serwera danych
            message.put("UserName",userName);
            message.put("UserPass",userPass);
            message.put("DBName",DBName);
            out.writeUTF(message.toString());
            // reading from server
            String str;
            str=(String)in.readUTF();
            message=(JSONObject) JSONValue.parse(str);
            System.out.println(message.get("result"));
            //warunki

            if(message.get("result").toString().equals("true"))
                return true;
            else
                return false;
            // object of scanner class
            //sc = new Scanner(System.in);
            //String line = null;

            //while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                //line = sc.nextLine();
                //line = "test";
                // sending the user input to server
                //out.println(line);
                //out.flush();

                // displaying server reply
                //System.out.println("Server replied " );
            //}
            // closing the scanner object
            //sc.close();

        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}