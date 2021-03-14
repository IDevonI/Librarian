package stud.devon.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stud.devon.service.DataBase;
import stud.devon.service.Gui;
import stud.devon.service.Validator;

public class AddBookController {

    @FXML
    private TextField titleField, authorField, publisherField,categoryField;
    @FXML
    private Label invalidTitle, invalidAuthor, invalidPublisher, invalidCategory;

    @FXML
    private void cancel() {
        try {
            Stage stage = (Stage) titleField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void confirm() {
        boolean dataIsValid = true;
        if (!Validator.checkTitle(titleField.getText())) {
            invalidTitle.setText("Invalid book title!");
            dataIsValid = false;
        } else {
            invalidTitle.setText("");
        }
        if (!Validator.checkAuthor(authorField.getText())) {
            System.out.println(authorField.getText());
            invalidAuthor.setText("Invalid book author!");
            dataIsValid = false;
        } else {
            invalidAuthor.setText("");
        }
        if (!Validator.checkPublisher(publisherField.getText())) {
            invalidPublisher.setText("Invalid book publisher!");
            dataIsValid = false;
        } else {
            invalidPublisher.setText("");
        }
        if (!Validator.checkCategory(categoryField.getText())) {
            invalidCategory.setText("Invalid book category!");
            dataIsValid = false;
        } else {
            invalidCategory.setText("");
        }
        if (dataIsValid) {
            onDataValid();
        }
    }

    private void onDataValid() {
        MainMenuAdminController controller=Gui.getFxmlLoader().getController();
        if(DataBase.addBook(titleField.getText(),authorField.getText(),publisherField.getText(),categoryField.getText()))
        {
            controller.getWarningsField().setText("Error while adding new book!");
        }
        controller.refreshBook();
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}

