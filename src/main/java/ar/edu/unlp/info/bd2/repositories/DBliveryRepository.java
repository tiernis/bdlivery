package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class DBliveryRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Object o){
        this.sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    public List<User> getUserBy(String field, Serializable id){
        return this.sessionFactory.getCurrentSession().createQuery("from User where "+ field +" = '" + id + "'").list();
    }

    public List<Product> getProductById(Serializable id){
        return this.sessionFactory.getCurrentSession().createQuery("from Product where id = '" + id + "'").list();
    }

    public List<Product> getProductByName(Serializable id){
        return this.sessionFactory.getCurrentSession().createQuery("from Product where name like '%"+ id + "%'").list();
    }

    public List<Order> getOrderById(Serializable id){
        return this.sessionFactory.getCurrentSession().createQuery("from Order where id = '" + id + "'").list();
    }

    public List<OrderStatus> getDeliveredOrdersFrom(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date_mod = simpleDateFormat.format(date);

        return this.sessionFactory.getCurrentSession().createQuery("FROM OrderStatus WHERE status='Delivered' AND date_status='"+date_mod+"'").list();
    }

    public List<Order> getAllOrdersMadeByUser(String username) {
        return this.sessionFactory.getCurrentSession().createQuery("FROM Order AS o INNER JOIN User AS u ON (o.client=u.id) WHERE username = '"+username+"'").list();
    }

    public List<Product> getTop10MoreExpensiveProducts() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT prod FROM Product AS prod INNER JOIN Price AS price ON(prod.id = price.product) WHERE price.id IN ( SELECT MAX(id) FROM Price GROUP BY product) ORDER BY price.price DESC").setMaxResults(9).list();
    }

    public List<User> getTop6UsersMoreOrders() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT u.username, COUNT(Orders.*) AS q_order FROM User AS u INNER JOIN Order AS o ON(u.id = o.client) GROUP BY u.username ORDER BY q_order DESC").setMaxResults(6).list();
    }

    public List<Order> getPendingOrders() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT o FROM Order AS o INNER JOIN OrderStatus AS os ON(o.id = os.order) WHERE os.order NOT IN(SELECT os1.order FROM OrderStatus AS os1 WHERE os1.status!='Pending')").list();
    }

    public List<Order> getCancelledOrdersInPeriod(Date start, Date end) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date_start_mod = simpleDateFormat.format(start);
        String date_end_mod = simpleDateFormat.format(end);

        return this.sessionFactory.getCurrentSession().createQuery("SELECT os FROM OrderStatus AS os WHERE os.status = 'Cancelled' AND os.dateStatus BETWEEN '"+ date_start_mod +"' AND '"+ date_end_mod +"'").list();
    }

    public List<Order> getSentOrders() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT o FROM Order AS o INNER JOIN OrderStatus AS os ON(o.id = os.order) WHERE os.status='Send' AND os.order NOT IN(SELECT os1.order FROM OrderStatus AS os1 WHERE os1.status!='Pending' AND os1.status!='Send')").list();
    }
}
