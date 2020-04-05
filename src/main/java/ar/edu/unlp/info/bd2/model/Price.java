package ar.edu.unlp.info.bd2.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "Price")
public class Price {

	@EmbeddedId
	private Long id;
	@ManyToOne
	@MapsId("product_id")
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne
	@MapsId("supplier_id")
	@JoinColumn(name = "supplier_id")
	private Supplier supplier;
	@Column(name ="price")
	private Float price;
	@Column(name ="startDate")
	private Date startDate;
	
	public Price(Float price, Date startDate, Product product, Supplier supplier) {
		this.setPrice(price);
		this.setStartDate(startDate);
		this.setProduct(product);
		this.setSupplier(supplier);
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}