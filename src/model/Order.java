package model;

import java.util.*;

public class Order {
	private Date date;
	private String address;
	private Float cordx;
	private Float cordy;
	private String state;
	private User user;
	private ArrayList<ProductOrder> products;
	
	public Order(Date date, String address, Float cordx, Float cordy, User user) {
		this.date = date;
		this.address = address;
		this.cordx = cordx;
		this.cordy = cordy;
		this.user = user;
		this.state="pendiente";
	}
	
	
}
