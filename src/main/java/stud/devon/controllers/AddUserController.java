package stud.devon.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stud.devon.service.DataBase;
import stud.devon.service.Gui;
import stud.devon.service.MailSender;
import stud.devon.service.Validator;

public class AddUserController {

    @FXML
    private TextField nameField, surnameField, emailField, addressField;
    @FXML
    private PasswordField passwordField, confirmPassField;
    @FXML
    private Label invalidPassword, invalidConfirmation, invalidName, invalidSurname, invalidEmail, invalidAddress;

    @FXML
    private void cancel() {
        try {
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
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
            if(DataBase.addUser(surnameField.getText(), nameField.getText(), addressField.getText(), emailField.getText(), passwordField.getText()))
                throw new Exception("addUser");
            if(MailSender.sendMail(emailField.getText(), "Librarian: Account created", "Your account on Librarian was created successfully!Your login ID is :"+DataBase.getHoldUser().getIdUser()))
                throw new Exception("sendMail");
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            if(e.getMessage().equals("addUser")) {
                invalidEmail.setText("Account with given email address already exists!");
            }else
            {
                DataBase.deleteUser();
                invalidEmail.setText("Given email address doesn't exist!");
            }
        }
        MainMenuAdminController controller= Gui.getFxmlLoader().getController();
        controller.refreshUser();
    }
}
