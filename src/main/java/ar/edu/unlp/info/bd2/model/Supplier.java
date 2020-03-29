package ar.edu.unlp.info.bd2.model;

public class Supplier {
	
	private String name;
	private Integer cuil;
	private String address;
	private Float coordX;
	private Float coordY;
	
	public Supplier createSupplier(String name, Integer cuil, String address, Float cordx, Float cordy) {
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		return this;
	}
	
	
}
