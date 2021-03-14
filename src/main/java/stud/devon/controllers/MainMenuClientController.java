package stud.devon.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import stud.devon.App;
import stud.devon.entities.Book;
import stud.devon.entities.Reservation;
import stud.devon.entities.UserLoan;
import stud.devon.service.DataBase;
import stud.devon.service.Gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainMenuClientController {

    @FXML
    private Label dateTime,aboutName,aboutSurname,aboutUserId,aboutUserType,aboutAddress,aboutEmail;
    @FXML
    private MenuButton userButton;
    @FXML
    private Text welcomeText;
    @FXML
    private HBox bookButtons,loanButtons, reservationButtons;
    @FXML
    private TextArea warningsField;

    @FXML
    private TableView<Book> booksTable;
    @FXML
    private TableColumn<Book,String> titleColumn;
    @FXML
    private TableColumn<Book,String> authorColumn;
    @FXML
    private TableColumn<Book,String> publisherColumn;
    @FXML
    private TableColumn<Book,String> categoryColumn;

    @FXML
    private TableView<UserLoan> loansTable;
    @FXML
    private TableColumn<UserLoan,Integer> bookIdColumn;
    @FXML
    private TableColumn<UserLoan,String> titleLColumn;
    @FXML
    private TableColumn<UserLoan,String> authorLColumn;
    @FXML
    private TableColumn<UserLoan,LocalDate> loanDateColumn;
    @FXML
    private TableColumn<UserLoan,LocalDate> loanEndDateColumn;
    

    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TableColumn<Reservation,Integer> bookIdRColumn;
    @FXML
    private TableColumn<Reservation,String> titleRColumn;
    @FXML
    private TableColumn<Reservation,String> authorRColumn;
    @FXML
    private TableColumn<Reservation,LocalDate> expectedLoanColumn;

    public void initialize() {
        Gui.getStage().close();
        Stage stage= new Stage();
        Gui.getScene().getStylesheets().add(String.valueOf(App.class.getResource("/stud/devon/css/menubar.css")));
        stage.setScene(Gui.getScene());
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/stud/devon/images/icon.png")));
        stage.setTitle("Librarian");
        stage.initStyle(StageStyle.DECORATED);
        Gui.setStage(stage);
        Gui.getStage().setHeight(600);
        Gui.getStage().setWidth(800);
        Gui.getStage().setResizable(false);
        Gui.getStage().show();
        initClock();
        welcomeText.setText("Welcome "+DataBase.getLoggedUser().getName()+" !");
        userButton.setText(DataBase.getLoggedUser().getName());
        aboutName.setText(DataBase.getLoggedUser().getName());
        aboutSurname.setText(DataBase.getLoggedUser().getSurname());
        aboutUserId.setText(Integer.toString(DataBase.getLoggedUser().getIdUser()));
        aboutAddress.setText(DataBase.getLoggedUser().getAddress());
        aboutEmail.setText(DataBase.getLoggedUser().getEmail());
        aboutUserType.setText("Client");
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("idBook"));
        titleLColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorLColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        loanEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanEnd"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        bookIdRColumn.setCellValueFactory(new PropertyValueFactory<>("idBook"));
        titleRColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorRColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        expectedLoanColumn.setCellValueFactory(new PropertyValueFactory<>("expectedLoanDate"));
        loansTable.setRowFactory(p-> new TableRow<>() {
            @Override
            public void updateItem(UserLoan item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty)
                {
                    long days = DAYS.between(LocalDate.now(), item.getLoanEnd());
                    if (days <= 3 && days > 0) {
                        setStyle("-fx-background-color: orange;");
                    } else if (days <=0) {
                        setStyle("-fx-background-color: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
        reservationsTable.setRowFactory(p->new TableRow<>() {
            @Override
            public void updateItem(Reservation item,boolean empty) {
                super.updateItem(item,empty);
                if(!empty) {
                    long days = DAYS.between(LocalDate.now(), item.getExpectedLoanDate());
                    if (days <= 3 && days > 0) {
                        setStyle("-fx-background-color: orange;");
                    } else if (days <=0) {
                        setStyle("-fx-background-color: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });
    }

    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    @FXML
    private void logout() {
        try {
            Gui.setRoot("loginMenuView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearLog()
    {
        warningsField.setText("");
    }


    @FXML
    private AnchorPane aboutPane;
    @FXML
    private void showAboutPane()
    {
        welcomeText.setVisible(false);
        booksTable.setVisible(false);
        loansTable.setVisible(false);
        reservationsTable.setVisible(false);
        aboutPane.setVisible(true);
    }

    @FXML
    private void showBooksTable()
    {
        bookButtons.setVisible(true);
        reservationButtons.setVisible(false);
        loanButtons.setVisible(false);
        welcomeText.setVisible(false);
        loansTable.setVisible(false);
        reservationsTable.setVisible(false);
        aboutPane.setVisible(false);
        booksTable.setItems(DataBase.getUserBooksList());
        booksTable.setVisible(true);
    }

    @FXML
    private void showLoansTable()
    {
        loanButtons.setVisible(true);
        reservationButtons.setVisible(false);
        bookButtons.setVisible(false);
        welcomeText.setVisible(false);
        booksTable.setVisible(false);
        reservationsTable.setVisible(false);
        aboutPane.setVisible(false);
        loansTable.setItems(DataBase.getUserLoansList());
        loansTable.setVisible(true);
    }

    @FXML
    private void showReservationsTable()
    {
        reservationButtons.setVisible(true);
        loanButtons.setVisible(false);
        bookButtons.setVisible(false);
        welcomeText.setVisible(false);
        loansTable.setVisible(false);
        booksTable.setVisible(false);
        aboutPane.setVisible(false);
        reservationsTable.setItems(DataBase.getReservationsList());
        reservationsTable.setVisible(true);
    }

    @FXML
    private void refreshBook()
    {
        booksTable.setItems(DataBase.getUserBooksList());
    }
    @FXML
    private void infoBook() {
        if(booksTable.getSelectionModel().getSelectedItem()!=null) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/stud/devon/fxml/infoBoxView.fxml"));
            Scene scene = null;
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
            InfoBoxController controller = fxmlLoader.getController();
            controller.getMessage().setText("Available examples: " +
                    DataBase.countAvailableBooks(booksTable.getSelectionModel().getSelectedItem()) + "\nAvailable for reservation: "
                    + DataBase.countAvailableReservationBooks(booksTable.getSelectionModel().getSelectedItem()));
        }else
            warningsField.setText("First select a book!");

    }

    @FXML
    private void reserveBook() {
        if(booksTable.getSelectionModel().getSelectedItem()!=null)
            if(DataBase.canReserve(booksTable.getSelectionModel().getSelectedItem().getTitle()))
                if(DataBase.countAvailableReservationBooks(booksTable.getSelectionModel().getSelectedItem())>0)
                {
                    if(DataBase.reserveBook(booksTable.getSelectionModel().getSelectedItem()))
                        warningsField.setText("Could not reserve this book!");
                    else
                        refreshReservation();
                }else
                {
                    warningsField.setText("No books available for reservation!");
                }
            else
                warningsField.setText("This book is in your loans!");
        else
            warningsField.setText("First select a book!");
    }

    @FXML
    private void refreshLoan()
    {
        loansTable.setItems(DataBase.getUserLoansList());
    }
    @FXML
    private void deleteReservation()
    {
        if(reservationsTable.getSelectionModel().getSelectedItem()!=null)
        {
            if(DataBase.deleteReservation(reservationsTable.getSelectionModel().getSelectedItem()))
                warningsField.setText("Could not delete reservation!");
            else
                refreshReservation();
        }else
            warningsField.setText("First select a reservation!");
    }
    @FXML
    private void refreshReservation() { reservationsTable.setItems(DataBase.getReservationsList()); }

}
