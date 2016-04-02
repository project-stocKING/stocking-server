package Database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;

/**
 * Created by Beata on 2016-03-17.
 */

public class DatabaseConnection
{

    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoDatabase connectToDatabase(String dBname) throws UnknownHostException
    {
        mongoClient = new MongoClient("156.17.41.238", 27017);
        database = mongoClient.getDatabase(dBname);

        return database;
    }

}
