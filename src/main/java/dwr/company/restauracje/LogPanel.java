package dwr.company.restauracje;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.AccessibleAction;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogPanel {
    @FXML
    private ComboBox place;
    @FXML
    protected TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Button logInbutton;
    @FXML
    private Button exitButton;
    @FXML
    private Button minimalizeButton;
    @FXML
    private Label Warning;

    @FXML
    protected void authorization(){
        //Miejsce na autoryzacje u góry klasy już zadeklarowane pola z zmiennymi...
        try {
            if (Client.connect("localhost", 1234, login.getText(), password.getText(), place.getValue().toString())) {
                Warning.setText("");
                //okienko poprawne dane
            } else {
                Warning.setText("Niepoprawny login lub hasło...");
                //Dziala jak natura chciala
            }
        }catch (Exception e)
        {
            Warning.setText("Brak połączenia z serwerem.");
            System.out.println("Serwer wyłączony");
        }
    }

    @FXML
    protected void onExitbutton(){
        Platform.exit();
    }
    @FXML
    protected  void onMinimalizationButton(){
        ((Stage) minimalizeButton.getParent().getScene().getWindow()).setIconified(true);
    }

}
