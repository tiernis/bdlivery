package ar.edu.unlp.info.bd2.model;

//HACER DE 0

public class ProductOrder {
	
	private Integer cant;
	private Product product;
	
	public ProductOrder(Integer cant, Product product) {
		super();
		this.cant = cant;
		this.product = product;
	}

    public short getStatus() {
		return 1;
    }
}
