package stud.devon.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_generator")
    @SequenceGenerator(name = "c_generator",sequenceName = "USER_SEQUENCE",allocationSize = 1)
    private int idUser;
    private String surname;
    private String name;
    private String address;
    private String email;
    @Column(name = "join_date")
    private LocalDate joinDate;
    @Column(name = "hire_date")
    private LocalDate hireDate;
    @Column(name = "admin_role")
    private boolean adminRole;

    public User() {
    }

    public User(String surname, String name, String address, String email, boolean adminRole) {
        this.surname = surname;
        this.name = name;
        this.address = address;
        this.email = email;
        if(adminRole) {
            this.joinDate = null;
            this.hireDate = LocalDate.now();
        }else
        {
            this.joinDate = LocalDate.now();
            this.hireDate = null;
        }
        this.adminRole = adminRole;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idClient) {
        this.idUser = idClient;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdminRole() {
        return adminRole;
    }
}
