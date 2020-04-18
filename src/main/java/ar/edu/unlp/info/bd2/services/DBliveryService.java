package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            product.updateProductPrice(price, new Date());
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
            orderConcrete.addProduct(quantity, product);
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

    @Override
    @Transactional(propagation=Propagation.REQUIRED, readOnly=true, noRollbackFor=DBliveryException.class)
    public List<Product> getProductByName(String name) {
        return repository.getProductByName(name);
    }

    public List<Order> getAllOrdersMadeByUser(String user) {
        return null;
    }

    @Override
    public List<User> getUsersSpendingMoreThan(float quantity) {
        return null;
    }

    @Override
    public List<User> getUsersSpendingMoreThan(Float amount) {
        return null;
    }

    public List<Supplier> getTopNSuppliersInSentOrders(int quantity_suppliers) {
        return null;
    }

    public List<Product> getTop10MoreExpensiveProducts() {
        return null;
    }

    public List<User> getTop6UsersMoreOrders() {
        return null;
    }

    public List<Order> getCancelledOrdersInPeriod(Date start, Date end) {
        return null;
    }

    public List<Order> getPendingOrders() {
        return null;
    }

    public List<Order> getSentOrders() {
        return null;
    }

    public List<Order> getDeliveredOrdersInPeriod(Date start, Date end) {
        return null;
    }

    public List<Order> getDeliveredOrdersForUser(String user) {
        return null;
    }

    public List<Order> getSentMoreOneHour() {
        return null;
    }

    public List<Order> getDeliveredOrdersSameDay() {
        return null;
    }

    public List<User> get5LessDeliveryUsers() {
        return null;
    }

    public Product getBestSellingProduct() {
        return null;
    }

    public List<Product> getProductsOnePrice() {
        return null;
    }

    public List<Product> getProductIncreaseMoreThan100() {
        return null;
    }

    public Supplier getSupplierLessExpensiveProduct() {
        return null;
    }

    public List<Supplier> getSuppliersDoNotSellOn(Date date) {
        return null;
    }

    public List<Product> getSoldProductsOn(Date date) {
        return null;
    }

    public List<Order> getOrdersCompleteMorethanOneDay() {
        return null;
    }

    public List<Object[]> getProductsWithPriceAt(Date date) {
        return null;
    }

    public List<Product> getProductsNotSold() {
        return null;
    }

    public List<Order> getOrderWithMoreQuantityOfProducts(Date date) {
        return null;
    }
}