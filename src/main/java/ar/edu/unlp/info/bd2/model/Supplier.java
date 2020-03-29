package ar.edu.unlp.info.bd2.model;

public class Supplier {

	private Integer id;//PREGUNTAR COMO SETEAR ESTE ID SI DE ESO SE ENCARGA SIEMPRE SQL
	private String name;
	private Integer cuil;
	private String address;
	private Float coordX;
	private Float coordY;
	
	public Supplier(String name, Integer cuil, String address, Float coordX, Float coordY) {
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
	}

    public Object getId() {
		return id;
    }

	public Object getName() {
		return name;
	}
}
