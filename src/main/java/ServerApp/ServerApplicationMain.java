package ServerApp;

import Communication.RequestListener;
import Models.IndicatorInformation;
import Service.IndicatorManager;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException, UnknownHostException, FileNotFoundException, MessagingException, IOException {

        RequestListener requestListener;
        int port = 5001;
        if(args.length != 0)
        {
            port = Integer.parseInt(args[0]);
        }

        requestListener = new RequestListener(port);
        /*
        IndicatorInformation indicatorInformation = new IndicatorInformation("WMACrossover", "KGHM");
        indicatorInformation.addParameter("startDate", "20110201");
        indicatorInformation.addParameter("endDate", "20140601");
        indicatorInformation.addParameter("short_period", "45");
        indicatorInformation.addParameter("long_period", "100");
        indicatorInformation.addParameter("budget", "10000");
        IndicatorManager indicatorManager = new IndicatorManager(indicatorInformation);
        String json = indicatorManager.calculateIndex();
        System.out.print(json);
        */
    }
}

