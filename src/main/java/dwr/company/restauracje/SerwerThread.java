package dwr.company.restauracje;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class SerwerThread implements Runnable {
    private final Socket clientSocket;
    private String name;
    private int accesLevel;
    // Constructor
    public SerwerThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        String message;
        JSONObject JSON;
        DataOutputStream out;
        DataInputStream in;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {

            // get the outputstream of client
            message=in.readUTF();
            JSON=(JSONObject) JSONValue.parse(message);
            if(authorization(JSON.get("UserName").toString(),JSON.get("UserPass").toString(),JSON.get("DBName").toString()))
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
        //komentarz
        JSONObject JSON;
        DataOutputStream out;
        DataInputStream in;
        try {
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            message = in.readUTF();
            JSON = (JSONObject) JSONValue.parse(message);
            message = JSON.get("command").toString();
            if (message.equals("break"))
                break;
            switch (message) {
                case "getAllUsers":
                    JSON.clear();
                    JSON.put("result","Oto wszyscy userzy!");
                    out.writeUTF(JSON.toString());
                    break;
                case "getUser":
                    break;
            }
        }
    }
    private boolean authorization(String userName, String password,String DBname) throws FileNotFoundException {
        String s;
        Scanner odczyt = new Scanner(new File(DBname));
        while(odczyt.hasNext())
        {
            s=odczyt.nextLine();
            System.out.println(s.substring(0,s.lastIndexOf(";")));
            if(s.substring(0,s.lastIndexOf(";")).length()==userName.length()+password.length()+1)
            {
                if (s.substring(0,s.lastIndexOf(";")).equals(userName+";"+password)) // true jeśli jest w bazie)
                {
                    name = userName;
                    accesLevel=Integer.parseInt(s.substring(s.lastIndexOf(";")+1));
                    return true;
                }
            }
        }
        return false;
    }

}

