package ar.edu.unlp.info.bd2.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "Price")
public class Price {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	@Column(name ="price")
	private Float price;
	@Column(name ="startDate")
	private Date startDate;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Product.class)
	private Long id_product;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Price(Float price, Date startDate, Long id_product) {
		this.price = price;
		this.startDate = startDate;
		this.id_product = id_product;
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

	public Long getProduct() {
		return id_product;
	}

	public void setProduct(Long id_product) {
		this.id_product = id_product;
	}
}