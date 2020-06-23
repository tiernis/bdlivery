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
	@Column(name ="weight")
	private Float weight;
	@OneToOne
	private Supplier supplier;
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Price> allPrices = new ArrayList<>();

	public Product(){
	}

	public Product(String name, Float price, Float weight, Supplier supplier)
	{
		this.setName(name);
		this.updateProductPrice(price, new Date());
		this.setWeight(weight);
		this.setSupplier(supplier);
	}

	public Product(String name, Float price, Float weight, Supplier supplier, Date date)
	{
		this.setName(name);
		this.updateProductPrice(price, date);
		this.setWeight(weight);
		this.setSupplier(supplier);
	}

	public Product getMe(){
		return this;
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

	public Float getWeight() {
		return this.weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<Price> getPrices(){
		return this.allPrices;
	}

	public Float getPrice() {
		return this.getPrices().get(this.getPrices().size() - 1).getPrice();
	}

	public Date getStartDate() {
		return this.getPrices().get(this.getPrices().size() - 1).getStartDate();
	}

	public Product updateProductPrice(Float price, Date date) {
		Price new_price = new Price(price, date, this.getMe());
		this.getPrices().add(new_price);
		return this;
	}

	public Float getPriceAt(Date day) {
		Float pricesAt=0F;
		for (int i = 0; i< this.getPrices().size(); i++) {
			if(this.getPrices().get(i).getStartDate().before(day)) {
				pricesAt=this.getPrices().get(i).getPrice();
			}
		}
		return pricesAt;
	}
}