package ServerApp;

import Communication.RequestListener;

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
        }

        requestListener = new RequestListener(port);
    }



}
