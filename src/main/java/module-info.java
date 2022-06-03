module dwr.company.restauracje {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens dwr.company.restauracje to javafx.fxml;
    exports dwr.company.restauracje;
}