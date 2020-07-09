package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.User;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	
	List<Order> findByClient(User client);
	
	@Query("SELECT o FROM Order AS o INNER JOIN OrderStatus AS os ON(o.id = os.order) WHERE os.order NOT IN(SELECT os1.order FROM OrderStatus AS os1 WHERE os1.status!='Pending')")
	List<Order> getPendingOrders();
	
	@Query("SELECT o FROM Order AS o INNER JOIN OrderStatus AS os ON(o.id = os.order) WHERE os.status='Send' AND os.order NOT IN(SELECT os1.order FROM OrderStatus AS os1 WHERE os1.status!='Pending' AND os1.status!='Send')")
	List<Order> getSentOrders();
	
	@Query("SELECT o FROM Order AS o INNER JOIN OrderStatus AS os ON (os.order=o.id) WHERE(os.status='Delivered' AND o.client= :user)")
	List<Order> getDeliveredOrdersForUser(@Param("user") User user);
	
	@Query("SELECT os FROM OrderStatus AS os WHERE os.status = 'Delivered' AND os.dateStatus BETWEEN :startDate AND :endDate")
	List<Order> getDeliveredOrdersInPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}