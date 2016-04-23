package ServerApp;

import Communication.RequestListener;
import Indexes.IndicatorManager;
import Models.IndicatorInformation;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.text.ParseException;


/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException, UnknownHostException, FileNotFoundException {

        /*RequestListener requestListener;
        int port = 5001;
        if(args.length != 0)
        {
            port = Integer.parseInt(args[0]);
        }

        requestListener = new RequestListener(port);
        */
        IndicatorInformation indicatorInformation = new IndicatorInformation("SMACrossover", "KGHM");
        indicatorInformation.addParameter("StartDate", "20110408");
        indicatorInformation.addParameter("endDate", "20120824");
        indicatorInformation.addParameter("short_period", "50");
        indicatorInformation.addParameter("long_period", "200");
        indicatorInformation.addParameter("budget", "10000");
        IndicatorManager indicatorManager = new IndicatorManager(indicatorInformation);
        String json = indicatorManager.calculateIndex();
        System.out.print(json.toString());
    }

}
