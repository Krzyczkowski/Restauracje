package dwr.company.dwrestauracje;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class App extends Application {
    //Mouse click cordinates
    private double xCordinates = 0;
    private double yCordinates = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("logPanel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //Set borderless window, styles from CSS and move window by mouse
        stage.initStyle(StageStyle.UNDECORATED);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

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


        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}