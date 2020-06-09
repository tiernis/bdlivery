package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class OrderStatus {
    
	private String status;
	private Date dateStatus;
	
	public OrderStatus() {}
	
	public OrderStatus(String status, Date dateStatus) {
		this.status = status;
		this.dateStatus = dateStatus;
	}
	public String getStatus() {
		return status;
	}
	public Date getDateStatus() {
		return dateStatus;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDateStatus(Date dateStatus) {
		this.dateStatus = dateStatus;
	}
	
}
