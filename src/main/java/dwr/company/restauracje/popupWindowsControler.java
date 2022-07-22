package dwr.company.restauracje;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class popupWindowsControler implements Initializable{
    //Mouse click cordinates
    private double xCordinates = 0;
    private double yCordinates = 0;

    //Fields with data
    @FXML
    private TableColumn IngId, IngName, IngAmount,newProductIngId,newProductIngName,newProductIngAmount;
    @FXML
    protected TextField newSecondName, newPesel, newLevel, newSalary, newLogin, newName;
    @FXML
    protected TextField newComponentName,newAmount,newProductPrice;
    @FXML
    private TableView<Storage>componentsTable; // tabela ze wszystkimi skladnikami (lewa w dodaj produkt)
    @FXML
    private TableView<Storage>newProductComponentsTable;
    @FXML
    private Spinner amountToAdd;
    private final ObservableList<Storage> storageList = FXCollections.observableArrayList();
    private Storage selectedIngridient;

    private final ObservableList<Storage> componentProductList = FXCollections.observableArrayList();

    private final List <Storage> ingridients = new ArrayList<>();
    @FXML
    private TextField newProductName, newCategoryName;
    @FXML
    private PasswordField newPassword;
    private String command;
    private int id;
    //Button
    @FXML
    private Button exitButton;
    @FXML
    private Button minimalizeButton;
    @FXML
    private Button saveEmploye;
    @FXML
    private Button addToProductComponents;
    @FXML
    private Button deleteFromProductComponents;
    @FXML
    private Button saveNewProduct;
    @FXML
    private Button saveNewCategory;
    @FXML
    private Button deleteCategory;
    @FXML
    private Button yesButton, noButton;
    @FXML
    private ComboBox newPlace;
    @FXML
    private ComboBox newProductCategory;

    @FXML
    private Label warningLabelEmp;

    //Other declarations

    @FXML
    private Label warningLabel4;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switch (GeneralController.popup){
            case 0:
                if(GeneralController.toEditPopUp != null){
                    loadWorkerData();
                } else {
                    command = "insert";
                    try {
                        newPlace.getItems().addAll(Client.getAllRestaurants());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case 1:
                break;
            case 2:
                try {
                    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100) ;
                    valueFactory.setValue(1);
                    amountToAdd.setValueFactory(valueFactory);
                    loadPopupIngridients();
                    try{if(GeneralController.selectedProduct != null);
                        loadProductInfo();}catch (Exception e){}

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    private void loadProductInfo() {
        Products p = GeneralController.selectedProduct;
        newProductName.setText(p.getName());
        for(int i = 0;i< newProductCategory.getItems().size();i++){
            if(p.getCategory().equals( newProductCategory.getItems().get(i))){
                newProductCategory.getSelectionModel().select(i);
            }
        }

        newProductPrice.setText(String.valueOf(p.getPrice()));
    }

    private void loadPopupIngridients() throws IOException {
        newProductCategory.getItems().clear();
        newProductCategory.getItems().addAll(Client.getCategories());
        List<Storage> sl = Client.getStorage();
        IngId.setCellValueFactory(new PropertyValueFactory<Positions, String>("id"));
        IngName.setCellValueFactory(new PropertyValueFactory<Positions, Integer>("name"));
        IngAmount.setCellValueFactory(new PropertyValueFactory<Positions, Integer>("amount"));
        storageList.clear();
        storageList.addAll(sl);
        componentsTable.setItems(storageList);

    }
    @FXML
    protected void createCategory() throws IOException {
        if(!newCategoryName.getText().equals("")){
            int j = 0;
            for(int i = 0; i<newProductCategory.getItems().size();i++){
                if(newProductCategory.getItems().get(i).toString().equals(newCategoryName.getText())){
                    j=1;
                    break;
                }
            }
            if(j==0){
                Client.insertCategory(newCategoryName.getText());
                newProductCategory.getItems().clear();
                newProductCategory.getItems().addAll(Client.getCategories());
                newProductCategory.getSelectionModel().selectLast();
                newCategoryName.setText("");
                warningLabel4.setText("");
                }
            else{
                //warning jest taka kategoria
                warningLabel4.setText("Taka kategoria już istnieje");
            }
        }
        else{
            warningLabel4.setText("Podaj nazwe kategorii");
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
    protected void saveEmployeInfo() throws IOException {
        if(newName.getText().equals("")||newSecondName.getText().equals("")||
                newLogin.getText().equals("")||newSalary.getText().equals("")||
                    newPlace.getSelectionModel().isEmpty()||newPesel.getText().equals("")||
                    newPassword.getText().equals("")||newLevel.getText().equals("")){
                warningLabelEmp.setText("uzupełnij dane");
        }else{
            try{
                Integer.parseInt(newLevel.getText());
                Integer.parseInt(newPesel.getText());
                Float.parseFloat(newSalary.getText());
            }catch (NumberFormatException e){
                warningLabelEmp.setText("nie poprawny format liczb");
                return;
            }
            if(Integer.parseInt(newLevel.getText())>Client.getLevelacces()) {
                warningLabelEmp.setText("Nie poprawny poziom dostępu");
                return;
            }
            if (command.equals("update")){
                Client.updateEmployee(newName.getText(),newSecondName.getText(),
                        id,newLogin.getText(),newPassword.getText(),Integer.parseInt(newLevel.getText()),
                        newPlace.getValue().toString(),Float.parseFloat(newSalary.getText()),Integer.parseInt(newPesel.getText()));
            }
            else if (command.equals("insert")) {
                Client.insertEmployee(newName.getText(),newSecondName.getText(),
                        newLogin.toString(),newPassword.toString(),Integer.parseInt(newLevel.getText()),
                        newPlace.getValue().toString(),Float.parseFloat(newSalary.getText()),Integer.parseInt(newPesel.getText()));
            }
            GeneralController.toEditPopUp = null;
            Stage stage = (Stage) exitButton.getScene().getWindow();
            warningLabelEmp.setText("");
            GeneralController.employeesList.clear();
            GeneralController.employeesList.addAll(Client.getEmployeesFullInfo());
            stage.close();
            }
        }

    @FXML
    protected void saveProductInfo(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    protected void saveComponentInfo()throws IOException {
            Storage s = new Storage(newComponentName.getText(),Integer.parseInt(newAmount.getText()));
            Client.insertStorageItem(s);
            GeneralController.itemList.clear();
            GeneralController.itemList.addAll(Client.getStorage());
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
    }

    @FXML
    protected void warningWindow() throws IOException {
        GeneralController.popup = 3;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("deleteCatgory.fxml"));
        Scene popupScene = new Scene(loader.load(), 437.0, 167.0);
        //popupScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        popupScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        Stage popup = new Stage();
        popup.initStyle(StageStyle.UNDECORATED);
        popup.setScene(popupScene);
        moveWindow(popupScene, popup);
        popup.show();
    }

    @FXML
    protected void decision(ActionEvent event){
        if(event.getTarget().equals(yesButton)){
            Stage stage = (Stage) yesButton.getScene().getWindow();
            stage.close();
        }else if (event.getTarget().equals(noButton)){
            Stage stage = (Stage) noButton.getScene().getWindow();
            stage.close();
        }
    }

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

    protected void loadWorkerData(){
        newName.setText(GeneralController.toEditPopUp.getEmp().getName());
        newSecondName.setText((GeneralController.toEditPopUp.getEmp().getLastname()));
        newPesel.setText((GeneralController.toEditPopUp.getPesel().toString()));
        newLevel.setText((GeneralController.toEditPopUp.getLevelaccess().toString()));
        newSalary.setText(String.valueOf(GeneralController.toEditPopUp.getSalary()));
        newLogin.setText((GeneralController.toEditPopUp.getLogin()));
        newPassword.setText((GeneralController.toEditPopUp.getPassword()));
        id=GeneralController.toEditPopUp.getId();

        try {
            newPlace.getItems().addAll(Client.getAllRestaurants());
            for (int i = 0; i<newPlace.getItems().size();i++){
                if (newPlace.getItems().get(i).toString().equals( GeneralController.toEditPopUp.getRestaurantname()))
                {
                    newPlace.getSelectionModel().select(i);
                }
            }
        } catch (IOException e) {
            //System.out.println(e);
        }
        command = "update";
    }


    public void addIngridientToProduct(){
        if (!componentsTable.getSelectionModel().isEmpty()) {
            selectedIngridient = componentsTable.getSelectionModel().getSelectedItem();
            selectedIngridient.setAmount(Integer.parseInt(amountToAdd.getValue().toString()) );
            ingridients.add(selectedIngridient);
            loadNewProductIngridientsTable(ingridients);
            warningLabel4.setText("");
            amountToAdd.getValueFactory().setValue(1);
        }
        else
            warningLabel4.setText("wybierz składnik do dodania");
    }
    private void loadNewProductIngridientsTable(List<Storage> ingridients) {
        newProductIngId.setCellValueFactory(new PropertyValueFactory<Positions, String>("id"));
        newProductIngName.setCellValueFactory(new PropertyValueFactory<Positions, Integer>("name"));
        newProductIngAmount.setCellValueFactory(new PropertyValueFactory<Positions, Integer>("amount"));
        componentProductList.clear();
        componentProductList.addAll(ingridients);
        newProductComponentsTable.setItems(componentProductList);
    }

    public void removeIngridientFromProduct() {
        selectedIngridient = newProductComponentsTable.getSelectionModel().getSelectedItem();
        if (!newProductComponentsTable.getSelectionModel().isEmpty()) {
            selectedIngridient = newProductComponentsTable.getSelectionModel().getSelectedItem();
            ingridients.remove(selectedIngridient);
            loadNewProductIngridientsTable(ingridients);
            warningLabel4.setText("");
        }
        else
            warningLabel4.setText("wybierz składnik do usunięcia");
    }

    public void onSaveProduct() throws IOException {
        if(newProductName.getText().equals("")|| newProductCategory.getSelectionModel().isEmpty()|| newProductComponentsTable.getItems().size()==0 || newProductPrice.getText().equals("") ){
            String s ="Należy wypełnić: ";
            if(newProductName.getText().equals(""))
                s+="nazwe, ";
            if( newProductCategory.getSelectionModel().isEmpty())
                s+="kategorie, ";
            if(newProductComponentsTable.getItems().size()==0)
                s+="składniki, ";
            if(newProductPrice.getText().equals(""))
                s+="cena, ";
            warningLabel4.setText(s);
        }
        else{
            try{
                Float.parseFloat(newProductPrice.getText());
            }catch (NumberFormatException e){
                warningLabel4.setText("Podaj liczbe np. 2.5");
                return;
            }
            //wysłanie zapytania
            List<Storage> listOfIngridientsInNewProduct;
            listOfIngridientsInNewProduct=componentProductList;
            Products p = new Products();
            p.setId(0);
            p.setCategory(newProductCategory.getValue().toString());
            p.setName(newProductName.getText());
            p.setRestaurant(Client.restaurantName);
            p.setPrice(Float.parseFloat(newProductPrice.getText()));
            ingridients.clear();
            newProductPrice.setText("");
            Client.makeProduct(p,listOfIngridientsInNewProduct);
            warningLabel4.setText("");
            newProductName.setText("");
            newProductComponentsTable.getItems().clear();
        }
    }
}




