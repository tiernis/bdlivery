package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name = "ProductOrder")
public class ProductOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	@OneToOne
	private Product product;
	@OneToOne
	private Order order;
	@Column(name ="quantity")
	private Long quantity;

	public ProductOrder(){

	}
	
	public ProductOrder(Long quantity, Product product, Order order) {
		this.setQuantity(quantity);
		this.setProduct(product);
		this.setOrder(order);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
