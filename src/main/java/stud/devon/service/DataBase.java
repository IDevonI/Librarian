package stud.devon.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import stud.devon.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Iterator;

public class DataBase implements Runnable {

    private static boolean hibernateLoaded = false;
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static User loggedUser;
    private static LoggingInfo loggedPassword;
    private static User holdUser;
    private static Book holdBook;

    private static Loan holdLoan;
    private static LoggingInfo holdLoggingInfo;

    public static boolean isHibernateLoaded() {
        return hibernateLoaded;
    }

    @Override
    public  void run() {
        entityManagerFactory = Persistence.createEntityManagerFactory("dataBase");
        entityManager = entityManagerFactory.createEntityManager();
        hibernateLoaded=true;
    }

    public static String authMe(String id) {
        try {
            int idInt = Integer.parseInt(id);
            loggedUser = entityManager.find(User.class, idInt);
            loggedPassword = entityManager.find(LoggingInfo.class, idInt);
            if (loggedPassword != null) {
                return loggedPassword.getPassword();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean addUser(String surname, String name, String address, String email, String password) {
        try {
            entityManager.getTransaction().begin();
            holdUser = new User(surname, name, address, email, false);
            entityManager.persist(holdUser);
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            holdUser.setIdUser((Integer) entityManager.createQuery("SELECT idUser FROM User WHERE email LIKE :custEmail").setParameter("custEmail", email).getSingleResult());
            holdLoggingInfo = new LoggingInfo(holdUser.getIdUser(), password);
            entityManager.persist(holdLoggingInfo);
        } catch (Exception exception) {
            return true;
        } finally {
            entityManager.getTransaction().commit();
        }
        return false;
    }

    public static boolean deleteUser() {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(LoggingInfo.class, holdUser.getIdUser()));
            entityManager.getTransaction().commit();
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(User.class, holdUser.getIdUser()));
            holdUser = null;
            holdLoggingInfo = null;
        } catch (Exception e) {
            return true;
        } finally {
            entityManager.getTransaction().commit();
        }
        return false;
    }

    public static boolean updateUser(User user, String what) {
        try {
            entityManager.getTransaction().begin();
            holdUser = entityManager.find(User.class, user.getIdUser());
            if (what.equals("surname"))
                holdUser.setSurname(user.getSurname());
            else if (what.equals("name"))
                holdUser.setName(user.getName());
            else if (what.equals("address"))
                holdUser.setAddress(user.getAddress());
            else if (what.equals("email"))
                holdUser.setEmail(user.getEmail());
        } catch (Exception exception) {
            return true;
        } finally {
            entityManager.getTransaction().commit();
        }
        return false;
    }

    public static boolean addLoan(String bookId, String workerId, String clientId, LocalDate loanDate, LocalDate loanEnd) {
        try {
            entityManager.getTransaction().begin();
            holdLoan = new Loan(Integer.parseInt(bookId), Integer.parseInt(workerId), Integer.parseInt(clientId),
                    loanDate, loanEnd);
            entityManager.persist(holdLoan);
        } catch (Exception e) {
            return true;
        } finally {
            entityManager.getTransaction().commit();
        }
        return false;
    }

