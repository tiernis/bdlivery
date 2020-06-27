package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.OrderStatus;

@Repository
public interface OrderStatusRepository extends CrudRepository<OrderStatus, Long>{
	List<OrderStatus> findByDateStatus(Date dateStatus);
	List<OrderStatus> findByStatus(String status);
}	
