package ar.edu.unlp.info.bd2.model;

import java.util.Collection;
import java.util.LinkedList;

public class Product {

	private Long id; //PREGUNTAR COMO SETEAR ESTE ID SI DE ESO SE ENCARGA SIEMPRE SQL
	private String name;
	private Integer price;
	private double weight;
	private Supplier supplier;
	private Collection<Product> products;//ESTOY USANDO ESTA VARIABLE PARA TODAS LOS PRODUCTOS, PERO COMO LAS TOMO TODAS???? PRODUCT REPRESENTA A SOLO UN PRODUCTO.
	
	public Product(String name, Integer price, double weight, Supplier supplier)
	{
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.supplier = supplier;
	}

    public Long getId() {
		return this.id;
    }

	public String getName() {
		return this.name;
	}

	public Integer getPrice() {
		return this.price;
	}

	public Collection<Integer> getPrices() {
		Collection<Integer> list = new LinkedList<Integer>();
		for (Product product : this.products)
		{
			list.add(product.getPrice());
		}
		return list;
	}

}