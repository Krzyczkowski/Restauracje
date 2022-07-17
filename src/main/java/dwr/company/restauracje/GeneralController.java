package dwr.company.restauracje;

import entity.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.security.spec.ECField;
import java.util.List;
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
    private TableView<Logins> employeeTable;
    @FXML
    private TableView<Products> tableWithProducts;
    @FXML
    private TableView<Storage>tabelWithComponents;
    @FXML
    private TableView<Logins> logHistory;
    @FXML
    private TableColumn nr,name,lastName,pesel,salary,restuarant,amount;
    @FXML
    private TextField searchEmployee;
    @FXML
    private TableColumn productName,productCategory,productPrice;
    @FXML
    private TableColumn itemName,itemAmount,itemId;
    @FXML
    private TextField searchProduct;

    @FXML
    public TextField editWarnigLabel;
    @FXML
    public TextField amountOfComponent;

    //Buttons
    @FXML
    private Button minimalizeButton;
    @FXML
    private Button menuButton1, menuButton2, menuButton3, menuButton4;
    @FXML
    private Button findEmployee, editEmployee, addEmployee;
    @FXML
    private ComboBox choseCategory;
    //Elements dependent on data base
    private ObservableList<Logins> employeesList = FXCollections.observableArrayList();
    private ObservableList<Products> productList = FXCollections.observableArrayList();
    private ObservableList<Storage> itemList = FXCollections.observableArrayList();
    public static Logins toEditPopUp;
    public static Storage toEditStoragePopUp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image iconImage = new Image("/person.png", false);
            userIcon.setFill(new ImagePattern(iconImage));
            userIcon.setEffect(new DropShadow(20, Color.WHITESMOKE));
            loadEmployesToTable();
            loadStorageToTable();
            //loadProductToTable();
        } catch (Exception er) {
           //er.printStackTrace();        // Bug between Fxml and initialize do not uncomend it bc consol will be red, everythnk work without it.
        }
    }

    //Deal with some actions int window
    @FXML
    protected void onExitbutton() throws IOException {
        Client.logout();
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
    protected void employesSection() throws Exception {
        actualWindow = new GeneralWindowSet();
        actualWindow.setEmployesScene();
        try{
            loadEmployesToTable();
        }catch (Exception er) {
            //er.printStackTrace();        // Bug between Fxml and initialize do not uncomend it bc consol will be red, everythnk work without it.
        }

    }

    @FXML
    protected void productSection() throws Exception {
        actualWindow = new GeneralWindowSet();
        actualWindow.setProductsScene();
        try{
            choseCategory.getItems().clear();
            choseCategory.getItems().addAll(Client.getCategories());
            choseCategory.getItems().add(null);
            loadProductToTable();
        }catch (Exception er) {
            //er.printStackTrace();        // Bug between Fxml and initialize do not uncomend it bc consol will be red, everythnk work without it.
        }
    }

    @FXML
    protected void warehouseSection() throws Exception {
        actualWindow = new GeneralWindowSet();
        actualWindow.setWarehouseScene();
        try {
            loadStorageToTable();
        }catch (Exception er) {
            System.out.println(er);      // Bug between Fxml and initialize do not uncomend it bc consol will be red, everythnk work without it.
    }


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
        Client.logout();
        Stage stage = new Stage();
        logSet againLog = new logSet(stage);
        actualWindow.getWindow().close();
    }

    //Popups to edit employes
    @FXML
    protected void openPopupEmploye(ActionEvent event) throws Exception {
        toEditPopUp=null;
        if(event.getTarget().equals(editEmployee)
                && !employeeTable.getSelectionModel().isEmpty()) {
            editWarnigLabel = new TextField();
            editWarnigLabel.clear();
            toEditPopUp = employeeTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader= new FXMLLoader(App.class.getResource("editEmploye.fxml"));
            Scene popupScene = new Scene(loader.load(), 333.0, 765.0);
            popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Stage popup = new Stage();
            popup.initStyle(StageStyle.UNDECORATED);
            popup.setScene(popupScene);
            moveWindow(popupScene, popup);
            popup.show();

        } else if(event.getTarget().equals(addEmployee)) {
            editWarnigLabel = new TextField();
            editWarnigLabel.clear();
            FXMLLoader loader= new FXMLLoader(App.class.getResource("editEmploye.fxml"));
            Scene popupScene = new Scene(loader.load(), 333.0, 780.0);
            popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            Stage popup = new Stage();
            popup.initStyle(StageStyle.UNDECORATED);

            popup.setScene(popupScene);
            moveWindow(popupScene, popup);
            popup.show();

        } else {
            editWarnigLabel = new TextField();
            editWarnigLabel.setText("Nie wybrano żadnego elemantu!");
        }
        loadEmployesToTable();
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
    @FXML
    protected void loadEmployesToTable() throws Exception {
        nr.setCellValueFactory(new PropertyValueFactory<Logins, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Logins, Employee >("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<Logins, Employee>("lastname"));
        pesel.setCellValueFactory(new PropertyValueFactory<Logins, String>("pesel"));
        salary.setCellValueFactory(new PropertyValueFactory<Logins, String>("salary"));
        restuarant.setCellValueFactory(new PropertyValueFactory<Logins,String>("restaurantname"));
        //TODO:
        //trzebba bedzie zmienic zamiast id restaurant na nazwe restauracji - trzeba wykonac zapytanie ze zlaczeniem
        //restaurantID.setCellValueFactory(new PropertyValueFactory<Logins, String>("idrestaurant"));

        employeesList.clear();
        if(searchEmployee.getText().length()>0)
            employeesList.addAll(Client.getEmployeesFullInfo(searchEmployee.getText()));
        else
            employeesList.addAll(Client.getEmployeesFullInfo());
        employeeTable.setItems(employeesList);
    }
    @FXML
    protected void loadProductToTable()throws Exception {
        productName.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
        productCategory.setCellValueFactory(new PropertyValueFactory<Products, String>("category"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Products, Float>("price"));
        productList.clear();

        if(searchProduct.getText().length()>0 || choseCategory.getValue() != null){
            if(choseCategory.getValue() != null){
                productList.addAll(Client.getProducts(searchProduct.getText(),choseCategory.getValue().toString()));
            }
            else
                productList.addAll(Client.getProducts(searchProduct.getText(),""));
        }
        else
            productList.addAll(Client.getProducts());
        tableWithProducts.setItems(productList);

    }
    @FXML
    protected void loadStorageToTable()throws Exception {
        itemId.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("id"));
        itemName.setCellValueFactory(new PropertyValueFactory<Storage, String>("name"));
        itemAmount.setCellValueFactory(new PropertyValueFactory<Storage, String>("amount"));
        itemList.clear();
        itemList.addAll(Client.getStorage());
        tabelWithComponents.setItems(itemList);

    }

    public void editAmountStorage(MouseEvent mouseEvent) throws IOException {
        if(Integer.valueOf(amountOfComponent.getText())>=-99999999){
            toEditStoragePopUp = tabelWithComponents.getSelectionModel().getSelectedItem();
            toEditStoragePopUp.setAmount(Integer.valueOf(amountOfComponent.getText()));
            System.out.println(toEditStoragePopUp.getId());
            Client.updateStorageItem(toEditStoragePopUp);
        }


    }

    public void selectComponent(MouseEvent mouseEvent) {
        toEditStoragePopUp = tabelWithComponents.getSelectionModel().getSelectedItem();
        amountOfComponent.setText(String.valueOf(toEditStoragePopUp.getAmount()));
    }
}
