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
	private ObjectId delivery;
	private List<OrderStatus> status = new ArrayList<>();
	private List<ObjectId> products = new ArrayList<>();
	private Float cost;
	
	public Order(){
	
	}
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		this.address=address;
		this.coordX=coordX;
		this.coordY=coordY;
		this.client=client;
		OrderStatus newStatus= new OrderStatus("Pending", dateOfOrder);
		status.add(newStatus);
		this.cost=0F;
	}
	
	
    public ObjectId getObjectId() {
        return null;
    }

    public Collection<OrderStatus> getStatus() {
        return status;
    }

    public User getClient() {
        return client;
    }

    public Collection<ObjectId> getProducts() {
        return products;
    }

    public ObjectId getDeliveryUser() {
        return delivery;
    }

    public ObjectId getId() {
        return objectId;
    }
    
    
}
