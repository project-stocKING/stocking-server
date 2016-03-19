package Database;

import Models.StockCompany;
import com.mongodb.Cursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Beata on 2016-03-17.
 */
public class DatabaseConnection {

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> mongoCollection;
    Document doc;
    ArrayList<StockCompany> stockCompanyArrayList = new ArrayList<StockCompany>();
    StockCompany stockCompany;

    public void connectToDatabase() throws UnknownHostException {

        mongoClient = new MongoClient();
        database = mongoClient.getDatabase("parserDB");
    }

    public ArrayList<StockCompany> getCollection(String name ) {
        mongoCollection = database.getCollection(name);

        FindIterable<Document> fDoc =  mongoCollection.find();
        MongoCursor<Document> cursor =  fDoc.iterator();
        while(cursor.hasNext())
        {
            doc = cursor.next();
            stockCompany = new StockCompany(doc.getString("Name: "), doc.getDate("Date: "), doc.getDouble("startValue: "),
                    doc.getDouble("maxValue: "), doc.getDouble("minValue: "), doc.getDouble("endValue: "), doc.getDouble("Volume: "));

            System.out.println(stockCompany.toString());
            stockCompanyArrayList.add(stockCompany);

        }
        System.out.println("size array " + stockCompanyArrayList.size());
        return stockCompanyArrayList;
    }
}
