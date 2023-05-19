package Controller.Components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador implements Initializable {
    @FXML private GridPane gridPane = new GridPane();
    @FXML private TextField mesaje;
    @FXML private Pane paneMatriz;
    @FXML private AnchorPane padre;
    @FXML Pane sombra;
    private String mensaje;
    private int[][] matriz;

    @FXML private Button eliminar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createGridPane(2, 2);
    }

    public void createGridPane(int numRows, int numCols) {
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(100.0 / numCols);
        RowConstraints rowConst = new RowConstraints();
        rowConst.setPercentHeight(100.0 / numRows);

        for (int i = 0; i < numCols; i++) {
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            gridPane.getRowConstraints().add(rowConst);
        }

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                TextField textField = new TextField();
                textField.setId("matriz_Text");
                textField.setAlignment(Pos.CENTER);
                textField.setPrefHeight(100);
                gridPane.add(textField, col, row);
            }
        }

        gridPane.setVgap(4);
        gridPane.setHgap(4);
        gridPane.setAlignment(Pos.CENTER);

        paneMatriz.getChildren().add(gridPane);

        gridPane.prefWidthProperty().bind(paneMatriz.widthProperty());
        gridPane.prefHeightProperty().bind(paneMatriz.heightProperty());
    }

    //Obtener el contenido de las celdas
    @FXML public void obtenerClave() {
        matriz = new int[gridPane.getRowCount()][gridPane.getColumnCount()];

        for (int i = 0; i < gridPane.getRowCount(); i++) {
            for (int j = 0; j < gridPane.getColumnCount(); j++) {
                Node node = gridPane.getChildren().get(i * gridPane.getColumnCount() + j);

                if (node instanceof TextField) {
                    TextField textField = (TextField) node;
                    String data = textField.getText();
                    matriz[i][j] = Integer.parseInt(data);
                }
            }
        }
        mensaje = mesaje.getText();
    }

    @FXML public void updateMatriz(ActionEvent event) {
        int numRows = gridPane.getColumnCount() + 1;
        int numCols = gridPane.getRowCount() + 1;

        if (gridPane.getColumnCount() < 5){

            gridPane.getChildren().clear(); // elimina todos los nodos del GridPane
            gridPane.getColumnConstraints().clear(); // elimina todas las columnas del GridPane
            gridPane.getRowConstraints().clear(); // elimina todas las filas del GridPane

            paneMatriz.getChildren().remove(gridPane);

            createGridPane(numRows, numCols);

            gridPane.getParent().layout();  //Actualiza la vista
        }
    }

    @FXML public void deleteRowsColumns(ActionEvent event){
        int numRows = gridPane.getColumnCount() - 1;
        int numCols = gridPane.getRowCount() - 1;

        if(gridPane.getColumnCount() > 2){
            gridPane.getChildren().clear(); // elimina todos los nodos del GridPane
            gridPane.getColumnConstraints().clear(); // elimina todas las columnas del GridPane
            gridPane.getRowConstraints().clear(); // elimina todas las filas del GridPane

            paneMatriz.getChildren().remove(gridPane);
            createGridPane(numRows, numCols);

            gridPane.getParent().layout();
        }
    }

    @FXML public void clearMatriz(ActionEvent event){
        int numRows = gridPane.getColumnCount();
        int numCols = gridPane.getRowCount();

        gridPane.getChildren().clear(); // elimina todos los nodos del GridPane
        gridPane.getColumnConstraints().clear(); // elimina todas las columnas del GridPane
        gridPane.getRowConstraints().clear(); // elimina todas las filas del GridPane

        paneMatriz.getChildren().remove(gridPane);
        createGridPane(numRows, numCols);

        gridPane.getParent().layout();
    }

    @FXML public void encriptarButton(ActionEvent event) throws IOException {
        cargarSigXML("encriptar");
    }

    @FXML public void desencriptarButton(ActionEvent event) throws IOException {
        cargarSigXML("desencriptar");
    }

    @FXML public void cargarSigXML(String operacion) throws IOException {
        obtenerClave();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resultado.fxml"));
        AnchorPane modal = null;
        ControladorResultado controlador2;
        Desencriptar desencriptar = new Desencriptar();
        Encriptar encriptar = new Encriptar();

        try {
            modal = loader.load();
            controlador2 = loader.getController();
            controlador2.llenarMatrizResultado(matriz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //desencriptar.inversa(desencriptar.matrizDouble(matriz)); //Calcula la validez de la inversa

        if (desencriptar.validez(matriz)){
            if(operacion.equals("encriptar")){
                controlador2.mostrarResultado(true, "encriptar", encriptar.encriptar(mensaje, matriz));
            } else {
                controlador2.mostrarResultado(true, "desencriptar", desencriptar.desencriptar(mensaje, matriz));
            }
            padre.getChildren().add(modal);

        } else {
            FXMLLoader loaderError = new FXMLLoader(getClass().getResource("ErrorMessage.fxml"));
            AnchorPane error = loaderError.load();
            ErrorMessage errorMessage = loaderError.getController();;
            padre.getChildren().add(error);

            System.out.println("No es posible realizar la operacion");
        }
    }

    public Node getSombra() {
        return sombra;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int[][] getMatriz() {
        return matriz;
    }

}

