package ar.edu.unlp.info.bd2.model;

import java.util.*;

public class Order {
	private Date dateOfOrder;
	private String address;
	private Float coordX;
	private Float coordY;
	private String state;
	private User client;
	private ArrayList<ProductOrder> products;
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User user) {
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.user = user;
		this.state="pendiente";
	}
	
	
}