    public static boolean deleteLoan() {
        try {

            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Loan.class, holdLoan.getIdBook()));
            holdLoan = null;
        } catch (Exception e) {
            return true;
        } finally {
            entityManager.getTransaction().commit();
        }
        return false;
    }

    public static boolean updateLoan(Loan loan, String what) {
        try {
            entityManager.getTransaction().begin();
            holdLoan = entityManager.find(Loan.class, loan.getIdBook());
            if (what.equals("idBook"))
                holdLoan.setIdBook(loan.getIdBook());
            else if (what.equals("idWorker"))
                holdLoan.setIdWorker(loan.getIdWorker());
            else if (what.equals("idClient"))
                holdLoan.setIdClient(loan.getIdClient());
            else if (what.equals("loanDate"))
                holdLoan.setLoanDate(loan.getLoanDate());
            else if (what.equals("loanEnd"))
                holdLoan.setLoanEnd(loan.getLoanEnd());
            else if (what.equals("reservedBy"))
                holdLoan.setIdReservation(loan.getIdReservation());
        } catch (Exception exception) {
            return true;
        } finally {
            entityManager.getTransaction().commit();
        }
        return false;
    }

    public static boolean addBook(String title, String author, String publisher, String category) {
        try {
            entityManager.getTransaction().begin();
            holdBook = new Book(title, author, publisher, category);
            entityManager.persist(holdBook);
        } catch (Exception e) {
            return true;
        } finally {
            entityManager.getTransaction().commit();
        }
        return false;
    }

    public static boolean deleteBook() {
        try {

            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Book.class, holdBook.getIdBook()));
            holdLoan = null;
        } catch (Exception e) {
            return true;
        } finally {
            entityManager.getTransaction().commit();
        }
        return false;
    }

    public static boolean updateBook(Book book, String what) {
        try {
            entityManager.getTransaction().begin();
            holdBook = entityManager.find(Book.class, book.getIdBook());
            if (what.equals("title"))
                holdBook.setTitle(book.getTitle());
            else if (what.equals("author"))
                holdBook.setAuthor(book.getAuthor());
            else if (what.equals("publisher"))
                holdBook.setPublisher(book.getPublisher());
            else if (what.equals("category"))
                holdBook.setCategory(book.getCategory());
            else if (what.equals("available"))
                holdBook.setAvailable(book.isAvailable());
            else if (what.equals("reservation"))
                holdBook.setReservation(book.isReservation());
        } catch (Exception exception) {
            return true;
        } finally {
            entityManager.getTransaction().commit();
        }

        return false;
    }

    public static int callFunctionCheckWorker(Integer id) {
        try {
            BigDecimal bool = (BigDecimal) entityManager.createNativeQuery("SELECT CHECK_ID_WORKER(:IDW) FROM dual").setParameter("IDW", id).getSingleResult();
            return bool.intValue();
        }catch (Exception e)
        {
            return 0;
        }
    }

    public static int callFunctionCheckUser(Integer id) {
        try {
            BigDecimal bool = (BigDecimal) entityManager.createNativeQuery("SELECT CHECK_ID_USER(:IDU) FROM dual").setParameter("IDU", id).getSingleResult();
            return bool.intValue();
        }catch (Exception e)
        {
            return 0;
        }
    }

    public static int callFunctionCheckBook(Integer id) {
        try{
        BigDecimal bool = (BigDecimal) entityManager.createNativeQuery("SELECT CHECK_ID_BOOK(:IDB) FROM dual").setParameter("IDB", id).getSingleResult();
        return bool.intValue();
        }catch (Exception e)
        {
            return 0;
        }

    }

    public static User getHoldUser() {
        return holdUser;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static ObservableList<Loan> getLoansList() {
        try {
            Query query = entityManager.createQuery("SELECT p FROM Loan AS p");
            ObservableList<Loan> loanObservableList = FXCollections.observableArrayList(query.getResultList());
            return loanObservableList;
        }catch (Exception e)
        {
            return null;
        }
    }

    public static ObservableList<User> getUsersList() {
        try {
            Query query = entityManager.createQuery("SELECT p FROM User AS p");
            ObservableList<User> userObservableList = FXCollections.observableArrayList(query.getResultList());
            return userObservableList;
        }catch (Exception e)
        {
            return null;
        }
    }

    public static ObservableList<Book> getBooksList() {
        try {
            Query query = entityManager.createQuery("SELECT p FROM Book AS p");
            ObservableList<Book> bookObservableList = FXCollections.observableArrayList(query.getResultList());
            return bookObservableList;
        }catch (Exception e)
        {
            return null;
        }
    }

    public static ObservableList<Book> getUserBooksList() {
        try{
        Query query = entityManager.createQuery("SELECT DISTINCT b.title,b.author,b.publisher,b.category FROM Book b");
        ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
        Iterator<?> iterator = query.getResultList().iterator();
        while (iterator.hasNext()) {
            Object[] item = (Object[]) iterator.next();
            bookObservableList.add(new Book(0, (String) item[0], (String) item[1], (String) item[2], (String) item[3], true, false));
        }
        return bookObservableList;
        }catch (Exception e)
        {
            return null;
        }
    }

    public static ObservableList<Reservation> getReservationsList() {
        try {
            Query query = entityManager.createNativeQuery("SELECT b.id_book,b.title,b.author,l.loan_end FROM Books b JOIN Loans l ON b.id_book=l.id_book WHERE l.id_reservation=:ID");
            query.setParameter("ID", loggedUser.getIdUser());
            ObservableList<Reservation> reservationObservableList = FXCollections.observableArrayList();
            Iterator<?> iterator = query.getResultList().iterator();
            while (iterator.hasNext()) {
                Object[] item = (Object[]) iterator.next();
                reservationObservableList.add(new Reservation((BigDecimal) item[0], (String) item[1], (String) item[2], (Timestamp) item[3]));
            }
            return reservationObservableList;
        }catch (Exception e)
        {
            return null;
        }
    }

    public static ObservableList<UserLoan> getUserLoansList() {
        try {
            Query query = entityManager.createNativeQuery("SELECT b.id_book,b.title,b.author,l.loan_date,l.loan_end FROM Books b JOIN Loans l ON b.id_book=l.id_book WHERE l.id_client=:ID");
            query.setParameter("ID", loggedUser.getIdUser());
            ObservableList<UserLoan> loanObservableList = FXCollections.observableArrayList();
            Iterator<?> iterator = query.getResultList().iterator();
            while (iterator.hasNext()) {
                Object[] item = (Object[]) iterator.next();
                loanObservableList.add(new UserLoan((BigDecimal) item[0], (String) item[1], (String) item[2], (Timestamp) item[3], (Timestamp) item[4]));
            }
            return loanObservableList;
        } catch (Exception e)
        {
            return null;
        }
    }

    public static Long countAvailableBooks(Book book) {
        try {
            Query query = entityManager.createQuery("SELECT count(*) FROM Book WHERE title=:T AND author=:A AND publisher=:P AND available=1");
            query.setParameter("T", book.getTitle());
            query.setParameter("A", book.getAuthor());
            query.setParameter("P", book.getPublisher());
            Long count = (Long) query.getSingleResult();
            return count;
        }catch (Exception e)
        {
            return null;
        }
    }

    public static Long countAvailableReservationBooks(Book book) {
        try {
            Query query = entityManager.createQuery("SELECT count(*) FROM Book WHERE title=:T AND author=:A AND publisher=:P AND available=0 AND reservation=0");
            query.setParameter("T", book.getTitle());
            query.setParameter("A", book.getAuthor());
            query.setParameter("P", book.getPublisher());
            Long count = (Long) query.getSingleResult();
            return count;
        } catch (Exception e)
        {
            return null;
        }
    }

    public static boolean canReserve(String title) {
        try {
            for (UserLoan l : getUserLoansList()) {
                if (l.getTitle().equals(title)) {
                    return false;
                }
            }
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    public static boolean reserveBook(Book book) {
        try {
            Query query1 = entityManager.createQuery("SELECT idBook FROM Book WHERE title=:T AND author=:A AND publisher=:P AND available=0 AND reservation=0");
            query1.setParameter("T", book.getTitle());
            query1.setParameter("A", book.getAuthor());
            query1.setParameter("P", book.getPublisher());
            int id = (int) query1.getSingleResult();
            Query query = entityManager.createQuery("UPDATE Loan SET idReservation=:IDU WHERE id_book=:IDB");
            query.setParameter("IDU", loggedUser.getIdUser());
            query.setParameter("IDB", id);
            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return false;
        }catch (Exception e)
        {
            return true;
        }
    }

    public static boolean deleteReservation(Reservation reservation) {
        try {
            entityManager.getTransaction().begin();
            holdLoan = entityManager.find(Loan.class, reservation.getIdBook());
            holdLoan.setIdReservation(0);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            return true;
        }
        return false;
    }

    public static String findEmail(int id)
    {
        try
        {
            holdUser=entityManager.find(User.class,id);
            return holdUser.getEmail();
        }catch (Exception e)
        {
            return null;
        }
    }

    public static String findBookToString(int id)
    {
        try
        {
            holdBook=entityManager.find(Book.class,id);
            return holdBook.toString();
        }catch (Exception e)
        {
            return null;
        }
    }

    public static void setHoldLoan(Loan holdLoan) {
        DataBase.holdLoan = holdLoan;
    }

    public static void setHoldBook(Book holdBook) {
        DataBase.holdBook = holdBook;
    }

    public static void setHoldUser(User holdUser) {
        DataBase.holdUser = holdUser;
    }
}
