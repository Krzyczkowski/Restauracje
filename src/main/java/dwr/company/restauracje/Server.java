package dwr.company.restauracje;


import sun.security.krb5.Config;

import java.io.*;
import java.net.*;

// Server class
class Server {

     public static void main(String[] args) {
         ServerSocket server = null;
         Configuration privileges;
         try {
             privileges = new Configuration();
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
         try {
            // server is listening on port 1234
            server = new ServerSocket(1235);
            server.setReuseAddress(true);
            // running infinite loop for getting
            // client request
            while (true) {
                // socket object to receive incoming client
                // requests
                Socket client = server.accept();
                // Displaying that new client is connected
                // to server
                System.out.println("NEW CLIENT:" + client.getInetAddress().getHostAddress());
                // create a new thread object
                SerwerThread clientSock = new SerwerThread(client,privileges);
                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}