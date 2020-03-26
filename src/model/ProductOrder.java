package model;

public class ProductOrder {
	
	private Integer cant;
	private Product product;
	
	public ProductOrder ProductOrder(Integer cant, Product product) {
		super();
		this.cant = cant;
		this.product = product;
		return this;
	}
	
}
