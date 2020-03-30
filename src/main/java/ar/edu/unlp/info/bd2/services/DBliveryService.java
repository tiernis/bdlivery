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
        return null;
    }

    @Override
    public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        return null;
    }

    @Override
    public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        return null;
    }

    @Override
    public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
        return null;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
        return null;
    }

    @Override
    public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
        return null;
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
        return null;
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
        return null;
    }
}
