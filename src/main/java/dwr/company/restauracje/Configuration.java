package dwr.company.restauracje;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Configuration {
    public static Map<String,Integer> privileges = new HashMap<>();
    public Configuration() throws FileNotFoundException {
        Scanner in = new Scanner(new File("configuration.txt"));
        String line;
        while(in.hasNext()){
            line =in.nextLine();
            privileges.put(line.substring(0,line.lastIndexOf(":")),Integer.parseInt(line.substring(line.lastIndexOf(":")+1) ));
        }
        for(Map.Entry <String,Integer> entry : privileges.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }


}