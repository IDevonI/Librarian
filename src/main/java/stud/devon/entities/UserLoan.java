package stud.devon.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class UserLoan {
    private int idBook;
    private String title;
    private String author;
    private LocalDate loanDate;
    private LocalDate loanEnd;

    public UserLoan() {
    }

    public UserLoan(BigDecimal idBook, String title, String author, Timestamp loanDate, Timestamp loanEnd) {
        this.idBook = idBook.intValue();
        this.title = title;
        this.author = author;
        this.loanDate = loanDate.toLocalDateTime().toLocalDate();
        this.loanEnd = loanEnd.toLocalDateTime().toLocalDate();
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

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getLoanEnd() {
        return loanEnd;
    }

    public void setLoanEnd(LocalDate loanEnd) {
        this.loanEnd = loanEnd;
    }
}

