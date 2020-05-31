package ar.edu.unlp.info.bd2.model;

import org.bson.types.ObjectId;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Order {
	@BsonId
	private ObjectId objectId;
	private String address;
	private Float coordX;
	private Float coordY;
	private User client;
	private User deliveryUser;
	private List<OrderStatus> status = new ArrayList<>();
	private List<ProductOrder> products = new ArrayList<>();
	private Float cost;
	
	public Order(){
	
	}
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		OrderStatus newStatus= new OrderStatus("Pending", dateOfOrder);
		status.add(newStatus);
		this.setCost(0F);
	}

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getCoordX() {
		return coordX;
	}

	public void setCoordX(Float coordX) {
		this.coordX = coordX;
	}

	public Float getCoordY() {
		return coordY;
	}

	public void setCoordY(Float coordY) {
		this.coordY = coordY;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}
	
	public User getDeliveryUser() {
		return deliveryUser;
	}

	public void setDeliveryUser(User delivery) {
		this.deliveryUser = delivery;
	}

	public List<OrderStatus> getStatus() {
		return status;
	}

	public List<ProductOrder> getProducts() {
		return products;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

	public ObjectId getObjectId() {
		return objectId;
	}  
	
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
	}

	public void setStatus(List<OrderStatus> status) {
		this.status = status;
	}

	public void setProducts(List<ProductOrder> products) {
		this.products = products;
	}

	public Order addProduct(Long quantity, Product product) {
		ProductOrder newProduct= new ProductOrder(quantity,product);
		this.products.add(newProduct);
		Float newProdCost= this.getCost()+(product.getPrice()*quantity);
		this.setCost(newProdCost);
		return this;
	}
    
}
