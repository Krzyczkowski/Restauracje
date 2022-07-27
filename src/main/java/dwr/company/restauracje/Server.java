package dwr.company.restauracje;

import java.io.*;
import java.net.*;

// Server class
class Server {

     @SuppressWarnings("InstantiationOfUtilityClass")
     public static void main(String[] args) {
         ServerSocket server = null;
         Configuration config;
         try {
             config = new Configuration();
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
         try {
            // nasluchiwanie na porcie 1235
            server = new ServerSocket(1235);
            server.setReuseAddress(true);
            // nieskonczona petla nasluchująca żądań klienta
            while (true) {
                Socket client = server.accept();
                System.out.println("NOWY CLIENT:" + client.getInetAddress().getHostAddress());
                // tworzenie nowego Thread'u
                SerwerThread clientSock = new SerwerThread(client,config);
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