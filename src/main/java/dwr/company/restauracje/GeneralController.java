package dwr.company.restauracje;

import entity.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
public class GeneralController{
    //Mouse click cordinates
    private double xCordinates = 0;
    private double yCordinates = 0;
    //Scene holder

    GeneralWindowSet actualWindow;
    public static int popup;
    //Data Fields
    //Labels
    @FXML
    private Circle userIcon;
    @FXML
    public Label orderPrice;
    @FXML
    private Label userName;
//    @FXML
//    private Label welcomeText;
    @FXML
    private Label warnigLabel1 , warningLabel2, warningLabel3;

    //Tabel Views and columns
    @FXML
    private TableView<Logins> employeeTable;
    @FXML
    private TableView<Products> tableWithProducts;
    @FXML
    private TableView<Products> tableWithProductsOrders;
    @FXML
    private TableView<Storage>tabelWithComponents;
    @FXML
    private TableView<Orders>tableWithHistory;
    @FXML
    private TableView<Positions> tableWithPositions,PositionsEditionTable; // tabela z pozycjami w zamowieniu (ta na dole)
//    @FXML
//    private TableView<Logins>logHistory;
    @SuppressWarnings("rawtypes")
    @FXML
    private TableColumn nr,name,lastName,pesel,salary,restuarant;
    @FXML
    private TableColumn priceProductOrder, nameProductOrder,PositionsEditionProductName,PositionsEditionProductAmount,PositionsEditionProductPrice;
    @FXML
    private TableColumn productName,productCategory,productPrice,PositionsProduct, PositionsAmount,PositionsCost;
    @FXML
    private TableColumn historyid,historydate,historyprice,historyorder;

    @FXML
    private TableColumn itemName,itemAmount,itemId;

    //Spiners, comboboxes
    @FXML
    private Spinner<Integer> amountOfProductsInOrder; //= new Spinner<>(1,20,1);
    @FXML
    private ComboBox choseCategory;
    @FXML
    private ComboBox categoriesOrders;

    //Text Fields
    @FXML
    private TextField searchProduct;
    @FXML
    private TextField searchEmployee;
//    @FXML
//    public TextField editWarnigLabel;
    @FXML
    public TextField searchProductOrders;
    @FXML
    public TextField amountOfComponent;
    @FXML
    private TextField clientPhone,clientAddress;

    //Buttons
    @FXML
    private Button minimalizeButton;
    @FXML
    private Button menuButton0, menuButton1, menuButton2, menuButton3, menuButton4;
    @FXML
    private Button findEmployee, editEmployee, addEmployee;
    @FXML
    private Button addComponent, editAmount, deleteComponent;
    @FXML
    private Button addProduct, editProduct, deletProduct;

    //Elements dependent on data base
    private final ObservableList<Logins> employeesList = FXCollections.observableArrayList();
    private final ObservableList<Products> productList = FXCollections.observableArrayList();
    private final ObservableList<Storage> itemList = FXCollections.observableArrayList();
    private final ObservableList<Positions> positionList = FXCollections.observableArrayList();
    private final ObservableList<Orders> historyOrders = FXCollections.observableArrayList();

    public static Logins toEditPopUp;
    public static Storage toEditStoragePopUp;

    public static Products selectedProduct;
    public static Positions selectedPostion;
    public static Orders selectedOrder;

    private List<Positions> tempOrder = new ArrayList<>(); // temp variable which helps with order



