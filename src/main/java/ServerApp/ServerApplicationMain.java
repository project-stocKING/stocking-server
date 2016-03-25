package ServerApp;

import Communication.RequestListener;

import java.text.ParseException;

/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException {

        RequestListener requestListener = new RequestListener(5001);

        /*DatabaseConnection database = new DatabaseConnection();
        ArrayList<StockCompany> stockCompanyArrayList = new ArrayList<StockCompany>();

        try {
            database.connectToDatabase();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

       // stockCompanyArrayList = database.getCollection("PZU");

        database.findDocByDate();*/
    }



}
