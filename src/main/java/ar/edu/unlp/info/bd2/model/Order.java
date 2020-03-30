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
	//private Collection<Product> products; para guardar los productos con sus respectivas cantidades esta la clase productOrder
	private Collection<Order> orders; //ESTOY USANDO ESTA VARIABLE PARA TODAS LAS ORDENES, PERO COMO LAS TOMO TODAS???? ORDER REPRESENTA A SOLO UNA ORDEN.
	private Long id;//PREGUNTAR COMO SETEAR ESTE ID SI DE ESO SE ENCARGA SIEMPRE SQL

	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User user) {
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.client = user;
		this.state = "pending";
	}

    public Long getId() {
		return this.id;
    }

    public String getState(){
		return this.state;
	}

	public Collection<String> getStatus() {
		Collection<String> list = new LinkedList<String>();
		for (Order order : this.orders)
		{
			list.add(order.getState());
		}
		return list;
	}

	public Object getClient() {
		return this.client;
	}

	public Collection<Product> getProducts() {
		return this.products;
	}

	public User getDeliveryUser() {
		return this.client;
	}
	
	public Order addProduct (Long quantity, Product product ) {
		ProductOrder nuevo = new ProductOrder(quantity,product);
		this.products.add(nuevo);
		return this;
	}
	
	public Boolean canCancel() {
		if(this.state == "pending"){
			return true;
		}else {return false;}
	}
}
