package dwr.company.restauracje;

import java.io.IOException;

public class LogPanelConsole {
    public static void main(String[] args) throws IOException {
        Client.connect("localhost", 1234, "user", "password", "lok1");
    }
}
