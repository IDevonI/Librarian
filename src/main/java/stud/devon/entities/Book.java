package stud.devon.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
public class Book implements Serializable {
    @Id
    @Column(name = "id_book")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "b_generator")
    @SequenceGenerator(name = "b_generator",sequenceName = "BOOK_SEQUENCE", allocationSize = 1)
    private int idBook;
    private String title;
    private String author;
    private String publisher;
    private String category;
    private boolean available;
    private boolean reservation;

    public Book() {
    }

    public Book(String title, String author, String publisher, String category) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.available=true;
        this.reservation=false;
    }

    @Override
    public String toString() {
        return "Book{" +
                "idBook=" + idBook +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", category='" + category +
                ". available="+available+" }";
    }

    public Book(int idBook, String title, String author, String publisher, String category, boolean available, boolean reservation) {
        this.idBook = idBook;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.available = available;
        this.reservation = reservation;
    }

    public int getIdBook() {
        return idBook;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isReservation() {
        return reservation;
    }

    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }
}
