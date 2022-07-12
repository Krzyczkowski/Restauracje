package dwr.company.restauracje;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class Configuration {
    public static Map<String,Integer> privileges;
    public Configuration() throws FileNotFoundException {
        Scanner in = new Scanner(new File("configuration.txt"));
        String line;
        while(in.hasNext()){
            line =in.nextLine();
            System.out.println(line.substring(0,line.lastIndexOf(":")));
            System.out.println(line.substring(line.lastIndexOf(":")+1));
            privileges.put(line.substring(0,line.lastIndexOf(":")),Integer.parseInt(line.substring(line.lastIndexOf(":")+1) ));
        }
    }
}