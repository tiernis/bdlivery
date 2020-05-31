package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;
import com.mongodb.Block;
import com.mongodb.client.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonParseException;
import org.bson.json.JsonWriter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

public class DBliveryMongoRepository {

    @Autowired private MongoClient client;

    public void saveUser(User user){
        MongoCollection<User> collection = this.getDb().getCollection("User", User.class);
        collection.insertOne(user);
    }

    public void saveProduct(Product product){
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        collection.insertOne(product);
    }

    public Product getProduct(ObjectId id){
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        return collection.find(eq("_id", id)).first();
    }

    public void replaceProduct(Product product){
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        collection.replaceOne(eq("_id", product.getObjectId()), product);
    }
    
    public void saveSupplier(Supplier supplier){
        MongoCollection<Supplier> collection = this.getDb().getCollection("Supplier", Supplier.class);
        collection.insertOne(supplier);
    }

    public List<Product> getProductByName(String name) {
        MongoCollection<Product> collection = this.getDb().getCollection("Product", Product.class);
        ArrayList<Product> list = new ArrayList<>();
        for (Product dbObject : collection.find(eq("name", name)))
        {
            list.add(dbObject);
        }
        return list;
    }
    
    

    //MÉTODOS QUE NO ENTIENDO

    public void saveAssociation(PersistentObject source, PersistentObject destination, String associationName) {
        Association association = new Association(source.getObjectId(), destination.getObjectId());
        this.getDb()
                .getCollection(associationName, Association.class)
                .insertOne(association);
    }

    public MongoDatabase getDb() {
        return this.client.getDatabase("bd2_grupo" + this.getGroupNumber() );
    }

    private Integer getGroupNumber() { return 11; }

    public <T extends PersistentObject> List<T> getAssociatedObjects(
            PersistentObject source, Class<T> objectClass, String association, String destCollection) {
        AggregateIterable<T> iterable =
                this.getDb()
                        .getCollection(association, objectClass)
                        .aggregate(
                                Arrays.asList(
                                        match(eq("source", source.getObjectId())),
                                        lookup(destCollection, "destination", "_id", "_matches"),
                                        unwind("$_matches"),
                                        replaceRoot("$_matches")));
        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }


    //RETAZOS DE CODIGO QUE EN ALGUN MOMENTO USE

        /*public User getLastUserInserted(){
        MongoCollection<User> collection = this.getDb().getCollection("User", User.class);
        Block<User> printBlock = new Block<User>() {
            @Override
            public void apply(final User user) {
                System.out.println(user);
            }
        };

        User final_user = null;

        for (User user : collection.find().sort(new Document("_id", -1)).limit(1)) {
            final_user = user;
        }

        return final_user;
    }*/

            /*AggregateIterable<Product> product = collection.aggregate(Arrays.asList(Aggregates.match(Filters.eq("_id", id))));
        for (Product dbObject : product)
        {
            System.out.println(dbObject.getAllPrices().get(0).getPrice());
        }
        return new Product();*/
}
