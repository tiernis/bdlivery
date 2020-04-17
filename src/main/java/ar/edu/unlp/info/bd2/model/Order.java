package ar.edu.unlp.info.bd2.model;

import java.util.*;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import javax.persistence.*;

@Entity
@Table(name = "Orden")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	@Column(name ="address_order")
	private String address;
	@Column(name ="coordx_order")
	private Float coordX;
	@Column(name ="coory_order")
	private Float coordY;
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<OrderStatus> status = new ArrayList<>();
	@OneToOne
	private User client;
	@OneToOne
	private User delivery;
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<ProductOrder> products = new ArrayList<>();

	public Order(){

	}
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		this.addOrderStatus("Pending", dateOfOrder);
	}

	public Order getMe(){
		return this;
	}

    public Long getId() {
		return this.id;
    }

	public void setId(Long id) {
		this.id = id;
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

	public List<OrderStatus> getStatus(){
		return this.status;
	}

	public User getClient() {
		return this.client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public User getDeliveryUser() {
		return this.delivery;
	}

	public void setDelivery(User delivery) {
		this.delivery = delivery;
	}

	public List<ProductOrder> getProducts() {
		return this.products;
	}

	public Order addProduct (Long quantity, Product product) {
		ProductOrder newProduct = new ProductOrder(quantity, product, this.getMe());
		this.getProducts().add(newProduct);
		return this;
	}

	public Order addOrderStatus(String status, Date date){
		OrderStatus orderStatus = new OrderStatus(status, this.getMe(), date);
		this.getStatus().add(orderStatus);
		return this;
	}
	
	public Boolean canCancel() {
		return (this.getActualStatus().getStatus() == "Pending");
	}
	
	public Boolean canFinish() {
		return (this.getActualStatus().getStatus() == "Send");
	}
	
	public Boolean canDeliver() {
		return ((this.getActualStatus().getStatus() == "Pending") && (this.getProducts().size() != 0));
	}
	
	public Order deliverOrder(User deliveryUser, Date date) throws DBliveryException {
		if(this.canDeliver()) {
			this.setDelivery(deliveryUser);
			this.addOrderStatus("Send", date);
			return this;
		}else { throw new DBliveryException("The order can't be delivered");}
	}
	
	public Order cancelOrder(Date date) throws DBliveryException {
		if(this.canCancel()) {
			this.addOrderStatus("Cancelled", date);
			return this;
		}else { throw new DBliveryException("The order can't be canceled");}
	}
	
	public Order finishOrder(Date date) throws DBliveryException {
		if(this.canFinish()) {
			this.addOrderStatus("Delivered", date);
			return this;
		}else { throw new DBliveryException("The order can't be finished");}
	}

	public OrderStatus getActualStatus(){
		return this.getStatus().get(this.getStatus().size() - 1);
	}

    public Float getAmount() {
		return null;
    }
}
