package Controller.Components;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControladorResultado implements Initializable {
    @FXML
    AnchorPane modal;
    @FXML GridPane matrizClave = new GridPane();
    @FXML private Label labelModal;
    @FXML private TextField textModal;
    @FXML
    private Pane panel_mat;
    @FXML private ImageView iconNotify;

    String mensaje;
    int[][] matriz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarModal();
    }

    @FXML public void mostrarResultado(boolean resultado, String operacion, String mensaje) {
        if(resultado){
            if(operacion.equals("encriptar")){
                labelModal.setText("Mensaje encriptado correctamente");
            } else {
                labelModal.setText("Mensaje desencriptado correctamente");
            }
            textModal.setText(mensaje);
            textModal.setEditable(false);
        }
    }

    @FXML public void cerrarModalAction(ActionEvent event) throws IOException {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), modal);
        translate.setByY(-100);

        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), modal);
        fade.setToValue(0);

        ParallelTransition parallel = new ParallelTransition(translate, fade);
        parallel.play();

        parallel.setOnFinished(event2 -> modal.setVisible(false));
        //modal.setVisible(false);
        panel_mat.getChildren().remove(matrizClave);
    }

    @FXML public void llenarMatrizResultado(int[][] matriz){
        matrizClave.setId("matrizClave");
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(100.0 / matriz[0].length);
        RowConstraints rowConst = new RowConstraints();
        rowConst.setPercentHeight(100.0 / matriz.length);

        for (int i = 0; i < matriz[0].length; i++) {
            matrizClave.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < matriz.length; i++) {
            matrizClave.getRowConstraints().add(rowConst);
        }

        for (int row = 0; row < matriz.length; row++) {
            for (int col = 0; col < matriz[0].length; col++) {
                TextField textField = new TextField();
                textField.setText(matriz[row][col] + " ");
                textField.setId("res_Matriz");
                textField.setEditable(false);
                textField.setAlignment(Pos.CENTER);
                textField.setPrefHeight(100);
                matrizClave.add(textField, col, row);
            }
        }

        panel_mat.getChildren().add(matrizClave);

        matrizClave.prefWidthProperty().bind(panel_mat.widthProperty());
        matrizClave.prefHeightProperty().bind(panel_mat.heightProperty());
    }

    @FXML public void cargarModal(){
        modal.setOpacity(0);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), modal);
        transition.setFromY(700);
        transition.setToY(10);
        transition.setCycleCount(1);

        FadeTransition fade = new FadeTransition(Duration.seconds(0.7), modal);
        fade.setToValue(1); // establecemos la opacidad final del panel en 1

        ParallelTransition parallel = new ParallelTransition(transition, fade);
        parallel.play();
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }
}
