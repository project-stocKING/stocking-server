package ServerApp;

import Communication.RequestListener;
import Database.psql.PsqlConnector;
import Entities.StrategyInformation;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.sql.SQLException;
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
