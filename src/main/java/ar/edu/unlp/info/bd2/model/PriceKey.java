package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PriceKey implements Serializable {

    @Column(name="product_id")
    Long productId;

    @Column(name="supplier_id")
    Long supplierId;

}
