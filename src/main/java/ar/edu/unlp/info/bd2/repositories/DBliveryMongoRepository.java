package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Accumulators.first;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoWriteException;
import com.mongodb.client.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.mongodb.client.model.*;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonParseException;
import org.bson.json.JsonWriter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class DBliveryMongoRepository {

    @Autowired private MongoClient client;

    public MongoDatabase getDb() {
        return this.client.getDatabase("bd2_grupo" + this.getGroupNumber() );
    }

    private Integer getGroupNumber() { return 11; }

    private void createUserIndex(){
        MongoCollection<User> collection = this.getDb().getCollection("User", User.class);
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection.createIndex(Indexes.ascending("email, username"), indexOptions);
    }

    private void createSupplierIndex() {
        MongoCollection<Supplier> collection = this.getDb().getCollection("Supplier", Supplier.class);
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection.createIndex(Indexes.ascending("cuil"), indexOptions);
    }

    private void createProductIndex() {
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        IndexOptions indexOptions = new IndexOptions().unique(true);
        collection.createIndex(Indexes.compoundIndex(Indexes.ascending("name"), Indexes.ascending("supplier")), indexOptions);
    }

    public void initialize() {
        this.createUserIndex();
        this.createSupplierIndex();
        this.createProductIndex();
    }

    public Boolean saveUser(User user) throws MongoWriteException {
        MongoCollection<User> collection = this.getDb().getCollection("User", User.class);
        try {
            collection.insertOne(user);
            return true;
        }
        catch(MongoWriteException e) {
            return false;
        }
    }

    public Boolean saveSupplier(Supplier supplier){
        MongoCollection<Supplier> collection = this.getDb().getCollection("Supplier", Supplier.class);
        try {
            collection.insertOne(supplier);
            return true;
        }
        catch(MongoWriteException e) {
            return false;
        }
    }

    public Boolean saveProduct(Product product){
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        try {
            collection.insertOne(product);
            return true;
        }
        catch(MongoWriteException e) {
            collection.deleteOne(eq("_id", this.getProductByName(product.getName()).get(0).getObjectId()));
            collection.insertOne(product);
            return false;
        }
    }

    public Product getProduct(ObjectId id){
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        return collection.find(eq("_id", id)).first();
    }

    public List<Product> getProductByName(String name) {
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        ArrayList<Product> list = new ArrayList<>();
        for (Product dbObject : collection.find(regex("name", ".*" + Pattern.quote(name) + ".*", "i")))
        {
            list.add(dbObject);
        }
        return list;
    }

    public Product getProductByNameAndSupplier(String name, Supplier supplier) {
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        return collection.find(and(eq("name", name), eq("supplier", supplier.getObjectId()))).first();
    }

    public Supplier getSupplier(String cuil) {
        MongoCollection<Supplier> collection = this.getDb().getCollection("Supplier", Supplier.class);
        return collection.find(eq("cuil", cuil)).first();
    }
    
    public Supplier getSupplierById(ObjectId id){
        MongoCollection<Supplier> collection = this.getDb().getCollection("Supplier", Supplier.class);
        return collection.find(eq("_id", id)).first();
    }

    public User getUserById(ObjectId objectId) {
        MongoCollection<User> collection = this.getDb().getCollection("User", User.class);
        return collection.find(eq("_id", objectId)).first();
    }

    public User getUserByEmail(String email) {
        MongoCollection<User> collection = this.getDb().getCollection("User", User.class);
        return collection.find(eq("email", email)).first();
    }

    public User getUserByUsername(String username) {
        MongoCollection<User> collection = this.getDb().getCollection("User", User.class);
        return collection.find(eq("username", username)).first();
    }

    public void replaceProduct(Product product){
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        collection.replaceOne(eq("_id", product.getObjectId()), product);
    }

    public void saveOrder(Order order){
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);
        collection.insertOne(order);
    }

    public Order getOrder(ObjectId id){
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);
        Order o= collection.find(eq("_id", id)).first();
        return o;
    }

    public void updateOrder(Order order){
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);
        collection.replaceOne(eq("_id", order.getObjectId()), order);
    }

    public List<Order> getAllOrdersMadeByUser(String username) {
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);
        ArrayList<Order> list = new ArrayList<>();
        for (Order dbObject : collection.find(eq("client.username", username)))
        {
            list.add(dbObject);
        }
        return list;
    }

    public Product getMaxWeigth() {
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        return collection.find().sort(new Document("weight",-1)).first();
    }

    public List<Order> getPendingOrders() {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);

        for (Order dbObject : collection.find(eq("actualStatus.status", "Pending")))
        {
            list.add(dbObject);
        }
        return list;
    }

    public List<Order> getSentOrders() {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);

        for (Order dbObject : collection.find(eq("actualStatus.status", "Send")))
        {
            list.add(dbObject);
        }
        return list;
    }

    public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);
        for (Order order : collection.aggregate(Arrays.asList(
                match(eq("actualStatus.status", "Delivered")),
                match(gt("actualStatus.dateStatus", startDate)),
                match(lt("actualStatus.dateStatus", endDate))
        ))) {
            list.add(order);
        }
        return list;
    }

    public List<Order> getDeliveredOrdersForUser(String username) {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);
        for (Order order : collection.aggregate(Arrays.asList(
                match(eq("actualStatus.status", "Delivered")),
                match(eq("client.username", username))
        ))) {
            list.add(order);
        }
        return list;
    }

    public List<Product> getProductsOnePrice(){
        ArrayList<Product> list = new ArrayList<>();
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);

        for (Product dbObject : collection.find(size("allPrices",1)))
        {
            list.add(dbObject);
        }
        return list;
    }

    public List<Order> getOrderNearPlazaMoreno() {
        ArrayList<Order> list = new ArrayList<>();
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);
        Point refPoint = new Point(new Position(-34.921236,-57.954571));
        for (Order order : collection.find(Filters.near("position", refPoint, 400.0, 0.0)))
        {
            list.add(order);
        }
        return list;
    }

    public List<Product> getSoldProductsOn(Date day) {
        Set<Product> products = new HashSet<>();
        MongoCollection<Order> collection = this.getDb().getCollection("Order", Order.class);

        for (Order order : collection.aggregate(Arrays.asList(
                match(eq("status.0.dateStatus", day))
        )))
        {
            for (ProductOrder prod : order.getProducts()) {
                products.add(prod.getProduct());
            }
        }
        ArrayList<Product> products_final = new ArrayList<>(products);
        return products_final;
    }

    public Product getBestSellingProduct() {
        MongoCollection<Document> collection = this.getDb().getCollection("Order", Document.class);
        /*Object doc = collection.aggregate(Arrays.asList(
                unwind("$products"),
                group(
                        "$products.product._id", Accumulators.sum("count", 1)
                ),
                sort(Sorts.descending("count")),
                lookup("Product", "_id", "_id", "result")
        )).first();*/
        return this.getProduct(new ObjectId(collection.aggregate(Arrays.asList(
                unwind("$products"),
                group(
                        new Document().append("product_id", "$products.product._id"), Accumulators.sum("count", 1)
                ),
                sort(Sorts.descending("count"))
        )).first().get("_id").toString().substring(21,45)));
    }

    public Product getProductById(ObjectId id){
        MongoCollection<Product> collection = this.getDb().getCollection("User", Product.class);
        return collection.find(eq("_id", id)).first();
    }

    public List<Supplier> getTopNSuppliers(int n) {
        ArrayList<Supplier> list = new ArrayList<>();
        MongoCollection<Document> collection = this.getDb().getCollection("Order", Document.class);
        for (Document docu : collection.aggregate(Arrays.asList(
                match(
                        eq("actualStatus.status", "Send")
                ),
                unwind("$products"),
                group(
                        new Document().append("supplier_id", "$products.product.supplier"), Accumulators.sum("count", 1)
                ),
                sort(
                        Sorts.descending("count")
                ),
                //lookup("Supplier", "_id", "_id","ordersSuppliers"),
                limit(n)
                //replaceRoot(Document.parse("{ $mergeObjects: [ { $arrayElemAt: [ '$ordersSuppliers', 0 ] } ] }"))
                )
        )) {
        	list.add(this.getSupplierById(new ObjectId(docu.get("_id").toString().substring(22,46))));
        }
        return list;
    }
    
/*CONSULTAS
  db.getCollection('Order').aggregate([
    {$match: {"actualStatus.status": "Send"}},
    {$unwind:"$products"},
    {$group:{_id:"$products.product.supplier", count:{$sum:1}}},
    {$sort:{"count":-1}},
    {$lookup: {
         from: "Supplier",
         localField: "_id",    // field in the orders collection
         foreignField: "_id",  // field in the items collection
         as: "fromItems"
      }},
    {$limit:4},
   { $replaceRoot: { newRoot: { $mergeObjects: [ { $arrayElemAt: [ "$fromItems", 0 ] } ] } } }
])




db.getCollection('Order').aggregate([
    {$unwind:"$products"},
    {$group:{_id:"$products.product", count:{$sum:1}}},
    {$sort:{"count":-1}},
    {$limit:4},
   { $replaceRoot: { newRoot: "$_id"} }
])
 */
    
}
