package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.Association;
import ar.edu.unlp.info.bd2.mongo.PersistentObject;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryMongoRepository;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DBliveryServiceImpl implements DBliveryService {

    private DBliveryMongoRepository repo;

    public DBliveryServiceImpl(DBliveryMongoRepository repository) {
        this.setRepo(repository);
        this.getRepo().initialize();
    }

    public void setRepo(DBliveryMongoRepository repo) {
        this.repo = repo;
    }

    public DBliveryMongoRepository getRepo() {
        return repo;
    }

    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
        Product product = new Product(name, weight, supplier.getObjectId());
        Boolean was_inserted = this.getRepo().saveProduct(product.updateProductPrice(price, date));
        if (!was_inserted){
            product = this.getRepo().getProductByNameAndSupplier(name, supplier);
        }
        return product;
    }

    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
    	 Product product = new Product(name, weight, supplier.getObjectId());
         Boolean was_inserted = this.getRepo().saveProduct(product.updateProductPrice(price, new Date()));
         if (!was_inserted){
             product = this.getRepo().getProductByNameAndSupplier(name, supplier);
         }
         return product;
    }

    @Override
    public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        Supplier supplier = new Supplier(name,cuil,address,coordX,coordY);
        Boolean was_inserted = this.getRepo().saveSupplier(supplier);
        if (!was_inserted){
            supplier = this.getRepo().getSupplier(cuil);
        }
        return supplier;
    }

    @Override
    public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        User user = new User(email,password,username,name,dateOfBirth);
        this.getRepo().saveUser(user);
        return user;
    }

    @Override
    public Product updateProductPrice(ObjectId id, Float price, Date startDate) throws DBliveryException {
        Product product = this.getRepo().getProduct(id);
        if(product.getObjectId() != null) {
            this.getRepo().replaceProduct(product.updateProductPrice(price, startDate));
            return product;
        }else {throw new DBliveryException("The product don't exist");}
    }

    @Override
    public Optional<User> getUserById(ObjectId id) {
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
    public Optional<Order> getOrderById(ObjectId id) {
        return Optional.empty();
    }

    @Override
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
        return null;
    }

    @Override
    public Order addProduct(ObjectId order, Long quantity, Product product) throws DBliveryException {
        return null;
    }

    @Override
    public Order deliverOrder(ObjectId order, User deliveryUser) throws DBliveryException {
        return null;
    }

    @Override
    public Order deliverOrder(ObjectId order, User deliveryUser, Date date) throws DBliveryException {
        return null;
    }

    @Override
    public Order cancelOrder(ObjectId order) throws DBliveryException {
        return null;
    }

    @Override
    public Order cancelOrder(ObjectId order, Date date) throws DBliveryException {
        return null;
    }

    @Override
    public Order finishOrder(ObjectId order) throws DBliveryException {
        return null;
    }

    @Override
    public Order finishOrder(ObjectId order, Date date) throws DBliveryException {
        return null;
    }

    @Override
    public boolean canCancel(ObjectId order) throws DBliveryException {
        return false;
    }

    @Override
    public boolean canFinish(ObjectId id) throws DBliveryException {
        return false;
    }

    @Override
    public boolean canDeliver(ObjectId order) throws DBliveryException {
        return false;
    }

    @Override
    public OrderStatus getActualStatus(ObjectId order) {
        return null;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return this.getRepo().getProductByName(name);
    }
}
