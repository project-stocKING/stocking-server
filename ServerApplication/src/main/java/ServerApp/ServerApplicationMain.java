package ServerApp;

import Database.DatabaseConnection;
import Models.StockCompany;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) {

        DatabaseConnection database = new DatabaseConnection();
        ArrayList<StockCompany> stockCompanyArrayList = new ArrayList<StockCompany>();

        try {
            database.connectToDatabase();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        stockCompanyArrayList = database.getCollection("PZU");
    }
}
