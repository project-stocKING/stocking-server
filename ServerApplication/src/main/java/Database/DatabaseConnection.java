package Database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;

/**
 * Created by Beata on 2016-03-17.
 */
public class DatabaseConnection {

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> mongoCollection;
    Document doc;

    public void connectToDatabase() throws UnknownHostException {

        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("parserDB");
    }

    public void getCollection(String name ) {
        mongoCollection = database.getCollection(name);
        doc = mongoCollection.find().first();

        System.out.println("doc: " + doc.toString());
    }
}
