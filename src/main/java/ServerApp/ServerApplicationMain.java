package ServerApp;

import Communication.RequestListener;
import Database.EndOfDayDatabaseConnection;
import Indexes.IndicatorManager;
import Models.IndicatorInformation;
import Models.StockCompany;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;


/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException, UnknownHostException, FileNotFoundException {

        RequestListener requestListener;
        int port = 5001;
        if(args.length != 0)
        {
            port = Integer.parseInt(args[0]);
        }

        requestListener = new RequestListener(port);



    }

}
