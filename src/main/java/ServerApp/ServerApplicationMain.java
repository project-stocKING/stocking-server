package ServerApp;

import Communication.RequestListener;
import Database.psql.PsqlConnector;
import Entities.StrategyInformation;
import Models.Strategy;
import Parameters.IndicatorParameters;
import Parameters.StrategyParameters;
import Service.StrategyService;
import Tools.Signal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static void main( String[] args ) throws ParseException, IOException {

        /*RequestListener requestListener;
        int port = 5001;
        if(args.length != 0)
        {
            port = Integer.parseInt(args[0]);
        }

        requestListener = new RequestListener(port);*/

      //  PsqlConnector psql = new PsqlConnector();
        //List<Strategy> strategies = psql.findAllStrategies();
        //System.out.println(strategies);




        /*PsqlConnector psql = new PsqlConnector();
        StrategyParameters sp = new StrategyParameters();
        IndicatorParameters ip = new IndicatorParameters();
        ip.setIndicatorName("ISA");
        ip.addParameter("StartDate", "01012016");
        ip.addParameter("EndDate", "01022016");
        ip.addParameter("perion", "20");
        sp.addIndicator(ip);
        sp.setSignalsToPass(1);

        ObjectMapper om = new ObjectMapper();

        StrategyInformation si = new StrategyInformation();
        try {
            String content = om.writeValueAsString(sp);
            si.setContent(content);
            si.setId(1);
            si.setCreated_at(Calendar.getInstance().getTime());
            si.setUpdated_at(Calendar.getInstance().getTime());
            si.setUser_id(1);
            si.setSignal("Buy");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            psql.insert(si);
            List<StrategyInformation> str = psql.findAllStrategies();
            System.out.println(str);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }



}
