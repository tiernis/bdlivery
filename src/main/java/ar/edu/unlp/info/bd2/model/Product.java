package ar.edu.unlp.info.bd2.model;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;

public class Product {

    private ObjectId objectId;
    private String name;
    private Float weight;
    private Supplier supplier;
    //private List<Price> allPrices = new ArrayList<>();

    public ObjectId getObjectId() {
        return objectId;
    }

    public Collection<Object> getPrices() {
        return null;
    }

    public String getName() {
        return null;
    }

    public Float getPrice() {
        return null;
    }

    public ObjectId getId() {
        return null;
    }
}
