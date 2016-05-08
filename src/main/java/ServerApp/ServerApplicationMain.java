package ServerApp;

import Communication.RequestListener;
import Database.psql.IndicatorParamDatabase;
import Database.psql.StrategyDatabase;
import Entities.StrategyInformation;
import Indexes.Indicator;
import Models.IndicatorInformation;
import Parameters.IndicatorParameters;
import Parameters.StrategyParameters;
import Service.IndicatorManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Collections.IndicatorParametersCollection;


/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException, UnknownHostException, FileNotFoundException, MessagingException, IOException, SQLException {

        RequestListener requestListener;
        int port = 5001;
        if(args.length != 0)
        {
            port = Integer.parseInt(args[0]);
        }

        requestListener = new RequestListener(port);

        /*StrategyDatabase psql = new StrategyDatabase();
        IndicatorInformation ii = new IndicatorInformation();
        ii.setIndicatorName("IEMA");
        ii.setStockName("KGHM");
        ii.addParameter("period", "20");

        StrategyParameters sp = new StrategyParameters();
        sp.addIndicator(ii);
        sp.setSignalsToPass(1);

        ObjectMapper om = new ObjectMapper();
        String content = om.writeValueAsString(sp);
        StrategyInformation si = new StrategyInformation();
        si.setContent(content);
        si.setCreated_at(Calendar.getInstance().getTime());
        si.setSignal("buy");
        si.setUser_id(1);
        si.setUpdated_at(Calendar.getInstance().getTime());
        psql.insert(si);*/

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

