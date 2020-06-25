package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByNameContaining(String name);
    //@Query("SELECT max(p.weight) FROM Product p")
    //Float findMaxWeight();
    //Product findByWeight(Float weight);
    @Query("SELECT p FROM Product AS p WHERE p.weight IN (SELECT MAX(pr.weight) FROM Product AS pr)")
    Product findProductWithMaxWeight();
    //@Query("GIMME_A_QUERY_BASTARD!")
    //Product GIMME_A_GODDAMN_NAME(@Param("name") String name);

}