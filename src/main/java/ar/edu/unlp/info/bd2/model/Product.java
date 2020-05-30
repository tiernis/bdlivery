package ar.edu.unlp.info.bd2.model;

import org.bson.codecs.pojo.annotations.BsonId;
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
    
    public Product() {}

    public Product(String name, Float price, Float weight, ObjectId supplier) {
		Price newPrice = new Price(price, new Date());
		allPrices.add(newPrice);
		this.name = name;
		this.weight = weight;
		this.supplier = supplier;
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

	public Collection<Price> getPrices() {
        return allPrices;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return allPrices.get(allPrices.size() - 1).getPrice();
    }
}
