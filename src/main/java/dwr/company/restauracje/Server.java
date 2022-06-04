package dwr.company.restauracje;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;
import java.util.Scanner;

// Server class
class Server {
    private static Socket client;
    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            // server is listening on port 1234
            server = new ServerSocket(1234);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("NEW CLIENT:" + client.getInetAddress().getHostAddress());

                // create a new thread object
                ClientHandler clientSock = new ClientHandler(client);
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
    private static boolean authorization(String name, String password,String DBname) throws FileNotFoundException {
        //tak ma być
        //Scanner odczyt = new Scanner(new File(DBname));
        //dla testow to :
        Scanner odczyt = new Scanner(new File("Users"));
        while(odczyt.hasNext())
        {
            if(odczyt.nextLine().equals(name+";"+password))
                return true;
        }
        return false;
        }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            String message;
            JSONObject JSON;
            DataOutputStream out;
            DataInputStream in;
            try {
                out = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                in = new DataInputStream(client.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {

                // get the outputstream of client
                message=(String)in.readUTF();
                JSON=(JSONObject) JSONValue.parse(message);
                if(authorization(JSON.get("UserName").toString(),JSON.get("UserPass").toString(),JSON.get("DBName").toString()))
                {
                    JSON=new JSONObject();
                    JSON.put("result","true");
                }
                else{
                    JSON=new JSONObject();
                    JSON.put("result","false");
                }
                System.out.println(JSON.get("result"));
                out.writeUTF(JSON.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}