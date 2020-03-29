package ar.edu.unlp.info.bd2.model;

public class Product {
	
	private String name;
	private Integer price;
	private double weight;
	private Supplier supplier;
	//CREO QUE ESTA CLASE NO ES EL PRODUCTO EN SI, SINO LO QUE SE COMUNICA CON EL MODELO. PREGUNTAR
	
	public Product createProduct(String name, Integer price, double weight, Supplier supplier)
	{
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.supplier = supplier;
		//Aca habria que agregarlo a algun arraylist o algo
		return this;
	}

	public Product updateProductPrice(Long id, Float price, Date startDate)
	{
		try
		{
			//Aca hay que buscar el producto por id
		}
		catch(DBliveryException dbex)
		{
			System.out.println(dbex.getMessage());
		}
	}
}
