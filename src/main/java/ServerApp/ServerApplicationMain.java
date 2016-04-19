package ServerApp;

import Communication.RequestListener;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.text.ParseException;


/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException, UnknownHostException, FileNotFoundException {

        RequestListener requestListener = new RequestListener(5001);
        
    }

}
