package ar.edu.unlp.info.bd2.model;

import java.util.*;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "OldPrice")
public class OldPrice {
	@Id
	private Long id;
	@Column(name ="price")
	private Float price;
	@Column(name ="startDate")
	private Date startDate;
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public OldPrice(Float price, Date startDate) {
		this.price = price;
		this.startDate = startDate;
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