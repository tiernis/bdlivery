package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
class ProductOrderKey implements Serializable {

    @Column(name = "order_id")
    Long orderId;

    @Column(name = "product_id")
    Long productId;

}