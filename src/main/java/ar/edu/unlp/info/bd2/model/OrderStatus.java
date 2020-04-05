package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name = "OrderStatus")
public class OrderStatus {

    @EmbeddedId
    private Long id;
    @ManyToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "status", unique=true, nullable = false)
    private String status;

    public OrderStatus(String status, Order order, User user){
        this.setStatus(status);
        this.setOrder(order);
        this.setUser(user);
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}