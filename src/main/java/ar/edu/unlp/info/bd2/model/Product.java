package ar.edu.unlp.info.bd2.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	@Column(name ="name_product")
	private String name;
	@Column(name ="price_product")
	private Float price;
	@Column(name ="startDate")
	private Date startDate;
	@Column(name ="weight")
	private Float weight;
	@OneToOne
	private Supplier supplier;
	@Column
	@ElementCollection(targetClass=Price.class)
	private List<Price> allPrices;
	
	public Product(String name, Float price, Float weight, Supplier supplier)
	{
		this.name = name;
		this.allPrices = new ArrayList<>();
		this.updateProductPrice(price);
		this.weight = weight;
		this.supplier = supplier;
	}
		
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return this.price;
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

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<Price> getPrices(){
		return allPrices;
	}

	public Product updateProductPrice(Float price) {
		this.setPrice(price);
		this.startDate = new Date();
		Price new_price = new Price(this.price, this.startDate, this.id);
		this.allPrices.add(new_price);
		return this;
	}

}