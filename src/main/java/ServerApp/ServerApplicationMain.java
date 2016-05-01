package ServerApp;

import Communication.RequestListener;
import Database.psql.PsqlConnector;
import Models.StrategyInformation;
import Models.StrategyParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


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
