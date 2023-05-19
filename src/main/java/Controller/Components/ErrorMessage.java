package Controller.Components;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorMessage implements Initializable {
    @FXML
    AnchorPane error;
    @FXML
    Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error.setVisible(false);
        animation();
    }

    public void setMessage(String message) {
        errorLabel.setText(message);
    }

    public void animation(){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), error);
        translateTransition.setFromX(1100);
        translateTransition.setToX(800);
        translateTransition.setOnFinished(event -> {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), error);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
        });
        error.setVisible(true);
        translateTransition.play();
    }

}
