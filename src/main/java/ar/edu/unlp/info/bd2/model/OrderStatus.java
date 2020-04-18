package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OrderStatus")
public class OrderStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique=true, nullable = false)
    private Long id;
    @OneToOne
    private Order order;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "date_status", nullable = false)
    private Date dateStatus;

    public OrderStatus(){

    }

    public OrderStatus(String status, Order order, Date dateStatus){
        this.setStatus(status);
        this.setOrder(order);
        this.setDateStatus(dateStatus);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(Date dateStatus) {
        this.dateStatus = dateStatus;
    }
}