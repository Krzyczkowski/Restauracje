package dwr.company.dwrestauracje;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LogController {

    //Fields with data
    @FXML
    private ComboBox place;
    @FXML
    private Label Warning;
    @FXML
    protected TextField login;
    @FXML
    private PasswordField password;

    //Buttons
    @FXML
    private Button logInbutton;
    @FXML
    private Button exitButton;
    @FXML
    private Button minimalizeButton;

    //Other declarations
    private GeneralWindowSet mainWindow;


    @FXML
    protected void authorization() {
        //Miejsce na autoryzacje u góry klasy już zadeklarowane pola z zmiennymi...
        try {
            mainWindow = new GeneralWindowSet();

          /*  if (Client.connect("localhost", 1234, login.getText(), password.getText(), place.getValue().toString())) {
                Warning.setText("");
                //okienko poprawne dane
                try {
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                Warning.setText("Niepoprawny login lub hasło...");
                //Dziala jak natura chciala
            }*/

        } catch (Exception e) {
            Warning.setText("Brak połączenia z serwerem.");
            System.out.println("Serwer wyłączony");

            try {
                mainWindow = new GeneralWindowSet();
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //Deal with some actions int window
    @FXML
    protected void onExitbutton() {
        Platform.exit();
    }

    @FXML
    protected void onMinimalizationButton() {
        ((Stage) minimalizeButton.getParent().getScene().getWindow()).setIconified(true);
    }

}



