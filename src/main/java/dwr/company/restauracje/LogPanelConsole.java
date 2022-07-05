package dwr.company.restauracje;

public class LogPanelConsole {
    public static void main(String[] args) {
        Client.connect("localhost", 1234, "postgres", "haslo123", "mydatabase");
    }
}
