package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Supplier")
public class Supplier {
	@Id
	private Long id;
	@Column(name ="name_supplier")
	private String name;
	@Column(name ="cuil")
	private Integer cuil;
	@Column(name ="adress_supplier")
	private String address;
	@Column(name ="coordx_supplier")
	private Float coordX;
	@Column(name ="coory_supplier")
	private Float coordY;
	
	public Supplier(String name, Integer cuil, String address, Float coordX, Float coordY) {
		this.name = name;
		this.cuil = cuil;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
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

	public Integer getCuil() {
		return cuil;
	}

	public void setCuil(Integer cuil) {
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
