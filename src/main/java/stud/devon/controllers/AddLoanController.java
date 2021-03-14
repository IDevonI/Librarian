package stud.devon.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stud.devon.service.DataBase;
import stud.devon.service.Gui;
import stud.devon.service.Validator;

import java.time.LocalDate;

public class AddLoanController {

    @FXML
    private TextField bookIdField, workerIdField, clientIdField;
    @FXML
    private DatePicker loanDatePicker,loanEndPicker;
    @FXML
    private Label invalidBook, invalidWorker, invalidClient, invalidLoanDate, invalidLoanEnd;

    public void initialize()
    {
        workerIdField.setText(String.valueOf(DataBase.getLoggedUser().getIdUser()));
        loanDatePicker.setValue(LocalDate.now());
        loanEndPicker.setValue(LocalDate.now().plusDays(1));
        loanDatePicker.setDayCellFactory(d -> new DateCell(){
            @Override
            public void updateItem(LocalDate item,boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(loanEndPicker.getValue())||item.isBefore(LocalDate.of(2000,1,1)));
            }
        });
        loanEndPicker.setDayCellFactory(d -> new DateCell(){
            @Override
            public void updateItem(LocalDate item,boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(loanDatePicker.getValue()));
            }
        });
    }

    @FXML
    private void cancel() {
        try {
            Stage stage = (Stage) bookIdField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void confirm() {
        boolean dataIsValid = true;
        if (!Validator.checkBookId(bookIdField.getText())) {
            invalidBook.setText("Invalid book ID!");
            dataIsValid = false;
        } else {
            invalidBook.setText("");
        }
        if (!Validator.checkWorkerId(workerIdField.getText())) {
            invalidWorker.setText("Invalid worker ID!");
            dataIsValid = false;
        } else {
            invalidWorker.setText("");
        }
        if (!Validator.checkClientId(clientIdField.getText())) {
            invalidClient.setText("Invalid client ID!");
            dataIsValid = false;
        } else {
            invalidClient.setText("");
        }
        if (dataIsValid) {
            onDataValid();
        }
    }

    private void onDataValid() {
        MainMenuAdminController controller=Gui.getFxmlLoader().getController();
        if(DataBase.addLoan(bookIdField.getText(),workerIdField.getText(),clientIdField.getText(),loanDatePicker.getValue(),loanEndPicker.getValue()))
        {
            controller.getWarningsField().setText("Error while adding new loan!");
        }
        controller.refreshLoan();
        Stage stage = (Stage) bookIdField.getScene().getWindow();
        stage.close();
    }
}
