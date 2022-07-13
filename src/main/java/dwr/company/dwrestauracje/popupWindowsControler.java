package dwr.company.dwrestauracje;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class popupWindowsControler {
    //Fields with data
    @FXML
    protected TextField newSecondName, newPesel, newLevel, newSalary, newLogin, newName;
    @FXML
    private PasswordField newPassword;


    //Buttons
    @FXML
    private Button exitButton;
    @FXML
    private Button minimalizeButton;
    @FXML
    private Button saveEmploye;

    //Other declarations



    //Deal with some actions int window
    @FXML
    protected void onExitbutton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onMinimalizationButton() {
        ((Stage) minimalizeButton.getParent().getScene().getWindow()).setIconified(true);
    }

    @FXML
    protected void saveEmployeInfo(){

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    protected void saveProductInfo(){

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    protected void saveComponentInfo(){

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

}



