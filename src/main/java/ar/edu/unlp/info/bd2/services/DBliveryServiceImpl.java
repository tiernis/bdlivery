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
        //this.getRepo().initialize();
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
        Boolean was_inserted =this.getRepo().saveUser(user);
        if (!was_inserted){
            user = this.getRepo().getUserByUsername(username);
        }
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
        return Optional.of(this.getRepo().getUserById(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
    	return Optional.of(this.getRepo().getUserByEmail(email));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
    	return Optional.of(this.getRepo().getUserByUsername(username));
    }

    @Override
    public Optional<Order> getOrderById(ObjectId id) {
        return Optional.of(this.getRepo().getOrder(id));
    }

    @Override
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
    	Order order = new Order(dateOfOrder,address,coordX,coordY,client);
        this.getRepo().saveOrder(order);
        return order;
    }

    @Override
    public Order addProduct(ObjectId order, Long quantity, Product product) throws DBliveryException {
        Order theOrder=this.getRepo().getOrder(order);
        if(theOrder.getObjectId() != null) {
        	this.getRepo().updateOrder(theOrder.addProduct(quantity, product));
        	return theOrder;
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public Order deliverOrder(ObjectId order, User deliveryUser) throws DBliveryException {
    	Order theOrder= this.getRepo().getOrder(order);
        Order modifiedOrder= theOrder.deliver(deliveryUser);
        this.getRepo().updateOrder(modifiedOrder);
        return modifiedOrder;
    }

    @Override
    public Order deliverOrder(ObjectId order, User deliveryUser, Date date) throws DBliveryException {
    	Order theOrder= this.getRepo().getOrder(order);
        Order modifiedOrder= theOrder.deliver(deliveryUser,date);
        this.getRepo().updateOrder(modifiedOrder);
        return modifiedOrder;
    }

    @Override
    public Order cancelOrder(ObjectId order) throws DBliveryException {
    	Order theOrder= this.getRepo().getOrder(order);
        Order modifiedOrder= theOrder.cancel();
        this.getRepo().updateOrder(modifiedOrder);
        return modifiedOrder;
    }

    @Override
    public Order cancelOrder(ObjectId order, Date date) throws DBliveryException {
        Order theOrder= this.getRepo().getOrder(order);
        Order modifiedOrder= theOrder.cancel(date);
        this.getRepo().updateOrder(modifiedOrder);
        return modifiedOrder;
    }

    @Override
    public Order finishOrder(ObjectId order) throws DBliveryException {
    	Order theOrder= this.getRepo().getOrder(order);
        Order modifiedOrder= theOrder.finish();
        this.getRepo().updateOrder(modifiedOrder);
        return modifiedOrder;
    }

    @Override
    public Order finishOrder(ObjectId order, Date date) throws DBliveryException {
    	Order theOrder= this.getRepo().getOrder(order);
        Order modifiedOrder= theOrder.finish(date);
        this.getRepo().updateOrder(modifiedOrder);
        return modifiedOrder;
    }

    @Override
    public boolean canCancel(ObjectId order) throws DBliveryException {
        Order theOrder=this.getRepo().getOrder(order);
        if(theOrder.getObjectId() != null) {
        	return theOrder.canCancel();
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public boolean canFinish(ObjectId id) throws DBliveryException {
    	Order theOrder=this.getRepo().getOrder(id);
        if(theOrder.getObjectId() != null) {
        	return theOrder.canFinish();
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public boolean canDeliver(ObjectId order) throws DBliveryException {
    	Order theOrder=this.getRepo().getOrder(order);
        if(theOrder.getObjectId() != null) {
        	return theOrder.canDeliver();
        }else {throw new DBliveryException("The order don't exist");}
    }

    @Override
    public OrderStatus getActualStatus(ObjectId order) {
    	Order theOrder=this.getRepo().getOrder(order);
    	return theOrder.getActualStatus();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return this.getRepo().getProductByName(name);
    }
    
    @Override
    public List<Order> getAllOrdersMadeByUser(String username) throws DBliveryException{
    	return this.getRepo().getAllOrdersMadeByUser(username);
    }
    
    @Override
    public List<Supplier> getTopNSuppliersInSentOrders(int n){
    	return null;//this.getRepo().getTopNSuppliers(n);
    }

	@Override
	public List<Order> getPendingOrders() {
		return this.getRepo().getPendingOrders();
	}

	@Override
	public List<Order> getSentOrders() {
		return this.getRepo().getSentOrders();
	}

	@Override
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		return this.getRepo().getDeliveredOrdersInPeriod(startDate, endDate);
	}

	@Override
	public List<Order> getDeliveredOrdersForUser(String username) {
		return this.getRepo().getDeliveredOrdersForUser(username);
	}

	@Override
	public Product getBestSellingProduct() {
		return null;
	}

	@Override
	public List<Product> getProductsOnePrice() {
		return this.getRepo().getProductsOnePrice();
	}

	@Override
	public List<Product> getSoldProductsOn(Date day) {
		return null;//this.getRepo().getSoldProductsOn(day);
	}

	@Override
	public Product getMaxWeigth() {
		return this.getRepo().getMaxWeigth();
	}

	@Override
	public List<Order> getOrderNearPlazaMoreno() {
		return this.getRepo().getOrderNearPlazaMoreno();
	}
}
