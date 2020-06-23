package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique=true, nullable = false)
    private Long id;
    @Column(name ="email")
    private String email;
    @Column(name ="password")
    private String password;
    @Column(name ="username")
    private String username;
    @Column(name ="name_user")
    private String name;
    @Column(name ="date_of_birth")
    private Date dateOfBirth;

    public User(){}

    public User(String email, String password, String name, String username, Date dateOfBirth)
    {
        this.setEmail(email);
        this.setPassword(password);
        this.setUsername(username);
        this.setName(name);
        this.setDateOfBirth(dateOfBirth);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}