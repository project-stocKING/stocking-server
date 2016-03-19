package ServerApp;

import Database.DatabaseConnection;

import java.net.UnknownHostException;

/**
 * Created by Beata on 2016-03-17.
 */
public class ServerApplicationMain {

    public static void main( String[] args ) {

        DatabaseConnection database = new DatabaseConnection();

        try {
            database.connectToDatabase();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        database.getCollection("PZU");
    }
}
