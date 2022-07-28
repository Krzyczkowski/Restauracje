package dwr.company.restauracje;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import static dwr.company.restauracje.Client.logout;

@SuppressWarnings("ALL")
public class LogController{

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
    @FXML
    private Button refreshButton;

    //Other declarationss
    private GeneralWindowSet mainWindow;

    @FXML
    public void initialize() {
        try {
            place.getItems().clear();
            place.getItems().addAll(Client.InitGetRestaurantNames("localhost",1235));
        } catch (IOException e) {
            Warning.setText("Brak polaczenia z serwerem!");
        }
    }

    @FXML
    protected void authorization() throws IOException {
        //Active srever
        try {
            if (Client.login(login.getText(),password.getText(),(String) place.getValue())) {
                mainWindow = new GeneralWindowSet();
                mainWindow.firstUsage();

                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
            } else {
                Warning.setText("Niepoprawny login lub hasło...");
            }
        //Srver shouted down
        } catch (Exception e) {
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

    @FXML
    protected void refresh(){
        try {
            place.getItems().clear();
            place.getItems().addAll(Client.InitGetRestaurantNames("localhost",1235));
            Warning.setText("");
        } catch (IOException e) {
            Warning.setText("Brak polaczenia z serwerem!");
        }
    }

}



