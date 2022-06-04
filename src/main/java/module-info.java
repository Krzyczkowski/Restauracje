module dwr.company.restauracje {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires json.simple;

    opens dwr.company.restauracje to javafx.fxml;
    exports dwr.company.restauracje;
}