package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DBliveryRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Object o) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    public List<User> getUserBy(String field, Serializable id) {
        return this.sessionFactory.getCurrentSession().createQuery("from User where " + field + " = '" + id + "'").list();
    }

    public List<Product> getProductById(Serializable id) {
        return this.sessionFactory.getCurrentSession().createQuery("from Product where id = '" + id + "'").list();
    }

    public List<Product> getProductByName(Serializable id) {
        return this.sessionFactory.getCurrentSession().createQuery("from Product where name like '%" + id + "%'").list();
    }

    public List<Order> getOrderById(Serializable id) {
        return this.sessionFactory.getCurrentSession().createQuery("from Order where id = '" + id + "'").list();
    }

    public List<OrderStatus> getDeliveredOrdersFrom(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date_mod = simpleDateFormat.format(date);

        return this.sessionFactory.getCurrentSession().createQuery("FROM OrderStatus WHERE status='Delivered' AND date_status='" + date_mod + "'").list();
    }

    public List<Order> getAllOrdersMadeByUser(String username) {
        return this.sessionFactory.getCurrentSession().createQuery("select o FROM Order AS o INNER JOIN User AS u ON (o.client=u.id) WHERE username = '" + username + "'").list();
    }

    public List<Product> getTop10MoreExpensiveProducts() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT prod FROM Product AS prod INNER JOIN Price AS price ON(prod.id = price.product) WHERE price.id IN ( SELECT MAX(id) FROM Price GROUP BY product) ORDER BY price.price DESC").setMaxResults(9).list();
    }

    public List<User> getTop6UsersMoreOrders() {
        List<Object []> results = this.sessionFactory.getCurrentSession().createQuery("SELECT u, COUNT(o.id) AS q_order FROM User AS u INNER JOIN Order AS o ON(u.id = o.client) GROUP BY u ORDER BY q_order DESC").setMaxResults(6).list();
        List<User> users = new ArrayList<>();
        for (Object [] row : results) {
            users.add((User) row[0]);
        }
        return users;
    }

    public List<Order> getPendingOrders() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT o FROM Order AS o INNER JOIN OrderStatus AS os ON(o.id = os.order) WHERE os.order NOT IN(SELECT os1.order FROM OrderStatus AS os1 WHERE os1.status!='Pending')").list();
    }

    public List<Order> getCancelledOrdersInPeriod(Date start, Date end) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date_start_mod = simpleDateFormat.format(start);
        String date_end_mod = simpleDateFormat.format(end);

        return this.sessionFactory.getCurrentSession().createQuery("SELECT os FROM OrderStatus AS os WHERE os.status = 'Cancelled' AND os.dateStatus BETWEEN '" + date_start_mod + "' AND '" + date_end_mod + "'").list();
    }

    public List<Order> getSentOrders() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT o FROM Order AS o INNER JOIN OrderStatus AS os ON(o.id = os.order) WHERE os.status='Send' AND os.order NOT IN(SELECT os1.order FROM OrderStatus AS os1 WHERE os1.status!='Pending' AND os1.status!='Send')").list();
    }

    public List<User> getUsersSpendingMoreThan(Float amount) {
    	//SELECT u  FROM Order AS o INNER JOIN User AS u ON(u.id=o.client) INNER JOIN OrderStatus os ON (o.id=os.order) WHERE (os.status='Delivered') AND ((o.cost) > '" + amount + "')"
        return this.sessionFactory.getCurrentSession().createQuery("SELECT u, SUM(o.cost)  FROM Order AS o INNER JOIN User AS u ON(u.id=o.client) INNER JOIN OrderStatus os ON (o.id=os.order) WHERE (os.status='Delivered') GROUP BY u.id HAVING (SUM(o.cost) > '" + amount + "')").list();
    }

    public List<Order> getDeliveredOrdersInPeriod(Date start, Date end) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date_start_mod = simpleDateFormat.format(start);
        String date_end_mod = simpleDateFormat.format(end);
        return this.sessionFactory.getCurrentSession().createQuery("SELECT os FROM OrderStatus AS os WHERE os.status = 'Delivered' AND os.dateStatus BETWEEN '" + date_start_mod + "' AND '" + date_end_mod + "'").list();
    }

    public List<Order> getDeliveredOrdersForUser(String username) {
        Long idUser = this.getUserBy("username", username).get(0).getId();
        return this.sessionFactory.getCurrentSession().createQuery("SELECT o FROM Order AS o INNER JOIN OrderStatus AS os ON (os.order=o.id) WHERE(os.status='Delivered' AND o.client='" + idUser + "')").list();
    }

    public List<Order> getDeliveredOrdersSameDay() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT o FROM Order AS o INNER JOIN OrderStatus AS os ON (os.order=o.id) WHERE os.status='Pending' AND os.order IN(SELECT os1.order FROM OrderStatus AS os1 WHERE os1.status='Delivered' AND os.dateStatus=os1.dateStatus)").list();
    }

    public List<Product> getProductLessExpensive() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT pro FROM Product AS pro INNER JOIN Price AS pri ON (pri.product=pro.id) ORDER BY pri.price").list();
    }

    public List<Supplier> getSupplierLessExpensiveProduct() {
        Long productLE = this.getProductLessExpensive().get(0).getId();
        return this.sessionFactory.getCurrentSession().createQuery("SELECT s FROM Supplier AS s INNER JOIN Product AS p ON (s.id=p.supplier) WHERE (p.id='" + productLE + "') ").list();
    }

    public List<User> get5LessDeliveryUsers() {
        List<Object []> results = this.sessionFactory.getCurrentSession().createQuery("SELECT u, COUNT(u.id) AS q_delivery FROM Order AS o INNER JOIN OrderStatus AS os ON(o.id=os.order) INNER JOIN User AS u ON(o.delivery=u.id) WHERE os.order IN(SELECT os1.order FROM OrderStatus AS os1 WHERE os1.status='Send') GROUP BY u.id ORDER BY q_delivery ASC").setMaxResults(5).list();
        List<User> users = new ArrayList<>();
        for (Object [] row : results) {
            users.add((User) row[0]);
        }
        return users;
    }
    
    public List<Product> getProductsOnePrice() {
    	return this.sessionFactory.getCurrentSession().createQuery("SELECT pro FROM Price AS pri INNER JOIN Product AS pro ON (pri.product=pro.id) GROUP BY pri.product HAVING COUNT(*)=1").list();
    }
    
    public List<Supplier> getTopNSuppliersInSentOrders(int n){
    	//"SELECT s FROM ProductOrder po INNER JOIN Product p ON (po.product=p.id) INNER JOIN Supplier s ON (p.supplier=s.id) INNER JOIN OrderStatus os ON (os.order=po.order) WHERE os.status='Send' AND os.order NOT IN(SELECT os1.order FROM OrderStatus AS os1 WHERE os1.status!='Pending' AND os1.status!='Send') GROUP BY s.id ORDER BY COUNT(*) DESC"
    	return this.sessionFactory.getCurrentSession().createQuery("SELECT s FROM ProductOrder po INNER JOIN Product p ON (po.product=p.id) INNER JOIN Supplier s ON (p.supplier=s.id) INNER JOIN OrderStatus os ON (os.order=po.order) WHERE os.status='Send' GROUP BY s.id ORDER BY COUNT(*) DESC").setMaxResults(n).list();
    }
    
    public List<Supplier> getSuppliersDoNotSellOn(Date day){
    	String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String day_mod = simpleDateFormat.format(day);
    	return this.sessionFactory.getCurrentSession().createQuery("SELECT s FROM Supplier AS s WHERE s NOT IN (SELECT p.supplier FROM Order AS o INNER JOIN ProductOrder AS po ON (o.id=po.order) INNER JOIN Product AS p ON (p.id=po.product) INNER JOIN OrderStatus AS os ON (o.id=os.order) WHERE (os.dateStatus='"+day_mod+"'))").list();
    }
    
    public List<Product> getProductsNotSold(){
    	return this.sessionFactory.getCurrentSession().createQuery("SELECT p FROM Product AS p WHERE p NOT IN (SELECT po.product FROM ProductOrder AS po) ").list();
    }
}