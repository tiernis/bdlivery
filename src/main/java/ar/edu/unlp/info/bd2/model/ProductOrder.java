package ar.edu.unlp.info.bd2.model;

//HACER DE 0

public class ProductOrder {
	
	private Long quantity;
	private Product product;
	
	public ProductOrder(Long quantity, Product product) {
		this.quantity = quantity;
		this.product = product;
	}

    public short getStatus() {
		return 1;
    }
}
