package dwr.company.restauracje;

import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {
    private static PrintWriter out; // writing to server
    private static BufferedReader in; // reading from server
    private static Scanner sc;       // scanner for input
    private static String line;

    public static void Client()
    {

       connect("localhost",1234,"user","password");




    }
    private static void connect(String host, Integer port, String userName, String userPass){
        // establish a connection by providing host and port
        // number

        try (Socket socket = new Socket(host, port)) {

            // writing to server
            out = new PrintWriter(socket.getOutputStream(), true);

            // reading from server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // object of scanner class
            sc = new Scanner(System.in);
            String line = null;

            //while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                //line = sc.nextLine();
                line = "test";
                // sending the user input to server
                out.println(line);
                out.flush();

                // displaying server reply
                System.out.println("Server replied " + in.readLine());
            //}
            // closing the scanner object
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}