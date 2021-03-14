package stud.devon.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import stud.devon.App;
import stud.devon.entities.Book;
import stud.devon.entities.Loan;
import stud.devon.entities.User;
import stud.devon.service.DataBase;
import stud.devon.service.Gui;
import stud.devon.service.MailSender;
import stud.devon.service.Validator;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class MainMenuAdminController {

    @FXML
    private Label dateTime,aboutName,aboutSurname,aboutUserId,aboutUserType,aboutAddress,aboutEmail;
    @FXML
    private MenuButton userButton;
    @FXML
    private Text welcomeText;
    @FXML
    private HBox bookButtons,loanButtons,userButtons,bookSerialization,loanSerialization,userSerialization;
    @FXML
    private TextArea warningsField;

    @FXML
    private TableView<Book> booksTable;
    @FXML
    private TableColumn<Book,Integer> idColumn;
    @FXML
    private TableColumn<Book,String> titleColumn;
    @FXML
    private TableColumn<Book,String> authorColumn;
    @FXML
    private TableColumn<Book,String> publisherColumn;
    @FXML
    private TableColumn<Book,String> categoryColumn;
    @FXML
    private TableColumn<Book, Boolean> availableColumn;
    @FXML
    private TableColumn<Book,Boolean> reservedColumn;

    @FXML
    private TableView<Loan> loansTable;
    @FXML
    private TableColumn<Loan,Integer> bookIdColumn;
    @FXML
    private TableColumn<Loan,Integer> workerIdColumn;
    @FXML
    private TableColumn<Loan,Integer> clientIdColumn;
    @FXML
    private TableColumn<Loan,LocalDate> loanDateColumn;
    @FXML
    private TableColumn<Loan,LocalDate> loanEndDateColumn;
    @FXML
    private TableColumn<Loan,Integer> reservedByColumn;

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User,Integer> userIdColumn;
    @FXML
    private TableColumn<User,String> surnameColumn;
    @FXML
    private TableColumn<User,String> nameColumn;
    @FXML
    private TableColumn<User,String> addressColumn;
    @FXML
    private TableColumn<User,String> emailColumn;
    @FXML
    private TableColumn<User, LocalDate> joinDateColumn;
    @FXML
    private TableColumn<User,LocalDate> hireDateColumn;
    @FXML
    private TableColumn<User,Boolean> adminRoleColumn;

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
        aboutUserType.setText("Administrator");
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("idBook"));
        bookIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        workerIdColumn.setCellValueFactory(new PropertyValueFactory<>("idWorker"));
        workerIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        clientIdColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        loanDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        loanEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanEnd"));
        loanEndDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        reservedByColumn.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        reservedByColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idBook"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        publisherColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
        reservedColumn.setCellValueFactory(new PropertyValueFactory<>("reservation"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        joinDateColumn.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        adminRoleColumn.setCellValueFactory(new PropertyValueFactory<>("adminRole"));
        loansTable.setRowFactory(p->new TableRow<>() {
            @Override
            public void updateItem(Loan item, boolean empty) {
                super.updateItem(item,empty);
                if(!empty) {
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

    TextArea getWarningsField() {
        return warningsField;
    }

    @FXML
    private AnchorPane aboutPane;
    @FXML
    private void showAboutPane()
    {
        welcomeText.setVisible(false);
        booksTable.setVisible(false);
        loansTable.setVisible(false);
        usersTable.setVisible(false);
        aboutPane.setVisible(true);
    }

    @FXML
    private void showBooksTable()
    {
        bookSerialization.setVisible(true);
        loanSerialization.setVisible(false);
        userSerialization.setVisible(false);
        bookButtons.setVisible(true);
        userButtons.setVisible(false);
        loanButtons.setVisible(false);
        welcomeText.setVisible(false);
        loansTable.setVisible(false);
        usersTable.setVisible(false);
        aboutPane.setVisible(false);
        booksTable.setItems(DataBase.getBooksList());
        booksTable.setVisible(true);
    }

    @FXML
    private void showLoansTable()
    {
        bookSerialization.setVisible(false);
        loanSerialization.setVisible(true);
        userSerialization.setVisible(false);
        loanButtons.setVisible(true);
        userButtons.setVisible(false);
        bookButtons.setVisible(false);
        welcomeText.setVisible(false);
        booksTable.setVisible(false);
        usersTable.setVisible(false);
        aboutPane.setVisible(false);
        loansTable.setItems(DataBase.getLoansList());
        loansTable.setVisible(true);
    }

    @FXML
    private void showUsersTable()
    {
        bookSerialization.setVisible(false);
        loanSerialization.setVisible(false);
        userSerialization.setVisible(true);
        userButtons.setVisible(true);
        loanButtons.setVisible(false);
        bookButtons.setVisible(false);
        welcomeText.setVisible(false);
        loansTable.setVisible(false);
        booksTable.setVisible(false);
        aboutPane.setVisible(false);
        usersTable.setItems(DataBase.getUsersList());
        usersTable.setVisible(true);
    }
    @FXML
    private void editSurname(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
        String oldValue=userStringCellEditEvent.getOldValue();
        if(Validator.checkSurname(userStringCellEditEvent.getNewValue())) {
            User user = usersTable.getSelectionModel().getSelectedItem();
            user.setSurname(userStringCellEditEvent.getNewValue());
            if(DataBase.updateUser(user, "surname"))
            {
                usersTable.getSelectionModel().getSelectedItem().setSurname(oldValue);
                usersTable.refresh();
                warningsField.setText("Error while updating data base!");
            }
        }else
        {
            usersTable.getSelectionModel().getSelectedItem().setSurname(oldValue);
            usersTable.refresh();
            warningsField.setText("Not a valid surname!");
        }
    }
    @FXML
    private void editName(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
        String oldValue=userStringCellEditEvent.getOldValue();
        if(Validator.checkName(userStringCellEditEvent.getNewValue())) {
            User user = usersTable.getSelectionModel().getSelectedItem();
            user.setName(userStringCellEditEvent.getNewValue());
            if(DataBase.updateUser(user, "name"))
            {
                usersTable.getSelectionModel().getSelectedItem().setName(oldValue);
                usersTable.refresh();
                warningsField.setText("Error while updating data base!");
            }
        }
        else
        {
            usersTable.getSelectionModel().getSelectedItem().setName(oldValue);
            usersTable.refresh();
            warningsField.setText("Not a valid name!");
        }
    }
    @FXML
    private void editAddress(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
        String oldValue=userStringCellEditEvent.getOldValue();
        if(Validator.checkAddress(userStringCellEditEvent.getNewValue())) {
            User user = usersTable.getSelectionModel().getSelectedItem();
            user.setAddress(userStringCellEditEvent.getNewValue());
            if(DataBase.updateUser(user, "address"))
            {
                usersTable.getSelectionModel().getSelectedItem().setAddress(oldValue);
                usersTable.refresh();
                warningsField.setText("Error while updating data base!");
            }
        }
        else
        {
            usersTable.getSelectionModel().getSelectedItem().setAddress(oldValue);
            usersTable.refresh();
            warningsField.setText("Not a valid address!");
        }
    }
    @FXML
    private void editEmail(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
        String oldValue=userStringCellEditEvent.getOldValue();
        if(Validator.checkEmail(userStringCellEditEvent.getNewValue()))
        {
            User user=usersTable.getSelectionModel().getSelectedItem();
            user.setEmail(userStringCellEditEvent.getNewValue());
            if(DataBase.updateUser(user,"email"))
            {
                usersTable.getSelectionModel().getSelectedItem().setEmail(oldValue);
                usersTable.refresh();
                warningsField.setText("Account with this email already exist!");
            }else if(MailSender.sendMail(user.getEmail(),"Librarian: New email address","Your email address has been successfully changed!"))
            {
                usersTable.getSelectionModel().getSelectedItem().setEmail(oldValue);
                usersTable.refresh();
                user.setEmail(oldValue);
                DataBase.updateUser(user,"email");
                warningsField.setText("Given email doesn't exist!");
            }
        }
        else
        {
            usersTable.getSelectionModel().getSelectedItem().setEmail(oldValue);
            usersTable.refresh();
            warningsField.setText("Not a valid email!");
        }
    }
    @FXML
    private void editTitle(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
        String oldValue=bookStringCellEditEvent.getOldValue();
        if(Validator.checkTitle(bookStringCellEditEvent.getNewValue()))
        {
            Book book=booksTable.getSelectionModel().getSelectedItem();
            book.setTitle(bookStringCellEditEvent.getNewValue());
            if(DataBase.updateBook(book,"title"))
            {
                booksTable.getSelectionModel().getSelectedItem().setTitle(oldValue);
                booksTable.refresh();
                warningsField.setText("Error while updating data base!");

            }
        }else
        {
            booksTable.getSelectionModel().getSelectedItem().setTitle(oldValue);
            booksTable.refresh();
            warningsField.setText("Not a valid title!");
        }
    }
    @FXML
    private void editAuthor(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
        String oldValue=bookStringCellEditEvent.getOldValue();
        if(Validator.checkAuthor(bookStringCellEditEvent.getNewValue()))
        {
            Book book=booksTable.getSelectionModel().getSelectedItem();
            book.setAuthor(bookStringCellEditEvent.getNewValue());
            if(DataBase.updateBook(book,"author"))
            {
                booksTable.getSelectionModel().getSelectedItem().setAuthor(oldValue);
                booksTable.refresh();
                warningsField.setText("Error while updating data base!");
            }
        }else
        {
            booksTable.getSelectionModel().getSelectedItem().setAuthor(oldValue);
            booksTable.refresh();
            warningsField.setText("Not a valid author!");
        }
    }
    @FXML
    private void editPublisher(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
        String oldValue=bookStringCellEditEvent.getOldValue();
        if(Validator.checkPublisher(bookStringCellEditEvent.getNewValue()))
        {
            Book book=booksTable.getSelectionModel().getSelectedItem();
            book.setPublisher(bookStringCellEditEvent.getNewValue());
            if(DataBase.updateBook(book,"publisher"))
            {
                booksTable.getSelectionModel().getSelectedItem().setPublisher(oldValue);
                booksTable.refresh();
                warningsField.setText("Error while updating data base!");
            }
        }else
        {
            booksTable.getSelectionModel().getSelectedItem().setPublisher(oldValue);
            booksTable.refresh();
            warningsField.setText("Not a valid publisher!");
        }
    }
    @FXML
    private void editCategory(TableColumn.CellEditEvent<Book, String> bookStringCellEditEvent) {
        String oldValue=bookStringCellEditEvent.getOldValue();
        if(Validator.checkCategory(bookStringCellEditEvent.getNewValue()))
        {
            Book book=booksTable.getSelectionModel().getSelectedItem();
            book.setCategory(bookStringCellEditEvent.getNewValue());
            if(DataBase.updateBook(book,"category"))
            {
                booksTable.getSelectionModel().getSelectedItem().setCategory(oldValue);
                booksTable.refresh();
                warningsField.setText("Error while updating data base!");
            }
        }else
        {
            booksTable.getSelectionModel().getSelectedItem().setCategory(oldValue);
            booksTable.refresh();
            warningsField.setText("Not a valid category!");
        }
    }
    @FXML
    private void editBookId(TableColumn.CellEditEvent<Loan, Integer> loanIntegerCellEditEvent) {
        Integer oldValue=loanIntegerCellEditEvent.getOldValue();
        if(DataBase.callFunctionCheckBook(loanIntegerCellEditEvent.getNewValue())!=0)
        {
            Loan loan = loansTable.getSelectionModel().getSelectedItem();
            loan.setIdBook(loanIntegerCellEditEvent.getNewValue());
            DataBase.updateLoan(loan,"idBook");
        }else
        {
            loansTable.getSelectionModel().getSelectedItem().setIdWorker(oldValue);
            loansTable.refresh();
            warningsField.setText("Book with given ID doesn't exist or is already loaned!");
        }

    }
    @FXML
    private void editWorkerId(TableColumn.CellEditEvent<Loan, Integer> loanIntegerCellEditEvent) {
        Integer oldValue=loanIntegerCellEditEvent.getOldValue();
        if(DataBase.callFunctionCheckWorker(loanIntegerCellEditEvent.getNewValue())!=0)
        {
            Loan loan = loansTable.getSelectionModel().getSelectedItem();
            loan.setIdWorker(loanIntegerCellEditEvent.getNewValue());
            DataBase.updateLoan(loan,"idWorker");
        }else
        {
            loansTable.getSelectionModel().getSelectedItem().setIdWorker(oldValue);
            loansTable.refresh();
            warningsField.setText("Worker with given ID doesn't exist!");
        }

    }
    @FXML
    private void editClientId(TableColumn.CellEditEvent<Loan, Integer> loanIntegerCellEditEvent) {
        Integer oldValue=loanIntegerCellEditEvent.getOldValue();
        if(DataBase.callFunctionCheckUser(loanIntegerCellEditEvent.getNewValue())!=0)
        {
            Loan loan = loansTable.getSelectionModel().getSelectedItem();
            loan.setIdClient(loanIntegerCellEditEvent.getNewValue());
            DataBase.updateLoan(loan,"idClient");
        }else
        {
            loansTable.getSelectionModel().getSelectedItem().setIdClient(oldValue);
            loansTable.refresh();
            warningsField.setText("Client with given ID doesn't exist!");
        }
    }
    @FXML
    private void editLoanDate(TableColumn.CellEditEvent<Loan, LocalDate> loanDateCellEditEvent) {
        LocalDate oldValue=loanDateCellEditEvent.getOldValue();
        LocalDate current=LocalDate.now();
        if(loanDateCellEditEvent.getNewValue().compareTo(current)<=0)
        {
            Loan loan = loansTable.getSelectionModel().getSelectedItem();
            loan.setLoanDate(loanDateCellEditEvent.getNewValue());
            DataBase.updateLoan(loan,"loanDate");
        }else
        {
            loansTable.getSelectionModel().getSelectedItem().setLoanDate(oldValue);
            loansTable.refresh();
            warningsField.setText("Loan date must be older than current or equal!");
        }
    }
    @FXML
    private void editLoanEndDate(TableColumn.CellEditEvent<Loan, LocalDate> loanDateCellEditEvent) {
        LocalDate oldValue=loanDateCellEditEvent.getOldValue();
        LocalDate current=LocalDate.now();
        if(loanDateCellEditEvent.getNewValue().compareTo(current)>0)
        {
            Loan loan = loansTable.getSelectionModel().getSelectedItem();
            loan.setLoanEnd(loanDateCellEditEvent.getNewValue());
            DataBase.updateLoan(loan,"loanEnd");
        }else
        {
            loansTable.getSelectionModel().getSelectedItem().setLoanEnd(oldValue);
            loansTable.refresh();
            warningsField.setText("Loan end date must newer than current!");
        }
    }
    @FXML
    private void editReservedBy(TableColumn.CellEditEvent<Loan, Integer> loanIntegerCellEditEvent) {
        Integer oldValue=loanIntegerCellEditEvent.getOldValue();
        if(loanIntegerCellEditEvent.getNewValue()==0||loanIntegerCellEditEvent.getNewValue()==null)
        {
            Loan loan = loansTable.getSelectionModel().getSelectedItem();
            loan.setIdReservation(loanIntegerCellEditEvent.getNewValue());
            DataBase.updateLoan(loan,"reservedBy");
        }else
        {
            if(DataBase.callFunctionCheckUser(loanIntegerCellEditEvent.getNewValue())!=0)
            {
                Loan loan = loansTable.getSelectionModel().getSelectedItem();
                if(loanIntegerCellEditEvent.getNewValue()!=loan.getIdClient())
                {
                    loan.setIdReservation(loanIntegerCellEditEvent.getNewValue());
                    DataBase.updateLoan(loan,"reservedBy");
                }else
                {
                    loansTable.getSelectionModel().getSelectedItem().setIdReservation(oldValue);
                    loansTable.refresh();
                    warningsField.setText("ID of reservation must differ from client ID!");
                }
            }else
            {
                loansTable.getSelectionModel().getSelectedItem().setIdReservation(oldValue);
                loansTable.refresh();
                warningsField.setText("Client with given ID doesn't exist!");
            }
        }
    }
    @FXML
    private void addBook()
    {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(App.class.getResource("/stud/devon/fxml/addBookView.fxml"));
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
    }
    @FXML
    private void deleteBook()
    {
        try {
            DataBase.setHoldBook(booksTable.getSelectionModel().getSelectedItem());
            DataBase.deleteBook();
        }catch (Exception e)
        {
            warningsField.setText("Could not delete selected book!");
        }
        refreshBook();
    }
    @FXML
    void refreshBook()
    {
        booksTable.setItems(DataBase.getBooksList());
    }
    @FXML
    private void addLoan()
    {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(App.class.getResource("/stud/devon/fxml/addLoanView.fxml"));
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
    }
    @FXML
    private void deleteLoan()
    {
        try {
            DataBase.setHoldLoan(loansTable.getSelectionModel().getSelectedItem());
            DataBase.deleteLoan();
        }catch (Exception e)
        {
            warningsField.setText("Could not delete selected loan!");
        }
        refreshLoan();
    }
    @FXML
    void refreshLoan()
    {
        loansTable.setItems(DataBase.getLoansList());
    }
    
    @FXML
    private void sendReminder()
    {
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
        if(MailSender.sendMail(DataBase.findEmail(loansTable.getSelectionModel().getSelectedItem().getIdClient()),
                "Librarian: Reminder","Please remember that your loan of :\n"
                        +DataBase.findBookToString(loansTable.getSelectionModel().getSelectedItem().getIdBook())+"\nwill expire on "
                        +loansTable.getSelectionModel().getSelectedItem().getLoanEnd()+" !"))
        {
            controller.getOkButton().setVisible(true);
            controller.getMessage().setText("Couldn't send your email!");
        }else {
            controller.getOkButton().setVisible(true);
            controller.getMessage().setText("Remind email was send successfully!");
        }
    }
    
    @FXML
    private void addUser()
    {
        Stage stage=new Stage();
        FXMLLoader fxmlLoader=new FXMLLoader(App.class.getResource("/stud/devon/fxml/addUserView.fxml"));
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
    }
    @FXML
    private void deleteUser()
    {
        try {
            DataBase.setHoldUser(usersTable.getSelectionModel().getSelectedItem());
            if(!DataBase.getHoldUser().isAdminRole())
                DataBase.deleteUser();
            else
                warningsField.setText("You cannot delete an administrator account!");

        }catch (Exception e)
        {
            warningsField.setText("Could not delete selected user!");
        }
        refreshUser();
    }
    @FXML
    void refreshUser()
    {
        usersTable.setItems(DataBase.getUsersList());
    }

    @FXML
    private void backUpUsers(){
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream("usersBackUpFile.bin")))
        {
            outputStream.writeObject(new ArrayList<User>(DataBase.getUsersList()));
        } catch (IOException e) {
            e.printStackTrace();
            warningsField.setText("Making back up file for users failed!");
        }

    }
    @FXML
    private void backUpLoans(){
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream("loansBackUpFile.bin")))
        {
            outputStream.writeObject(new ArrayList<Loan>(DataBase.getLoansList()));
        } catch (IOException e) {
            e.printStackTrace();
            warningsField.setText("Making back up file for loans failed!");
        }

    }
    @FXML
    private void backUpBooks(){
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream("booksBackUpFile.bin")))
        {
            outputStream.writeObject(new ArrayList<Book>(DataBase.getBooksList()));
        } catch (IOException e) {
            e.printStackTrace();
            warningsField.setText("Making back up file for books failed!");
        }
    }
    @FXML
    private void loadBackUpUsers(){
        try(ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream("usersBackUpFile.bin")))
        {
            userButtons.setVisible(true);
            loanButtons.setVisible(false);
            bookButtons.setVisible(false);
            welcomeText.setVisible(false);
            loansTable.setVisible(false);
            booksTable.setVisible(false);
            aboutPane.setVisible(false);
            usersTable.setItems(FXCollections.observableArrayList((List<User>)inputStream.readObject()));
            usersTable.setVisible(true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            warningsField.setText("Loading back up file for users failed!");
        }

    }
    @FXML
    private void loadBackUpLoans(){
        try(ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream("loansBackUpFile.bin")))
        {
            loanButtons.setVisible(true);
            userButtons.setVisible(false);
            bookButtons.setVisible(false);
            welcomeText.setVisible(false);
            booksTable.setVisible(false);
            usersTable.setVisible(false);
            aboutPane.setVisible(false);
            loansTable.setItems(FXCollections.observableArrayList((List<Loan>)inputStream.readObject()));
            loansTable.setVisible(true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            warningsField.setText("Loading back up file for loans failed!");
        }

    }
    @FXML
    private void loadBackUpBooks(){
        try(ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream("booksBackUpFile.bin")))
        {
            bookButtons.setVisible(true);
            userButtons.setVisible(false);
            loanButtons.setVisible(false);
            welcomeText.setVisible(false);
            loansTable.setVisible(false);
            usersTable.setVisible(false);
            aboutPane.setVisible(false);
            booksTable.setItems(FXCollections.observableArrayList((List<Book>)inputStream.readObject()));
            booksTable.setVisible(true);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            warningsField.setText("Loading back up file for books failed!");
        }
    }
}
