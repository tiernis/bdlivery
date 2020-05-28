package ar.edu.unlp.info.bd2.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Supplier {
	
	@BsonId
	private ObjectId objectId;
	private String name;
	private String cuil;
	private String address;
	private Float coordX;
	private Float coordY;
	
	public Supplier(String name, String cuil, String address, Float coordX, Float coordY) {
		this.setName(name);
		this.setCuil(cuil);
		this.setAddress(address);
		this.setCoordX(coordX);
		this.setCoordY(coordY);
	}

	public ObjectId getObjectId() {
		return objectId;
	}
	
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
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
