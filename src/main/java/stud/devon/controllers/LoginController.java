package stud.devon.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stud.devon.App;
import stud.devon.Encryptor;
import stud.devon.service.DataBase;
import stud.devon.service.Gui;

import java.io.IOException;


public class LoginController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;
    @FXML
    private Label badAuth;
    @FXML
    private AnchorPane backPane;

    public void initialize()
    {
        Gui.getStage().close();
        Stage stage= new Stage();
        stage.setScene(Gui.getScene());
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/stud/devon/images/icon.png")));
        stage.setTitle("Librarian");
        stage.initStyle(StageStyle.UNDECORATED);
        Gui.setStage(stage);
        Gui.getStage().setHeight(200);
        Gui.getStage().setWidth(500);
        Gui.getStage().show();
    }

    @FXML
    private Button exitButton;

    @FXML
    private void signUp() {
        try {
            Gui.setRoot("signUpView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logIn() {
        if(Encryptor.encrypt(passField.getText()).equals(DataBase.authMe(loginField.getText()))) {
            try {
                if(DataBase.getLoggedUser().isAdminRole())
                    Gui.setRoot("mainMenuAdminView");
                else
                    Gui.setRoot("mainMenuClientView");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
                badAuth();
        }
    }

    @FXML
    private void closeApp() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void badAuth()
    {
        badAuth.setText("# Bad login or password! #");
        backPane.setStyle("-fx-background-color: red");
    }
    @FXML
    private void resetAuthKey(KeyEvent keyEvent)
    {
        if(!keyEvent.getCode().equals(KeyCode.ENTER))
        {
            resetAuth();
        }
    }

    @FXML
    private void resetAuth()
    {
        badAuth.setText("");
        backPane.setStyle("-fx-background-color: #9c5a0b");
    }
}