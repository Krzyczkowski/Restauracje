package dwr.company.restauracje;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        logSet firstLog = new logSet(stage);
    }
}