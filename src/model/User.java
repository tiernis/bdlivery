package model;

import java.util.Date;

public class User {
	private String email;
	private String pass;
	private String name;
	private String username;
	private Date dateOfBirth;
	
	public User User(String email, String pass, String name, String username, Date dateOfBirth) {
		this.email = email;
		this.pass = pass;
		this.name = name;
		this.username = username;
		this.dateOfBirth = dateOfBirth;
		return this;
	}
	
	
}
