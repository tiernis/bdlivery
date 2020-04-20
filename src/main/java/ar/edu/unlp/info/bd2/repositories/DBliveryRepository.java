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

        return this.sessionFactory.getCurrentSession().createQuery("FROM OrderStatus WHERE status='Delivered' and date_status='"+date_mod+"'").list();
    }

    public List<Order> getAllOrdersMadeByUser(String username) {
        return this.sessionFactory.getCurrentSession().createQuery("FROM Order AS o INNER JOIN User AS u ON (o.client=u.id) WHERE username = '"+username+"'").list();
    }

    public List<Product> getTop10MoreExpensiveProducts() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT prod FROM Product AS prod INNER JOIN Price AS price ON(prod.id = price.product) WHERE price.id IN (SELECT MAX(id) FROM Price GROUP BY product) ORDER BY price.price DESC").setMaxResults(9).list();
    }

    /*public List<Order> getPendingOrders() {
    }*/
}
