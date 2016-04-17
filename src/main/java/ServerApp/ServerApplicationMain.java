package ServerApp;

import Communication.RequestListener;
import Indexes.IndexManager;
import Indexes.IndexResult;
import Models.Bank;
import Models.IndexInformation;
import Tools.Signal;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;


/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException, UnknownHostException {

      //  RequestListener requestListener = new RequestListener(5001);
        IndexInformation indexInformation = new IndexInformation("ISMA", "LPP");
        indexInformation.addParameter("StartDate", "20100408");
        indexInformation.addParameter("endDate", "201511024");
        indexInformation.addParameter("period", "9");
        IndexManager indexManager = new IndexManager(indexInformation);
        String json = indexManager.calculateIndex();
        System.out.print(json.toString());
    }



}
