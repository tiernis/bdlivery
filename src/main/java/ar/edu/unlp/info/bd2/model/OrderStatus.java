package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class OrderStatus {
    
	private String status;
	private Date dateStatus;
	
	public OrderStatus() {}
	
	public OrderStatus(String status, Date dateStatus) {
		super();
		this.status = status;
		this.dateStatus = dateStatus;
	}
	public String getStatus() {
		return status;
	}
	public Date getDateStatus() {
		return dateStatus;
	}
	
	
}
