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
        Optional<Product> product = this.getProductById(id);
        return product.get().updateProductPrice(price);
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
        return Optional.empty();
    }

    @Override
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
        Order order = new Order(dateOfOrder,address,coordX,coordY,client);
        repository.save(order);
        return order;
    }

    @Override
    public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
        Order orderConcrete = this.repository.getOrderById(order).get(0);
        return orderConcrete.addProduct(quantity, product);
    }

    @Override
    public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
        return null;
    }

    @Override
    public Order cancelOrder(Long order) throws DBliveryException {
        return null;
    }

    @Override
    public Order finishOrder(Long order) throws DBliveryException {
        Order orderConcrete = this.repository.getOrderById(order).get(0);
        return orderConcrete.finishOrder();
    }

    @Override
    public boolean canCancel(Long order) throws DBliveryException {
        return false;
    }

    @Override
    public boolean canFinish(Long id) throws DBliveryException {
        return false;
    }

    @Override
    public boolean canDeliver(Long order) throws DBliveryException {
        return false;
    }

    @Override
    public ProductOrder getActualStatus(Long order) {
        return null;
    }

    @Override
    public List<Product> getProductByName(String name) {
        return repository.getProductByName(name);
    }
}
