package dwr.company.dwrestauracje;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainWindowController {
    @FXML
    private Label welcomeText;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("działa");
    }
}