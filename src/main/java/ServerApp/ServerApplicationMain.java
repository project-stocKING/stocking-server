package ServerApp;

import Communication.RequestListener;
import Indexes.IndexManager;
import Indexes.IndexResult;
import Models.Bank;
import Models.IndexInformation;
import Tools.Signal;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;


/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException, UnknownHostException, FileNotFoundException {

       // RequestListener requestListener = new RequestListener(5001);
        IndexInformation indexInformation = new IndexInformation("ISMA", "KGHM");
        indexInformation.addParameter("StartDate", "20120408");
        indexInformation.addParameter("endDate", "20120824");
        indexInformation.addParameter("period", "45");
        IndexManager indexManager = new IndexManager(indexInformation);
        String json = indexManager.calculateIndex();
    }

}
