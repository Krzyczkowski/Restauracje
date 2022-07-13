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

        setActualWindow(employesScene);
        window.show();
    }

    //All fxmls included in general Window
    protected void lodaFxmls() throws IOException {
        employesWindowLoader = new FXMLLoader(App.class.getResource("generalEmployes.fxml"));
        historyWindowLoader = new FXMLLoader(App.class.getResource("generalHistory.fxml"));
        productsWindowLoader = new FXMLLoader(App.class.getResource("generalProducts.fxml"));
        warehouseWindwoLoader = new FXMLLoader(App.class.getResource("generalWarehouse.fxml"));
        orderWindowLoader = new FXMLLoader(App.class.getResource("generalOrder.fxml"));

        orderScene = new Scene(orderWindowLoader.load(), 1200, 700);
        employesScene = new Scene(employesWindowLoader.load(), 1200, 700);
        historyScene = new Scene(historyWindowLoader.load(), 1200, 700);
        warehouseScene = new Scene(warehouseWindwoLoader.load(), 1200, 700);
        productsScene = new Scene(productsWindowLoader.load(), 1200, 700);

        employesScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        historyScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        warehouseScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        productsScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        orderScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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
        setActualWindow(employesScene);
    }

    protected void  setProductsScene() throws IOException {
        setActualWindow(productsScene);
    }

    protected void setWarehouseScene() throws IOException {
        setActualWindow(warehouseScene);
    }

    protected void setHistoryScene() throws IOException {
        setActualWindow(historyScene);
    }

    protected void setOrderScene() throws IOException {
        setActualWindow(orderScene);
    }

}