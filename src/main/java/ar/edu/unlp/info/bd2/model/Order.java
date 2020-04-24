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
	@Column(name ="coordy_order")
	private Float coordY;
	@OneToOne
	private User client;
	@OneToOne
	private User delivery;
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderStatus> status = new ArrayList<>();
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<ProductOrder> products = new ArrayList<>();
	@Column(name ="cost")
	private Float cost;

	public Order(){

	}

	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
		this.setClient(client);
		this.addOrderStatus("Pending", dateOfOrder);
		this.cost=0F;
	}

	public Order getMe(){
		return this;
	}

	public Float getCost() {
		return this.cost;
	}

	public void setCost(Float cost) {
		this.cost =cost;
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
		Float newProductCost=product.getPriceAt(this.getStatus().get(0).getDateStatus()) * quantity;
		this.setCost(this.getCost() + newProductCost);
		return this;
	}

	public Order addOrderStatus(String status, Date date){
		OrderStatus orderStatus = new OrderStatus(status, this.getMe(), date);
		this.getStatus().add(orderStatus);
		return this;
	}

	public Boolean canCancel() {
		return (this.getActualStatus().getStatus().equals("Pending"));
	}

	public Boolean canFinish() {
		return (this.getActualStatus().getStatus().equals("Send"));
	}

	public Boolean canDeliver() {
		return ((this.getActualStatus().getStatus().equals("Pending")) && (this.getProducts().size() != 0));
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
		/*Float precioTotal = 0F;

		for(ProductOrder po : this.getProducts()) {
			precioTotal += po.getProduct().getPrice() * po.getQuantity();
		};
		return precioTotal;*/
		return this.getCost();
	}
}