package ServerApp;

import Database.EndOfDayDatabaseConnection;
import Models.StockCompany;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException {

       // RequestListener requestListener = new RequestListener(5001);

        EndOfDayDatabaseConnection endOfDayDatabaseConnection = new EndOfDayDatabaseConnection();
        ArrayList<StockCompany> stockCompanyArrayList;
        stockCompanyArrayList = endOfDayDatabaseConnection.findByDate("20160211", "20160401", "PZU");

        for (StockCompany st: stockCompanyArrayList) {
            System.out.println(st.toString());
        }
    }
}
