package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SpringDataDBliveryService implements DBliveryService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    ProductRepository prodRepo;

    @Autowired
    SupplierRepository suppRepo;

    @Autowired
    OrderRepository ordRepo;

    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
        Product prod = new Product(name, price, weight, supplier);
        return prodRepo.save(prod);
    }

    @Override
    public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
        Product prod = new Product(name, price, weight, supplier, date);
        return prodRepo.save(prod);
    }

    @Override
    public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
        Supplier supp = new Supplier(name, cuil, address, coordX, coordY);
        return suppRepo.save(supp);
    }

    @Override
    public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
        User user = new User(email, password, name, username, dateOfBirth);
        return userRepo.save(user);
    }

    @Override
    public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
        Optional<Product> prod = prodRepo.findById(id);
        try {
            return prodRepo.save(prod.get().updateProductPrice(price, startDate));
        }
        catch (NullPointerException npe){
            throw new DBliveryException("The product don't exist");
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return ordRepo.findById(id);
    }

    @Override
    public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
        Order order = new Order(dateOfOrder,address,coordX,coordY,client);
        return ordRepo.save(order);
    }

    @Override
    public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(order);
        try {
            return ordRepo.save(ord.get().addProduct(quantity, product));
        }
        catch (NullPointerException npe){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(order);
        try {
            if (this.canDeliver(ord.get().getId())){
                return ordRepo.save(ord.get().deliverOrder(deliveryUser, new Date()));
            } else {
                throw new DBliveryException("The order can't be delivered");
            }
        }
        catch (NullPointerException | DBliveryException e){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(order);
        try {
            if (this.canDeliver(ord.get().getId())){
                return ordRepo.save(ord.get().deliverOrder(deliveryUser, date));
            } else {
                throw new DBliveryException("The order can't be delivered");
            }
        }
        catch (NullPointerException | DBliveryException e){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public Order cancelOrder(Long order) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(order);
        try {
            if (this.canCancel(ord.get().getId())){
                return ordRepo.save(ord.get().cancelOrder(new Date()));
            } else {
                throw new DBliveryException("The order can't be canceled");
            }
        }
        catch (NullPointerException | DBliveryException e){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public Order cancelOrder(Long order, Date date) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(order);
        try {
            if (this.canCancel(ord.get().getId())){
                return ordRepo.save(ord.get().cancelOrder(date));
            } else {
                throw new DBliveryException("The order can't be canceled");
            }
        }
        catch (NullPointerException | DBliveryException e){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public Order finishOrder(Long order) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(order);
        try {
            if (this.canFinish(ord.get().getId())){
                return ordRepo.save(ord.get().finishOrder(new Date()));
            } else {
                throw new DBliveryException("The order can't be finished");
            }
        }
        catch (NullPointerException | DBliveryException e){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public Order finishOrder(Long order, Date date) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(order);
        try {
            if (this.canFinish(ord.get().getId())){
                return ordRepo.save(ord.get().finishOrder(date));
            } else {
                throw new DBliveryException("The order can't be finished");
            }
        }
        catch (NullPointerException | DBliveryException e){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public boolean canCancel(Long order) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(order);
        try {
            return ord.get().canCancel();
        }
        catch (NullPointerException npe){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public boolean canFinish(Long id) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(id);
        try {
            return ord.get().canFinish();
        }
        catch (NullPointerException npe){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public boolean canDeliver(Long order) throws DBliveryException {
        Optional<Order> ord = ordRepo.findById(order);
        try {
            return ord.get().canDeliver();
        }
        catch (NullPointerException npe){
            throw new DBliveryException("The order don't exist");
        }
    }

    @Override
    public OrderStatus getActualStatus(Long order) {
        Optional<Order> ord = ordRepo.findById(order);
        return ord.get().getActualStatus();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return prodRepo.findByNameContaining(name);
    }

	@Override
	public Product getMaxWeigth() {
		return this.prodRepo.findProductWithMaxWeight();
	}

	@Override
	public List<Order> getAllOrdersMadeByUser(String username) {
		Optional<User> usr = this.getUserByUsername(username);
		return ordRepo.findByClient(usr.get());
	}

	@Override
	public List<Order> getPendingOrders() {
		return this.ordRepo.getPendingOrders();
	}

	@Override
	public List<Order> getSentOrders() {
		return this.ordRepo.getSentOrders();
	}

	@Override
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		return this.ordRepo.getDeliveredOrdersInPeriod(startDate, endDate);
	}

	@Override
	public List<Order> getDeliveredOrdersForUser(String username) {
		User user= this.getUserByUsername(username).get();
		return this.ordRepo.getDeliveredOrdersForUser(user);
	}

	@Override
	public List<Product> getProductsOnePrice() {
		return this.prodRepo.getProductsOnePrice();
	}

	@Override
	public List<Product> getSoldProductsOn(Date day) {
		return this.prodRepo.getSoldProductsOn(day);
	}
}
