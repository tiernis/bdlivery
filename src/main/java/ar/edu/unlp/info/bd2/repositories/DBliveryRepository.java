package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
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
}
