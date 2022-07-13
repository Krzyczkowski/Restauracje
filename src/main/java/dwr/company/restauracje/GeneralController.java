package dwr.company.restauracje;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GeneralController {
    //Mouse click cordinates
    private double xCordinates = 0;
    private double yCordinates = 0;

    //Scene holder
    GeneralWindowSet actualWindow;

    //Data Fields
    @FXML
    private Circle userIcon;
    @FXML
    private Label userName;
    @FXML
    private Label welcomeText;
    @FXML
    private TableView<Object> employeeTable;
    @FXML
    private TableView<Object> logHistory;
    @FXML
    private TextField searchEmployee;

    //Buttons
    @FXML
    private Button minimalizeButton;
    @FXML
    private Button menuButton1, menuButton2, menuButton3, menuButton4;
    @FXML
    private Button findEmployee, editEmployee, addEmployee;

    //Elements dependent on data base
    @FXML
    public void initialize() throws IOException {
        Image iconImage = new Image("/person.png", false);
        userIcon.setFill(new ImagePattern(iconImage));
        userIcon.setEffect(new DropShadow(20, Color.WHITESMOKE));
    }


    //Deal with some actions int window
    @FXML
    protected void onExitbutton() {
        Platform.exit();
    }

    @FXML
    protected void onMinimalization() {
        ((Stage) minimalizeButton.getParent().getScene().getWindow()).setIconified(true);
    }

    @FXML
    protected void employesSection() throws IOException {
        actualWindow = new GeneralWindowSet();
        actualWindow.setEmployesScene();
    }

    @FXML
    protected void productSection() throws IOException {
        actualWindow = new GeneralWindowSet();
        actualWindow.setProductsScene();
    }

    @FXML
    protected void warehouseSection() throws IOException {
        actualWindow = new GeneralWindowSet();
        actualWindow.setWarehouseScene();
    }

    @FXML
    protected void historySection() throws IOException {
        actualWindow = new GeneralWindowSet();
        actualWindow.setHistoryScene();
    }

    @FXML
    protected void orderSection() throws IOException {
        actualWindow = new GeneralWindowSet();
        actualWindow.setOrderScene();
    }

    @FXML
    protected void logOut() throws IOException {
        Stage stage = new Stage();
        logSet againLog = new logSet(stage);
        actualWindow.getWindow().close();
    }

    //Popups to edit employes
    @FXML
    protected void openPopupEmploye() throws IOException {
        FXMLLoader loader= new FXMLLoader(App.class.getResource("editEmploye.fxml"));
        Scene popupScene = new Scene(loader.load(), 333.0, 637.0);
        popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Stage popup = new Stage();
        popup.initStyle(StageStyle.UNDECORATED);

        popup.setScene(popupScene);
        moveWindow(popupScene, popup);
        popup.show();
    }

    @FXML
    protected void openPopupProduct() throws IOException {
        FXMLLoader loader= new FXMLLoader(App.class.getResource("editProduct.fxml"));
        Scene popupScene = new Scene(loader.load(), 333.0, 348.0);
        popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Stage popup = new Stage();
        popup.initStyle(StageStyle.UNDECORATED);

        popup.setScene(popupScene);
        moveWindow(popupScene, popup);
        popup.show();
    }

    @FXML
    protected void openPopupComponent() throws IOException {
        FXMLLoader loader= new FXMLLoader(App.class.getResource("editComponent.fxml"));
        Scene popupScene = new Scene(loader.load(), 333.0, 267.0);
        popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        Stage popup = new Stage();
        popup.initStyle(StageStyle.UNDECORATED);

        popup.setScene(popupScene);
        moveWindow(popupScene, popup);
        popup.show();
    }



    protected void moveWindow(Scene scene, Stage stage){
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xCordinates = mouseEvent.getSceneX();
                yCordinates = mouseEvent.getSceneY();
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xCordinates);
                stage.setY(mouseEvent.getScreenY() - yCordinates);
            }
        });
    }

}
