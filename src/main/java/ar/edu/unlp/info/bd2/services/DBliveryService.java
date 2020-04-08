package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DBliveryService implements DBliveryServiceable {

    private DBliveryRepository repository;

    public DBliveryService(DBliveryRepository repository){
        this.repository = repository;
    }

    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
        Product product = new Product(name, price, weight, supplier);
        repository.save(product);
        return product;
    }

    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
        return null;
    }

    @Override
    public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        Supplier supplier = new Supplier(name, cuil, address, coordX, coordY);
        repository.save(supplier);
        return supplier;
    }

    @Override
    public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        User user = new User(email, password, name, username, dateOfBirth);
        repository.save(user);
        return user;
    }

    @Override
    public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
        if(this.repository.getProductById(id).size() != 0) {
            Product product = this.repository.getProductById(id).get(0);
            product.updateProductPrice(price);
            repository.save(product);
            return product;
        }else {throw new DBliveryException("The product don't exist");}
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.of(repository.getUserBy("id", id).get(0));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.of(repository.getUserBy("email", email).get(0));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.of(repository.getUserBy("username", username).get(0));
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return Optional.of(repository.getProductById(id).get(0));
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return Optional.of(repository.getOrderById(id).get(0));
    }

    @Override
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
        Order order = new Order(dateOfOrder,address,coordX,coordY,client);
        repository.save(order);
        return order;
    }

    @Override
    public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            orderConcrete.addProduct(quantity, product);
            repository.save(orderConcrete);
            return orderConcrete;
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            if(orderConcrete.canDeliver()) {
                orderConcrete.deliverOrder(deliveryUser);
                repository.save(orderConcrete);
                return orderConcrete;
            }else { throw new DBliveryException("The order can't be delivered");}
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
        return null;
    }

    @Override
    public Order cancelOrder(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            if(orderConcrete.canCancel()) {
                orderConcrete.cancelOrder();
                repository.save(orderConcrete);
                return orderConcrete;
            }else { throw new DBliveryException("The order can't be canceled");}
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public Order cancelOrder(Long order, Date date) throws DBliveryException {
        return null;
    }

    @Override
    public Order finishOrder(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            if(orderConcrete.canFinish()) {
                orderConcrete.finishOrder();
                repository.save(orderConcrete);
                return orderConcrete;
            }else { throw new DBliveryException("The order can't be finished");}
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public Order finishOrder(Long order, Date date) throws DBliveryException {
        return null;
    }

    @Override
    public boolean canCancel(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            return orderConcrete.canCancel();
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public boolean canFinish(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            return orderConcrete.canFinish();
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public boolean canDeliver(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            return orderConcrete.canDeliver();
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public OrderStatus getActualStatus(Long order) {
        Order orderConcrete = this.repository.getOrderById(order).get(0);
        return orderConcrete.getActualStatus();
    }

    @Override
    public List<Product> getProductByName(String name) {
        return repository.getProductByName(name);
    }
}