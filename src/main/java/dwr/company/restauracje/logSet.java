package dwr.company.restauracje;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class logSet {
    //Cordinates
    private double xCordinates = 0;
    private double yCordinates = 0;


    logSet(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("logPanel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //Set borderless window, styles from CSS and move window by mouse
        stage.initStyle(StageStyle.UNDECORATED);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        scene.setOnMousePressed(mouseEvent -> {
            xCordinates = mouseEvent.getSceneX();
            yCordinates = mouseEvent.getSceneY();
        });

        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xCordinates);
            stage.setY(mouseEvent.getScreenY() - yCordinates);
        });


        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
