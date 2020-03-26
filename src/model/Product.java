package model;

public class Product {
	
	private String name;
	private double weight;
	private Supplier supplier;
	
	public Product Product(String name, double weight, Supplier supplier) {
		this.name = name;
		this.weight = weight;
		this.supplier = supplier;
		return this;
	}
	
}
