package ar.edu.unlp.info.bd2.model;

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
	@OneToMany(mappedBy = "order")
	private List<OrderStatus> status = new ArrayList<>();
	@OneToOne
	private User client;
	@OneToOne
	private User delivery;
	@OneToMany(mappedBy = "order")
	private List<ProductOrder> products = new ArrayList<>();
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		this.setDateOfOrder(dateOfOrder);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		this.addStatus("Pending");
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

	public Order addStatus(String status){
		OrderStatus orderStatus = new OrderStatus(status, this.getMe(), this.getClient());
		this.getStatus().add(orderStatus);
		return this;
	}
	
	public Boolean canCancel() {
		return (this.getStatus().size() == 1);
	}
	
	public Boolean canFinish() {
		return (this.getStatus().size() == 2);
	}
	
	public Boolean canDeliver() {
		return ((this.getStatus().size() == 1) && (this.getProducts().size() != 0));
	}
	
	public Order deliverOrder(User deliveryUser) {
		this.setDelivery(deliveryUser);
		this.addStatus("Sent");
		return this;
	}
	
	public Order cancelOrder() {
		this.addStatus("Cancelled");
		return this;
	}
	
	public Order finishOrder() {
		this.addStatus("Delivered");
		return this;
	}

	public OrderStatus getActualStatus(){
		return this.getStatus().get(this.getStatus().size() - 1);
	}
}
