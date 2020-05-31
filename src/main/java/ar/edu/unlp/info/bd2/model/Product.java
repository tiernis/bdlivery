package ar.edu.unlp.info.bd2.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Product {
	
	@BsonId
    private ObjectId objectId;
    private String name;
    private Float weight;
    private ObjectId supplier;
    private List<Price> allPrices = new ArrayList<>();

    public Product(){

	}

    public Product(String name, Float weight, ObjectId supplier) {
		this.setName(name);
		this.setWeight(weight);
		this.setSupplier(supplier);
	}

	public ObjectId getObjectId() {
        return objectId;
    }
	
    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public ObjectId getSupplier() {
		return supplier;
	}

	public void setSupplier(ObjectId supplier) {
		this.supplier = supplier;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAllPrices(List<Price> prices){
    	this.allPrices = prices;
	}

	public List<Price> getAllPrices() {
		return allPrices;
	}

	@BsonIgnore
	public List<Price> getPrices() {
        return allPrices;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return this.getPrices().get(this.getPrices().size() - 1).getPrice();
    }

	public Product updateProductPrice(Float price, Date startDate){
		Price newPrice = new Price(price, startDate);
		this.getPrices().add(newPrice);
		return this;
	}
}
