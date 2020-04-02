package ar.edu.unlp.info.bd2.model;

import ar.edu.unlp.info.bd2.repositories.DBliveryException;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "Orden")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	@Column(name ="date_of_order")
	private Date dateOfOrder;
	@Column(name ="address_order")
	private String address;
	@Column(name ="coordx_order")
	private Float coordX;
	@Column(name ="coory_order")
	private Float coordY;
	@Column(name ="state")
	private String state;
	@OneToOne
	private User client;
	@OneToOne
	private User delivery;
	@ElementCollection(targetClass=ProductOrder.class)
	private List<ProductOrder> products;

	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User user) {
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.client = user;
		this.state = "Pending";
		this.products = new ArrayList<>();
	}

    public Long getId() {
		return this.id;
    }

    public String getState(){
		return this.state;
	}

	public User getClient() {
		return this.client;
	}

	public List<ProductOrder> getProducts() {
		return this.products;
	}

	public User getDeliveryUser() {
		return this.client;
	}
	
	public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
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

	public void setState(String state) {
		this.state = state;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order addProduct (Long quantity, Product product) {
		ProductOrder new_product = new ProductOrder(quantity,product,this.id);
		this.products.add(new_product);
		return this;
	}
	
	public Boolean canCancel() {
		if(this.state == "pending"){
			return true;
		}else {return false;}
	}
	
	public Boolean canFinish() {
		if(this.state == "Send"){
			return true;
		}else {return false;}
	}
	
	/*public Boolean canDeliver() {
		if((this.state == "pending") && (this.products.size() != 0)){
			return true;
		}else {return false;}
	}*/
	
	public Order deliverOrder(User deliveryUser) {
		this.delivery= deliveryUser;
		this.state= "Send";
		return this;
	}
	
	public Order cancelOrder() {
		this.state="Canceled";
		return this;
	}
	
	public Order finishOrder() throws DBliveryException {
		if (this.state == "Send"){
			this.state = "Delivered";
			return this;
		}
		else {
			throw new DBliveryException("The order can't be finished");
		}
	}

	public List<String> getStatus() {
		List<String> list = new LinkedList<>();
		list.add(this.state);
		return list;
	}
}
