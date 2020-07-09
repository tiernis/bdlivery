package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameContaining(String name);
    //@Query("SELECT max(p.weight) FROM Product p")
    //Float findMaxWeight();
    //Product findByWeight(Float weight);
    @Query("SELECT p FROM Product AS p WHERE p.weight IN (SELECT MAX(pr.weight) FROM Product AS pr)")
    Product findProductWithMaxWeight();
    //@Query("GIMME_A_QUERY_BASTARD!")
    //Product GIMME_A_GODDAMN_NAME(@Param("name") String name);
    @Query("SELECT pro FROM Price AS pri INNER JOIN Product AS pro ON (pri.product=pro.id) GROUP BY pri.product HAVING COUNT(*)=1")
    List<Product> getProductsOnePrice();
    
    @Query("SELECT p FROM ProductOrder AS po INNER JOIN Order AS o ON (o.id=po.order) INNER JOIN Product AS p ON (po.product=p.id) WHERE o IN (SELECT os.order FROM OrderStatus AS os WHERE (os.dateStatus= :day))")
    List<Product> getSoldProductsOn(@Param("day") Date day);
}