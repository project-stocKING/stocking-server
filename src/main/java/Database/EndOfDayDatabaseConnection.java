package Database;

import Models.StockCompany;
import com.mongodb.BasicDBObject;
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
 * Created by Beata on 2016-04-02.
 */
public class EndOfDayDatabaseConnection {

    private ArrayList<StockCompany> stockCompanyArrayList;
    private DatabaseConnection databaseConnection;
    private MongoDatabase database;
    private MongoCollection<Document> mongoCollection;
    private Document doc;
    private FindIterable<Document> fDoc;
    private MongoCursor<Document> cursor;
    private StockCompany stockCompany;

    public EndOfDayDatabaseConnection() {
        stockCompanyArrayList = new ArrayList<StockCompany>();
        databaseConnection = new DatabaseConnection();
        try {
            database = databaseConnection.connectToDatabase("endOfDay");
            System.out.println("Success connection!");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<StockCompany> getCollection(String name, BasicDBObject query) {
        mongoCollection = database.getCollection(name);

        if(query != null)
            fDoc = mongoCollection.find(query);
            else fDoc = mongoCollection.find();

        cursor = fDoc.iterator();
        while (cursor.hasNext()) {
            doc = cursor.next();
            stockCompany = new StockCompany(doc.getString("Name: "), doc.getDate("Date: "), doc.getDouble("startValue: "),
                    doc.getDouble("maxValue: "), doc.getDouble("minValue: "), doc.getDouble("endValue: "), doc.getDouble("Volume: "));
            stockCompanyArrayList.add(stockCompany);

        }
        System.out.println("size array " + stockCompanyArrayList.size());
        return stockCompanyArrayList;
    }

    public ArrayList<StockCompany> findByDate(String start, String end, String name) throws ParseException {
        Date startDate = new SimpleDateFormat("yyyyMMdd", (Locale.ENGLISH)).parse(start); /// data w formacie bazy np 20140913
        Date endDate = new SimpleDateFormat("yyyyMMdd", (Locale.ENGLISH)).parse(end);
        BasicDBObject query = new BasicDBObject();
        query.put("Date: ", new BasicDBObject("$gt", startDate).append("$lte", endDate)); /// $gt - greater than $lte - less

        return getCollection(name, query);
    }
}