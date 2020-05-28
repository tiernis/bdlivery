package ar.edu.unlp.info.bd2.model;

import java.util.*;

public class Price {

	private Float price;
	private Date startDate;

	public Price(){

	}
	
	public Price(Float price, Date startDate) {
		this.setPrice(price);
		this.setStartDate(startDate);
	}


	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
