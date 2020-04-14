package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Supplier")
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
	private Long id;
	@Column(name ="name_supplier")
	private String name;
	@Column(name ="cuil")
	private String cuil;
	@Column(name ="address_supplier")
	private String address;
	@Column(name ="coordx_supplier")
	private Float coordX;
	@Column(name ="coory_supplier")
	private Float coordY;
	@OneToMany(mappedBy = "supplier")
	private List<Price> allPrices;

	public Supplier(){
	}
	
	public Supplier(String name, String cuil, String address, Float coordX, Float coordY) {
		this.setName(name);
		this.setCuil(cuil);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
	}

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

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getCoordX() {
		return coordX;
	}

	public void setCoordX(Float coordX) {
		this.coordX = coordX;
	}

	public Float getCoordY() {
		return coordY;
	}

	public void setCoordY(Float coordY) {
		this.coordY = coordY;
	}
	
}
