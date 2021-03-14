package stud.devon.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import stud.devon.App;
import stud.devon.service.DataBase;
import stud.devon.service.Gui;
import stud.devon.service.MailSender;
import stud.devon.service.Validator;

import java.io.IOException;

public class SignUpController {

    @FXML
    private TextField nameField, surnameField, emailField, addressField;
    @FXML
    private PasswordField passwordField, confirmPassField;
    @FXML
    private Label invalidPassword, invalidConfirmation, invalidName, invalidSurname, invalidEmail, invalidAddress;

    public void initialize() {
        Gui.getStage().close();
        Stage stage = new Stage();
        stage.setScene(Gui.getScene());
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/stud/devon/images/icon.png")));
        stage.setTitle("Librarian");
        stage.initStyle(StageStyle.UNDECORATED);
        Gui.setStage(stage);
        Gui.getStage().setHeight(400);
        Gui.getStage().setWidth(500);
        Gui.getStage().show();
    }

    @FXML
    private void cancel() {
        try {
            Gui.setRoot("loginMenuView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void confirm() {
        boolean dataIsValid = true;
        if (!Validator.checkName(nameField.getText())) {
            invalidName.setText("Invalid name!");
            dataIsValid = false;
        } else {
            invalidName.setText("");
        }
        if (!Validator.checkSurname(surnameField.getText())) {
            invalidSurname.setText("Invalid surname!");
            dataIsValid = false;
        } else {
            invalidSurname.setText("");
        }
        if (!Validator.checkEmail(emailField.getText())) {
            invalidEmail.setText("Invalid email address!");
            dataIsValid = false;
        } else {
            invalidEmail.setText("");
        }
        if (!Validator.checkAddress(addressField.getText())) {
            invalidAddress.setText("Invalid address!");
            dataIsValid = false;
        } else {
            invalidAddress.setText("");
        }
        if (!Validator.checkPasswordEquality(passwordField.getText(), confirmPassField.getText())) {
            invalidConfirmation.setText("Password differs from confirmation!");
            dataIsValid = false;
        } else {
            invalidConfirmation.setText("");
        }
        if (!Validator.checkPasswordLength(passwordField.getText())) {
            invalidPassword.setText("Password must be exactly 8 characters long!");
            dataIsValid = false;
        } else {
            invalidPassword.setText("");
        }
        if (dataIsValid) {
            onDataValid();
        }
    }

    private void onDataValid() {
        try {
            Stage stage=new Stage();
            FXMLLoader fxmlLoader=new FXMLLoader(App.class.getResource("/stud/devon/fxml/infoBoxView.fxml"));
            Scene scene= null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setScene(scene);
            stage.getIcons().add(new Image(App.class.getResourceAsStream("/stud/devon/images/icon.png")));
            stage.setTitle("Librarian");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            InfoBoxController controller=fxmlLoader.getController();
            controller.getMessage().setText("Please wait...");
            controller.getOkButton().setVisible(false);
            if(DataBase.addUser(surnameField.getText(), nameField.getText(), addressField.getText(), emailField.getText(), passwordField.getText()))
                throw new Exception("addUser");
            if(MailSender.sendMail(emailField.getText(), "Librarian: Account created", "Your account on Librarian was created successfully!Your login ID is :"+DataBase.getHoldUser().getIdUser()))
                throw new Exception("sendMail");
            controller.getOkButton().setVisible(true);
            controller.getMessage().setText("Account created successfully! A confirmation message was sent on given email address.Your id: "+DataBase.getHoldUser().getIdUser());
        } catch (Exception e) {
            if(e.getMessage().equals("addUser")) {
                invalidEmail.setText("Account with given email address already exists!");
            }else
            {
                DataBase.deleteUser();
                invalidEmail.setText("Given email address doesn't exist!");
            }
        }
    }
}
