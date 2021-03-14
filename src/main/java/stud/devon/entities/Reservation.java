package stud.devon.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Reservation {
        private int idBook;
        private String title;
        private String author;
        private LocalDate expectedLoanDate;


    public Reservation() {
    }

    public Reservation(BigDecimal idBook, String title, String author, Timestamp expectedLoanDate) {
        this.idBook = idBook.intValue();
        this.title = title;
        this.author = author;
        this.expectedLoanDate = expectedLoanDate.toLocalDateTime().toLocalDate();
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getExpectedLoanDate() {
        return expectedLoanDate;
    }

    public void setExpectedLoanDate(LocalDate expectedLoanDate) {
        this.expectedLoanDate = expectedLoanDate;
    }
}
