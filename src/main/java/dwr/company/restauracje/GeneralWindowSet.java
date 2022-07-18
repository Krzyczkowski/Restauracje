package dwr.company.restauracje;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class GeneralWindowSet {
    //Scenes, stage, loaders
    protected static Stage window;
    protected static Scene employesScene, productsScene, warehouseScene, historyScene, orderScene;
    protected FXMLLoader employesWindowLoader, historyWindowLoader, productsWindowLoader, warehouseWindwoLoader, orderWindowLoader;

    //Information about which window should be loaded
    public static int layout;

    //Mouse click cordinates
    private double xCordinates = 0;
    private double yCordinates = 0;

    GeneralWindowSet() throws IOException {
    }

    public static Stage getWindow() {
        return window;
    }

    //Open this window
    protected void firstUsage() throws IOException {
        window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.setTitle("Menagero");

        setOrderScene();
        window.show();
    }

    //Switch between options in main Window
    protected void setActualWindow(Scene actual){
        //Set borderless window, styles from CSS and move window by mouse
        actual.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xCordinates = mouseEvent.getSceneX();
                yCordinates = mouseEvent.getSceneY();
            }
        });

        actual.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                window.setX(mouseEvent.getScreenX() - xCordinates);
                window.setY(mouseEvent.getScreenY() - yCordinates);
            }
        });

        window.setScene(actual);
    }

    //Type of this window
    protected void setEmployesScene() throws IOException {
        layout = 0;
        employesWindowLoader = new FXMLLoader(App.class.getResource("generalEmployes.fxml"));
        employesScene = new Scene(employesWindowLoader.load(), 1200, 700);
        employesScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        setActualWindow(employesScene);
    }

    protected void  setProductsScene() throws IOException {
        layout = 1;
        productsWindowLoader = new FXMLLoader(App.class.getResource("generalProducts.fxml"));
        productsScene = new Scene(productsWindowLoader.load(), 1200, 700);
        productsScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        setActualWindow(productsScene);
    }

    protected void setWarehouseScene() throws IOException {
        layout = 2;
        warehouseWindwoLoader = new FXMLLoader(App.class.getResource("generalWarehouse.fxml"));
        warehouseScene = new Scene(warehouseWindwoLoader.load(), 1200, 700);
        warehouseScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        setActualWindow(warehouseScene);
    }

    protected void setHistoryScene() throws IOException {
        layout = 3;
        historyWindowLoader = new FXMLLoader(App.class.getResource("generalHistory.fxml"));
        historyScene = new Scene(historyWindowLoader.load(), 1200, 700);
        historyScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        setActualWindow(historyScene);
    }

    protected void setOrderScene() throws IOException {
        layout = 4;
        orderWindowLoader = new FXMLLoader(App.class.getResource("generalOrder.fxml"));
        orderScene = new Scene(orderWindowLoader.load(), 1200, 700);
        orderScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        setActualWindow(orderScene);
    }

}