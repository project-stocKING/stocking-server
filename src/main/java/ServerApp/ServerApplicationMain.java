package ServerApp;

import Communication.RequestListener;
import Indexes.IndexManager;
import Models.IndexInformation;

import java.net.UnknownHostException;
import java.text.ParseException;


/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) throws ParseException, UnknownHostException {

        RequestListener requestListener = new RequestListener(5001);
     /*   IndexInformation indexInformation = new IndexInformation("ISMA", "KGHM" , "20151109", "20160409");
        indexInformation.addParameter("10");
        IndexManager indexManager = new IndexManager(indexInformation);
        String json = indexManager.calculateIndex();
        System.out.println(json);*/

    }
}
