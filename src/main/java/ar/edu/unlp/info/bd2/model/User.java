package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class User {
	private String email;
	private String password;
	private String username;
	private String name;
	private Date dateOfBirth;
	
	public User createUser(String email, String password, String name, String username, Date dateOfBirth)
	{
		this.email = email;
		this.password = password;
		this.username = username;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public User getUserById(Long id)
	{
		try
		{
			//Efectuar operacion de busqueda en arraylist
		}
		catch(DBliveryException dbex)
		{
			System.out.println(dbex.getMessage());
		}
	}

	public User getUserByEmail(String email)
	{
		try
		{
			//Efectuar operacion de busqueda en arraylist
		}
		catch(DBliveryException dbex)
		{
			System.out.println(dbex.getMessage());
		}
	}
}
