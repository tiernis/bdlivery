package model;

public class Supplier {
	
	private String name;
	private Integer cuil;
	private String address;
	private Float cordx;
	private Float cordy;
	
	public Supplier Supplier(String name, Integer cuil, String address, Float cordx, Float cordy) {
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.cordx = cordx;
		this.cordy = cordy;
		return this;
	}
	
	
}
