package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;
import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

//@BsonDiscriminator
public class User implements PersistentObject {

    @BsonId
    private ObjectId objectId;
    //@BsonProperty(value = "email")
    private String email;
    //@BsonProperty(value = "password")
    private String password;
    //@BsonProperty(value = "username")
    private String username;
    //@BsonProperty(value = "name")
    private String name;
    //@BsonProperty(value = "dateOfBirth")
    private Date dateOfBirth;

    public User(){}

    //@BsonCreator
    public User(String email, String password, String username, String name, Date dateOfBirth) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUsername(username);
        this.setName(name);
        this.setDateOfBirth(dateOfBirth);
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}