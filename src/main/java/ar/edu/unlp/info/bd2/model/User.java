package ar.edu.unlp.info.bd2.model;

import org.bson.types.ObjectId;

import java.util.Date;

public class User {

    private ObjectId objectId;
    private String email;
    private String password;
    private String username;
    private String name;
    private Date dateOfBirth;

    public ObjectId getObjectId() {
        return objectId;
    }

    public String getUsername() {
        return null;
    }

    public String getEmail() {
        return null;
    }
}