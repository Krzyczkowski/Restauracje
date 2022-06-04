package dwr.company.restauracje;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    protected void authorization(){
        //Miejsce na autoryzacje u góry klasy już zadeklarowane pola z zmiennymi...
        try {
            if (Client.connect("localhost", 1234, login.getText(), password.getText(), place.getValue().toString())) {
                //okienko poprawne dane
            }
            //okienko nie poprawne dane
        }catch (Exception e)
        {
            System.out.println("Serwer wyłączony");
        }
    }

    @FXML
    protected void onExitbutton(){
        Platform.exit();
    }

}
