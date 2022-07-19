package dwr.company.restauracje;

import entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class popupWindowsControler implements Initializable{
    //Fields with data
    @FXML
    private TableColumn IngId, IngName, IngAmount,newProductIngId,newProductIngName,newProductIngAmount;
    @FXML
    protected TextField newSecondName, newPesel, newLevel, newSalary, newLogin, newName;
    @FXML
    protected TextField newComponentName,newAmount;
    @FXML
    private TableView<Storage>componentsTable; // tabela ze wszystkimi skladnikami (lewa w dodaj produkt)
    @FXML
    private TableView<Storage>newProductComponentsTable;
    private ObservableList<Storage> storageList = FXCollections.observableArrayList();
    private Storage selectedIngridient;

    private ObservableList<Storage> componentProductList = FXCollections.observableArrayList();

    private List <Storage> ingridients = new ArrayList<>();
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
    private ComboBox newPlace;
    @FXML
    private ComboBox newProductCategory;


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
                }
                break;
            case 1:
                break;
            case 2:
                try {
                    loadPopupIngridients();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                break;
        }
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
        if (command.equals("update")){
            Client.updateEmployee(newName.getText(),newSecondName.getText(),
                    id,newLogin.getText(),newPassword.getText(),Integer.parseInt(newLevel.getText()),
                    (String)newPlace.getValue(),Float.parseFloat(newSalary.getText()),Integer.parseInt(newPesel.getText()));

        }
        else if (command.equals("insert")) {
            Client.insertEmployee(newName.getText(),newSecondName.getText(),
                    0,newLogin.toString(),newPassword.toString(),2,
                        "macdonald",Float.parseFloat(newSalary.getText()),Integer.parseInt(newPesel.getText()));
        }
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
    protected void saveComponentInfo()throws IOException {
            Storage s = new Storage(newComponentName.getText(),Integer.valueOf(newAmount.getText()));
            Client.insertStorageItem(s);
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();

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
            System.out.println(e);
        }
        command = "update";
    }


    public void addIngridientToProduct(MouseEvent mouseEvent) throws IOException {
        if (!componentsTable.getSelectionModel().isEmpty()) {
            selectedIngridient = componentsTable.getSelectionModel().getSelectedItem();
            ingridients.add(selectedIngridient);
            loadNewProductIngridientsTable(ingridients);
            warningLabel4.setText("");
        }
        else
            warningLabel4.setText("wybierz składnik do dodania");
    }
    private void loadNewProductIngridientsTable(List<Storage> ingridients) throws IOException {
        newProductIngId.setCellValueFactory(new PropertyValueFactory<Positions, String>("id"));
        newProductIngName.setCellValueFactory(new PropertyValueFactory<Positions, Integer>("name"));
        newProductIngAmount.setCellValueFactory(new PropertyValueFactory<Positions, Integer>("amount"));
        componentProductList.clear();
        componentProductList.addAll(ingridients);
        newProductComponentsTable.setItems(componentProductList);
    }

    public void removeIngridientFromProduct(MouseEvent mouseEvent) throws IOException {
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
}