    @FXML
    public void initialize(){
        try {//Loading correct screen
            switch (GeneralWindowSet.layout){
                case 0:
                    loadEmployesToTable();
                    break;
                case 1:
                    loadProductToTable();
                    break;
                case 2:
                    loadStorageToTable();
                    break;
                case 3:
                    loadHistoryToTable();
                    break;
                case 4:
                    loadProductToTableOrders();
                    loadPositionsTable();
                    categoriesOrders.getItems().clear();
                    categoriesOrders.getItems().addAll(Client.getCategories());
                    categoriesOrders.getItems().add(null);
                    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100) ;
                    valueFactory.setValue(1);
                    amountOfProductsInOrder.setValueFactory(valueFactory);
                    loadPositionsTable();
                    break;
                default:
                    break;
            }
            userName.setText(Client.usrName);
            Image iconImage = new Image("/person.png", false);
            userIcon.setFill(new ImagePattern(iconImage));
            userIcon.setEffect(new DropShadow(20, Color.WHITESMOKE));
            acces();

            tempOrder = new ArrayList<>();
        } catch (Exception er) {
            //er.printStackTrace();
        }
    }
    public void acces() {
        if (Client.getLevelacces() < 3) {
            if (Client.getLevelacces() < 2) {
                menuButton1.setDisable(true);
                menuButton2.setDisable(true);
                menuButton3.setDisable(true);
            }
        }
    }

    //Deal with some actions int window
    @FXML
    protected void onExitbutton() throws IOException {
        try {
            Client.logout();
        }catch (Exception e){}

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
        acces();
        actualWindow = new GeneralWindowSet();
        actualWindow.setEmployesScene();
    }

    @FXML
    protected void productSection() throws IOException {
        acces();
        actualWindow = new GeneralWindowSet();
        actualWindow.setProductsScene();

    }

    @FXML
    protected void warehouseSection() throws Exception {
        acces();
        actualWindow = new GeneralWindowSet();
        actualWindow.setWarehouseScene();
    }

    @FXML
    protected void historySection() throws IOException {
        acces();
        actualWindow = new GeneralWindowSet();
        actualWindow.setHistoryScene();
    }

    @FXML
    protected void orderSection() throws Exception {

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
        toEditPopUp = null;
        if (event.getTarget().equals(editEmployee)
                && !employeeTable.getSelectionModel().isEmpty()) {
            toEditPopUp = employeeTable.getSelectionModel().getSelectedItem();
            popup = 0;
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editEmploye.fxml"));
            Scene popupScene = new Scene(loader.load(), 333.0, 765.0);
            popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Stage popup = new Stage();
            popup.initStyle(StageStyle.UNDECORATED);
            popup.setScene(popupScene);
            moveWindow(popupScene, popup);
            popup.show();
            warnigLabel1.setText("");

        } else if (event.getTarget().equals(addEmployee)) {
            popup = 0;
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editEmploye.fxml"));
            Scene popupScene = new Scene(loader.load(), 333.0, 780.0);
            popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            Stage popup = new Stage();
            popup.initStyle(StageStyle.UNDECORATED);

            popup.setScene(popupScene);
            moveWindow(popupScene, popup);
            popup.show();
            warnigLabel1.setText("");
        } else {
            warnigLabel1.setText("Nie wybrano żadnego elemantu!");
        }
    }

    @FXML
    protected void openPopupProduct(ActionEvent event) throws IOException {
        if(event.getTarget().equals(addProduct)) {
            popup = 2;
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editProduct.fxml"));
            Scene popupScene = new Scene(loader.load(), 767.0, 730.0);
            popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Stage popup = new Stage();
            popup.initStyle(StageStyle.UNDECORATED);
            popup.setScene(popupScene);
            moveWindow(popupScene, popup);
            popup.show();
        } else if (event.getTarget().equals(editProduct)){
            popup = 2;
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editProduct.fxml"));
            Scene popupScene = new Scene(loader.load(), 767.0, 730.0);
            popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            Stage popup = new Stage();
            popup.initStyle(StageStyle.UNDECORATED);
            popup.setScene(popupScene);
            moveWindow(popupScene, popup);
            popup.show();
        } else {
        }
    }

    @FXML
    protected void openPopupComponent(ActionEvent event) throws IOException {
        if(event.getTarget().equals(addComponent)) {
            popup = 1;
            FXMLLoader loader = new FXMLLoader(App.class.getResource("editComponent.fxml"));
            Scene popupScene = new Scene(loader.load(), 333.0, 267.0);
            //popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            popupScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
            Stage popup = new Stage();
            popup.initStyle(StageStyle.UNDECORATED);
            popup.setScene(popupScene);
            moveWindow(popupScene, popup);
            popup.show();
            warningLabel2.setText("");

        } else if (event.getTarget().equals(editAmount)
                && !tabelWithComponents.getSelectionModel().isEmpty()){
            warningLabel2.setText("");

        } else if (event.getTarget().equals(deleteComponent)
                && !tabelWithComponents.getSelectionModel().isEmpty()){
            warningLabel2.setText("");

        } else {
            warningLabel2.setText("Nie wybrano żadnego elemantu!");
        }
    }


    //Moveing popup's
    protected void moveWindow(Scene scene, Stage stage) {
        scene.setOnMousePressed(mouseEvent -> {
            xCordinates = mouseEvent.getSceneX();
            yCordinates = mouseEvent.getSceneY();
        });

        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xCordinates);
            stage.setY(mouseEvent.getScreenY() - yCordinates);
        });
    }
    //Deal with data froma database
    @FXML
    protected void loadEmployesToTable() throws Exception {
        nr.setCellValueFactory(new PropertyValueFactory<Logins, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Logins, Employee>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<Logins, Employee>("lastname"));
        pesel.setCellValueFactory(new PropertyValueFactory<Logins, String>("pesel"));
        salary.setCellValueFactory(new PropertyValueFactory<Logins, String>("salary"));
        restuarant.setCellValueFactory(new PropertyValueFactory<Logins, String>("restaurantname"));
        employeesList.clear();
        if (searchEmployee.getText().length() > 0)
            employeesList.addAll(Client.getEmployeesFullInfo(searchEmployee.getText()));
        else
            employeesList.addAll(Client.getEmployeesFullInfo());
        employeeTable.setItems(employeesList);
    }
    private void loadHistoryToTable() throws IOException {
        historyid.setCellValueFactory(new PropertyValueFactory<Logins, Integer>("idclient"));
        historyorder.setCellValueFactory(new PropertyValueFactory<Logins, Integer>("id"));
        historyprice.setCellValueFactory(new PropertyValueFactory<Logins, Float>("totalprice"));
        historydate.setCellValueFactory(new PropertyValueFactory<Logins, Date>("dates"));
        historyOrders.clear();
        historyOrders.addAll(Client.getOrders( new Date(System.currentTimeMillis()).toString()));
        tableWithHistory.setItems(historyOrders);
    }
    @FXML
    public void loadProductToTable() throws Exception {
        choseCategory.getItems().addAll(Client.getCategories());
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
    protected void loadProductToTableOrders() throws Exception {
        nameProductOrder.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
        priceProductOrder.setCellValueFactory(new PropertyValueFactory<Products, Float>("price"));
        productList.clear();
        if (searchProductOrders.getText().length() > 0 || categoriesOrders.getValue() != null) {
            if (categoriesOrders.getValue() != null) {
                productList.addAll(Client.getProducts(searchProductOrders.getText(), categoriesOrders.getValue().toString()));
            } else
                productList.addAll(Client.getProducts(searchProductOrders.getText(), ""));
        } else
            productList.addAll(Client.getProducts());
        tableWithProductsOrders.setItems(productList);
    }
    @FXML
    protected void loadPositionsTable() {
        PositionsProduct.setCellValueFactory(new PropertyValueFactory<Positions, String>("productName"));
        PositionsAmount.setCellValueFactory(new PropertyValueFactory<Positions, Integer>("amount"));
        PositionsCost.setCellValueFactory(new PropertyValueFactory<Positions, Float>("productPrice"));
        positionList.addAll(tempOrder);
        tableWithPositions.setItems(positionList);
    }
    @FXML
    protected void loadStorageToTable() throws Exception {
        itemId.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("id"));
        itemName.setCellValueFactory(new PropertyValueFactory<Storage, String>("name"));
        itemAmount.setCellValueFactory(new PropertyValueFactory<Storage, String>("amount"));
        itemList.clear();
        itemList.addAll(Client.getStorage());
        tabelWithComponents.setItems(itemList);
    }

    public void editAmountStorage() throws Exception {
        if (Integer.parseInt(amountOfComponent.getText()) >= -99999999 && !tabelWithComponents.getSelectionModel().isEmpty()) {
            toEditStoragePopUp = tabelWithComponents.getSelectionModel().getSelectedItem();
            toEditStoragePopUp.setAmount(Integer.parseInt(amountOfComponent.getText()));
            Client.updateStorageItem(toEditStoragePopUp);
        }
    }

    public void selectComponent() {
        toEditStoragePopUp = tabelWithComponents.getSelectionModel().getSelectedItem();
 //       amountOfComponent.setText(String.valueOf(toEditStoragePopUp.getAmount()));
    }

    public void deleteStorage() throws IOException {
        if (!tabelWithComponents.getSelectionModel().isEmpty()) {
            toEditStoragePopUp = tabelWithComponents.getSelectionModel().getSelectedItem();
            Client.deleteStorage(toEditStoragePopUp);
        }
    }

    public void addProductToOrder() {
        if (!tableWithProductsOrders.getSelectionModel().isEmpty()) {
            selectedProduct = tableWithProductsOrders.getSelectionModel().getSelectedItem();
            Positions pos = new Positions(0, selectedProduct.getId(), amountOfProductsInOrder.getValue(), 0, selectedProduct.getName(), selectedProduct.getPrice());
            //System.out.println("id:"+pos.getId()+"amount:"+pos.getAmount()+"idorder:"+pos.getIdorder()+"idproduct:"+pos.getIdproduct());
            tempOrder.clear();
            tempOrder.add(pos);
            positionList.addAll(tempOrder);
            tableWithPositions.setItems(positionList);
            loadAllPrice();
            amountOfProductsInOrder.getValueFactory().setValue(1);
        }
    }

    public void deleteProductFromOrder() {
        if (!tableWithPositions.getSelectionModel().isEmpty()) {
            selectedPostion = tableWithPositions.getSelectionModel().getSelectedItem();
            tableWithPositions.getItems().remove(selectedPostion);
            loadAllPrice();
        }
    }

    private void loadAllPrice() {
        float f = 0;
        for (int i = 0; i < positionList.size(); i++)
            f += tableWithPositions.getItems().get(i).getProductPrice();
        orderPrice.setText(String.valueOf(f));
    }

    private Clients getClientInfo() {
        Clients c = new Clients();
        c.setAddress(clientAddress.getText());
        c.setPhone(clientPhone.getText());
        return c;
    }

    public void setClientLocal() {
        clientAddress.setText("LOKAL");
        clientPhone.setText("000000000");
    }

    public void makeOrder() throws IOException {
        Date date = new Date(System.currentTimeMillis());
        tempOrder.clear();
        if(positionList.size()>0) {
            for (int i = 0; i < positionList.size(); i++) {
                tempOrder.add(tableWithPositions.getItems().get(i));
                tempOrder.get(i).setProductName(tableWithPositions.getItems().get(i).getProductName());
                tempOrder.get(i).setProductPrice(tempOrder.get(i).getAmount() * tableWithPositions.getItems().get(i).getProductPrice());
            }
            Clients c = getClientInfo();
            if (c.getPhone().equals("")||c.getAddress().equals(""))
            {
                warningLabel3.setText("brak danych klienta");
                return;
            }
            Orders order = new Orders(0,Client.id,c.getPhone(),Float.parseFloat(orderPrice.getText()),date,Client.restaurantName);
            OrderContainer orderContainer= new OrderContainer(order,tempOrder,c);
            Client.makeOrder(orderContainer);
            tableWithPositions.getItems().clear();
            orderPrice.setText("0.0");
            warningLabel3.setText("");
        }
        else
            warningLabel3.setText("brak wybranych produktów");
    }

    public void selectOrderToEdit() throws IOException {
        selectedOrder = tableWithHistory.getSelectionModel().getSelectedItem();
        LoadPositionsEditionTable(selectedOrder);
    }

    public void LoadPositionsEditionTable(Orders order) throws IOException {
        List<Positions> pl = Client.getPositions(order.getId());
        System.out.println(order);
        PositionsEditionProductName.setCellValueFactory(new PropertyValueFactory<Positions, String>("productName"));
        PositionsEditionProductAmount.setCellValueFactory(new PropertyValueFactory<Positions, Integer>("amount"));
        PositionsEditionProductPrice.setCellValueFactory(new PropertyValueFactory<Positions, Float>("productPrice"));
        positionList.clear();
        positionList.addAll(pl);
        PositionsEditionTable.setItems(positionList);
    }

    public void deleteIngridientFromStorage(MouseEvent mouseEvent) throws IOException {
        toEditStoragePopUp = tabelWithComponents.getSelectionModel().getSelectedItem();
        Client.deleteStorage(toEditStoragePopUp);
    }
}
