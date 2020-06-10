package ar.edu.unlp.info.bd2.model;

import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.repositories.DBliveryException;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

public class Order {
	@BsonId
	private ObjectId objectId;
	private String address;
	private Point position;
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
		Position pos = new Position(coordX,coordY);
		this.position = new Point(pos);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		OrderStatus newStatus= new OrderStatus("Pending", dateOfOrder);
		status.add(newStatus);
		this.setCost(0F);
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point position) {
		this.position= position;
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
	
	//@BsonIgnore
	public OrderStatus getActualStatus() {
		return this.getStatus().get(this.getStatus().size()-1);
	}
    
	public Boolean canCancel() {
		return (this.getActualStatus().getStatus().equals("Pending"));
	}
	
	public Order cancel() throws DBliveryException {
		if(this.canCancel()) {
			OrderStatus newStatus = new OrderStatus("Cancelled", new Date());
			this.getStatus().add(newStatus);
			return this;
		}else { throw new DBliveryException("The order can't be canceled");}
	}
	
	public Order cancel(Date date) throws DBliveryException {
		if(this.canCancel()) {
			OrderStatus newStatus = new OrderStatus("Cancelled", date);
			this.getStatus().add(newStatus);
			return this;
		}else { throw new DBliveryException("The order can't be canceled");}
	}

	public Boolean canDeliver() {
		return ((this.getActualStatus().getStatus().equals("Pending")) && (this.getProducts().size() != 0));
	}
	
	public Order deliver(User deliveryUser, Date date) throws DBliveryException {
		if(this.canDeliver()) {
			this.setDeliveryUser(deliveryUser);
			OrderStatus newStatus= new OrderStatus("Send", date);
			this.getStatus().add(newStatus);
			return this;
		}else { throw new DBliveryException("The order can't be delivered");}
	}
	
	public Order deliver(User deliveryUser) throws DBliveryException {
		if(this.canDeliver()) {
			this.setDeliveryUser(deliveryUser);
			OrderStatus newStatus= new OrderStatus("Send", new Date());
			this.getStatus().add(newStatus);
			return this;
		}else { throw new DBliveryException("The order can't be delivered");}
	}
	
	public Boolean canFinish() {
		return (this.getActualStatus().getStatus().equals("Send"));
	}
	
	public Order finish(Date date) throws DBliveryException {
		if(this.canFinish()) {
			OrderStatus newStatus= new OrderStatus("Delivered", date);
			this.getStatus().add(newStatus);
			return this;
		}else { throw new DBliveryException("The order can't be finished");}
	}
	
	public Order finish() throws DBliveryException {
		if(this.canFinish()) {
			OrderStatus newStatus= new OrderStatus("Delivered", new Date());
			this.getStatus().add(newStatus);
			return this;
		}else { throw new DBliveryException("The order can't be finished");}
	}
	
	@BsonIgnore	
	public Float getAmount() {
		return this.cost;
	}
}
