package ar.edu.unlp.info.bd2.model;

public class ProductOrder {
	
	private Long quantity;
	private Product product;
	
	public ProductOrder() {}
	
	public ProductOrder(Long quantity, Product product) {
		this.quantity = quantity;
		this.product = product;
	}

	public Long getQuantity() {
		return quantity;
	}

	public Product getProduct() {
		return product;
	}
	
}
