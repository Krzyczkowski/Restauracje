package dwr.company.restauracje;

import entity.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class popupWindowsControler implements Initializable{
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(GeneralController.toEditPopUp != null){
            newName.setText(GeneralController.toEditPopUp.getEmp().getName());
            newSecondName.setText((GeneralController.toEditPopUp.getEmp().getLastname()));
        } else {

        }
    }


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

        GeneralController.toEditPopUp = null;

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



