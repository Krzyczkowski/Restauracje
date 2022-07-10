package dwr.company.dwrestauracje;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class GeneralWindowSet {
    //Mouse click cordinates
    private double xCordinates = 0;
    private double yCordinates = 0;

    GeneralWindowSet() throws IOException {
        FXMLLoader mainWindowLoader = new FXMLLoader(App.class.getResource("generalWindow.fxml"));
        Scene mainWidowScene = new Scene(mainWindowLoader.load(), 1200, 700);
        Stage mainWindow = new Stage();

        //Set borderless window, styles from CSS and move window by mouse
        mainWindow.initStyle(StageStyle.UNDECORATED);
        mainWidowScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        mainWidowScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xCordinates = mouseEvent.getSceneX();
                yCordinates = mouseEvent.getSceneY();
            }
        });

        mainWidowScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mainWindow.setX(mouseEvent.getScreenX() - xCordinates);
                mainWindow.setY(mouseEvent.getScreenY() - yCordinates);
            }
        });

        mainWindow.setTitle("Menagero");
        mainWindow.setScene(mainWidowScene);
        mainWindow.show();
    }

}