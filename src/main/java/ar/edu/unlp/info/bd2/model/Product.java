package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Table(name = "Product")
public class Product {
	@Id
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
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private ArrayList<OldPrice> oldPrices;
	
	public Product(String name, Float price, Float weight, Supplier supplier)
	{
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.supplier = supplier;
		this.startDate= new Date();
	}

	//public Collection<Integer> getPrices() {
	//	Collection<Integer> list = new LinkedList<Integer>();
	//	for (Product product : this.products)
	//	{
	//		list.add(product.getPrice());
	//	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public ArrayList<OldPrice> getOldPrices() {
		return oldPrices;
	}

	public void setOldPrices(ArrayList<OldPrice> oldPrices) {
		this.oldPrices = oldPrices;
	}

	public Product updateProductPrice(Float p, Date sd) {
		OldPrice old = new OldPrice(price,startDate);
		oldPrices.add(old);
		this.price = p;
		this.startDate = sd;
		return this;
	}

}