package dwr.company.restauracje;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
        //Active srever
        try {
            mainWindow = new GeneralWindowSet();
            mainWindow.lodaFxmls();
            mainWindow.firstUsage();

            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();

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
        //Srver shouted down
        } catch (Exception e) {
            Warning.setText("Brak połączenia z serwerem.");
            System.out.println("Serwer wyłączony");


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



