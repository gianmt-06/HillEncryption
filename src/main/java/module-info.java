module com.example.encriptamiento {
    requires javafx.controls;
    requires javafx.fxml;
    requires Jama;

    exports Controller.Components;
    opens Controller.Components to javafx.fxml;
}