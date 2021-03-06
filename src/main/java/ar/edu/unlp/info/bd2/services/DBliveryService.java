package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

public class DBliveryService implements DBliveryServiceable {

    private DBliveryRepository repository;

    public DBliveryService(DBliveryRepository repository){
        this.repository = repository;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
        Product product = new Product(name, price, weight, supplier);
        repository.save(product);
        return product;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
        Product product = new Product(name, price, weight, supplier, date);
        repository.save(product);
        return product;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        Supplier supplier = new Supplier(name, cuil, address, coordX, coordY);
        repository.save(supplier);
        return supplier;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        User user = new User(email, password, name, username, dateOfBirth);
        repository.save(user);
        return user;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
        if(this.repository.getProductById(id).size() != 0) {
            Product product = this.repository.getProductById(id).get(0);
            product.updateProductPrice(price, startDate);
            repository.save(product);
            return product;
        }else {throw new DBliveryException("The product don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public Optional<User> getUserById(Long id) {
        return Optional.of(repository.getUserBy("id", id).get(0));
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public Optional<User> getUserByEmail(String email) {
        return Optional.of(repository.getUserBy("email", email).get(0));
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public Optional<User> getUserByUsername(String username) {
        return Optional.of(repository.getUserBy("username", username).get(0));
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public Optional<Product> getProductById(Long id) {
        return Optional.of(repository.getProductById(id).get(0));
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public Optional<Order> getOrderById(Long id) {
        return Optional.of(repository.getOrderById(id).get(0));
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client){
        Order order = new Order(dateOfOrder,address,coordX,coordY,client);
        repository.save(order);
        return order;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            Product db_prod = this.repository.getProductById(product.getId()).get(0);
            orderConcrete.addProduct(quantity, db_prod);
            repository.save(orderConcrete);
            return orderConcrete;
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            orderConcrete.deliverOrder(deliveryUser, new Date());
            repository.save(orderConcrete);
            return orderConcrete;
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            orderConcrete.deliverOrder(deliveryUser, date);
            repository.save(orderConcrete);
            return orderConcrete;
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Order cancelOrder(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            orderConcrete.cancelOrder(new Date());
            repository.save(orderConcrete);
            return orderConcrete;
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Order cancelOrder(Long order, Date date) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            orderConcrete.cancelOrder(date);
            repository.save(orderConcrete);
            return orderConcrete;
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Order finishOrder(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            orderConcrete.finishOrder(new Date());
            repository.save(orderConcrete);
            return orderConcrete;
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, noRollbackFor=DBliveryException.class)
    public Order finishOrder(Long order, Date date) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            orderConcrete.finishOrder(date);
            repository.save(orderConcrete);
            return orderConcrete;
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public boolean canCancel(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            return orderConcrete.canCancel();
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public boolean canFinish(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            return orderConcrete.canFinish();
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public boolean canDeliver(Long order) throws DBliveryException {
        if(this.repository.getOrderById(order).size() != 0) {
            Order orderConcrete = this.repository.getOrderById(order).get(0);
            return orderConcrete.canDeliver();
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public OrderStatus getActualStatus(Long order) {
        Order orderConcrete = this.repository.getOrderById(order).get(0);
        return orderConcrete.getActualStatus();
    }

    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public List<Product> getProductByName(String name) {
        return repository.getProductByName(name);
    }

    public List<Order> getAllOrdersMadeByUser(String user) {
        return this.repository.getAllOrdersMadeByUser(user);
    }

    public List<User> getUsersSpendingMoreThan(Float amount) {
        return this.repository.getUsersSpendingMoreThan(amount);
    }

    public List<Supplier> getTopNSuppliersInSentOrders(int quantity_suppliers) {
        return this.repository.getTopNSuppliersInSentOrders(quantity_suppliers);
    }

    public List<Product> getTop10MoreExpensiveProducts() {
        return this.repository.getTop10MoreExpensiveProducts();
    }

    public List<User> getTop6UsersMoreOrders() {
        return this.repository.getTop6UsersMoreOrders();
    }

    public List<Order> getCancelledOrdersInPeriod(Date start, Date end) {
        return this.repository.getCancelledOrdersInPeriod(start, end);
    }

    public List<Order> getPendingOrders() {
        return this.repository.getPendingOrders();
    }

    public List<Order> getSentOrders() {
        return this.repository.getSentOrders();
    }

    public List<Order> getDeliveredOrdersInPeriod(Date start, Date end) {
        return this.repository.getDeliveredOrdersInPeriod(start, end);
    }

    public List<Order> getDeliveredOrdersForUser(String username) {
        return this.repository.getDeliveredOrdersForUser(username);
    }

    public List<Order> getSentMoreOneHour() {
        return this.repository.getSentMoreOneHour();
    }

    public List<Order> getDeliveredOrdersSameDay() {
        return this.repository.getDeliveredOrdersSameDay();
    }

    public List<User> get5LessDeliveryUsers() {
        return this.repository.get5LessDeliveryUsers();
    }

    public Product getBestSellingProduct() {
        return this.repository.getBestSellingProducts().get(0);
    }

    public List<Product> getProductsOnePrice() {
        return this.repository.getProductsOnePrice();
    }

    public List<Product> getProductIncreaseMoreThan100() {
        return this.repository.getProductIncreaseMoreThan100();
    }

    public Supplier getSupplierLessExpensiveProduct() {
        return this.repository.getSupplierLessExpensiveProduct().get(0);
    }

    public List<Supplier> getSuppliersDoNotSellOn(Date day) {
        return this.repository.getSuppliersDoNotSellOn(day);
    }

    public List<Product> getSoldProductsOn(Date date) {
    	return this.repository.getSoldProductsOn(date);
    }

    public List<Order> getOrdersCompleteMorethanOneDay() {
        return this.repository.getOrdersCompleteMorethanOneDay();
    }

    public List<Object[]> getProductsWithPriceAt(Date date) {
        return repository.getProductsWithPriceAt(date);
    }

    public List<Product> getProductsNotSold() {
        return this.repository.getProductsNotSold();
    }

    public List<Order> getOrderWithMoreQuantityOfProducts(Date date) {
        return repository.getOrderWithMoreQuantityOfProducts(date);
    }
}