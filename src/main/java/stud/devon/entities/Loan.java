package stud.devon.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan implements Serializable {
    @Id
    @Column(name = "id_book")
    private int idBook;
    @Column(name = "id_worker")
    private int idWorker;
    @Column(name = "id_client")
    private int idClient;
    @Column(name = "loan_date")
    private LocalDate loanDate;
    @Column(name = "loan_end")
    private LocalDate loanEnd;
    @Column(name = "id_reservation")
    private int idReservation;

    public Loan() {
    }

    public Loan(int idBook, int idWorker, int idClient,LocalDate loanDate,LocalDate loanEnd) {
        this.idBook = idBook;
        this.idWorker = idWorker;
        this.idClient = idClient;
        this.loanDate = loanDate;
        this.loanEnd  = loanEnd;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(int idWorker) {
        this.idWorker = idWorker;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "idBook=" + idBook +
                ", idWorker=" + idWorker +
                ", idClient=" + idClient +
                ", loanDate=" + loanDate +
                ", loanEnd=" + loanEnd +
                ", idReservation=" + idReservation +
                '}';
    }
}
