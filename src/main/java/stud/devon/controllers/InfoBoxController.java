package stud.devon.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InfoBoxController {


    @FXML
    private Label message;
    @FXML
    private Button okButton;

    public Label getMessage() {
        return message;
    }

    private void setMessage(Label message) {
        this.message = message;
    }

    public Button getOkButton() {
        return okButton;
    }

    private void setOkButton(Button okButton) {
        this.okButton = okButton;
    }

    @FXML
    private void okAction()
    {
        Stage stage= (Stage) message.getScene().getWindow();
        stage.close();
    }
}
