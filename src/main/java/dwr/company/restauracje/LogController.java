package dwr.company.restauracje;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static dwr.company.restauracje.Client.logout;

public class LogController implements Initializable {

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
            if (Client.login(login.getText(),password.getText(),(String) place.getValue())) {
                //okienko poprawne dane
                mainWindow = new GeneralWindowSet();
                mainWindow.lodaFxmls();
                mainWindow.firstUsage();

                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            } else {
                Warning.setText("Niepoprawny login lub hasło...");
                //Dziala jak natura chciala
            }
        //Srver shouted down
        } catch (Exception e) {
            //Warning.setText("Brak połączenia z serwerem.");
            //System.out.println("Serwer wyłączony");
            e.getStackTrace();

        }
    }

    //Deal with some actions int window
    @FXML
    protected void onExitbutton() throws IOException {
        if(Warning.getText().equals("Brak polaczenia z serwerem!") ||
                Warning.getText().equals("Niepoprawny login lub hasło..."))
        {
            Platform.exit();
        } else {
            logout();
            Platform.exit();
        }
    }

    @FXML
    protected void onMinimalizationButton() {
        ((Stage) minimalizeButton.getParent().getScene().getWindow()).setIconified(true);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            place.getItems().addAll(Client.InitGetRestaurantNames("localhost",1235));
        } catch (IOException e) {
            Warning.setText("Brak polaczenia z serwerem!");
        }
    }
}



