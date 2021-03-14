package stud.devon.entities;

import stud.devon.Encryptor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "logging_info")
public class LoggingInfo {
    @Id
    @Column(name = "id_user")
    private int idUser;
    private String password;

    public LoggingInfo() {
    }

    public LoggingInfo(int idUser, String password) {
        this.idUser = idUser;
        this.password = Encryptor.encrypt(password);
    }

    public String getPassword() {
        return password;
    }
}

