package Database;

import Models.StockCompany;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

        mongoClient = new MongoClient("156.17.41.238", 27017);
        database = mongoClient.getDatabase("endOfDay");
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

    public void  findDocByDate() throws ParseException{

        Document document;

        Date start = new SimpleDateFormat("yyyyMMdd", (Locale.ENGLISH)).parse("20140704"); /// data w formacie bazy
        Date end = new SimpleDateFormat("yyyyMMdd", (Locale.ENGLISH)).parse("20140917");
        BasicDBObject query = new BasicDBObject();
        query.put("Date: ", new BasicDBObject("$gt", start).append("$lte",end)); /// $gt - greater than $lte - less
        mongoCollection = database.getCollection("PZU");
        FindIterable<Document> dbObjects = mongoCollection.find(query);
        MongoCursor<Document> cursor =  dbObjects.iterator();
        while(cursor.hasNext())
        {
            document = cursor.next();
            System.out.println(document.toString());

        }


    }



}
