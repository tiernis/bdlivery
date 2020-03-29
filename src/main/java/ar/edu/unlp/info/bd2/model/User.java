package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class User {
	private Long id;//PREGUNTAR COMO SETEAR ESTE ID SI DE ESO SE ENCARGA SIEMPRE SQL
	private String email;
	private String password;
	private String username;
	private String name;
	private Date dateOfBirth;
	
	public User(String email, String password, String name, String username, Date dateOfBirth)
	{
		this.email = email;
		this.password = password;
		this.username = username;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public Long getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}
}
