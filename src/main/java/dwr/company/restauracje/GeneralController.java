package dwr.company.restauracje;

import entity.Employee;
import entity.Logins;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralController implements Initializable {
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
    private TableView<Employee> employeeTable;
    @FXML
    private TableView<Logins> logHistory;
    @FXML
    private TableColumn nr;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn secondName;
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
    private final ObservableList<Employee> employeesList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image iconImage = new Image("/person.png", false);
            userIcon.setFill(new ImagePattern(iconImage));
            userIcon.setEffect(new DropShadow(20, Color.WHITESMOKE));
            loadEmployesToTable();

        } catch (Exception er) {
          //  er.printStackTrace();         Bug between Fxml and initialize do not uncomend it bc consol will be red, everythnk work without it.
        }
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
    protected void report(){
   //     loadEmployesToTable();
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


    //Moveing popup's
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

    //Deal with data froma database
    protected void loadEmployesToTable(){
        nr.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        secondName.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastname"));

        //polacznie z baza i wydobycie obiektow lub stringow ale lepiej obiektow w tym miejscu musi byc zimplementowane
        //Example przerzucenia do tabel view na podstawie dodanych danych nie z bazy

        employeesList.add(new Employee("Kowalski", "Dan"));
        employeesList.add(new Employee("Kowal", "Jarek"));
        employeesList.add(new Employee("Miłosław", "Pszeniczne"));

        employeeTable.setItems(employeesList);

    }
}
