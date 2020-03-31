package ar.edu.unlp.info.bd2.model;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Table(name = "ProductOrder")
public class ProductOrder {
	@Id
	private Long id;
	@Column(name ="quantity")
	private Long quantity;
	@OneToOne
	private Product product;
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
	
	public ProductOrder(Long quantity, Product product) {
		this.quantity = quantity;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
