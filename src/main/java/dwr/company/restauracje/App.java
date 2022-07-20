package dwr.company.restauracje;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new logSet(stage);
    }
}
//fxml : barchart dla
//<BarChart fx:id="chartWithIncome" layoutX="41.0" layoutY="422.0" prefHeight="246.0" prefWidth="769.0">
//<xAxis>
//<CategoryAxis side="BOTTOM" />
//</xAxis>
//<yAxis>
//<NumberAxis side="LEFT" />
//</yAxis>
//</BarChart>