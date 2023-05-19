package Controller.Components;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Principal extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("vistaMain.fxml")));
        Scene scene = new Scene(root);
        Image icono = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Icon.png")));
        // Establecer el icono de la ventana principal
        stage.getIcons().add(icono);
        stage.setTitle("Encriptamiento");
        stage.setResizable(false);
        stage.setWidth(1100);
        stage.setHeight(700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


