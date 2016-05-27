package Communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Michał on 2016-03-24.
 */

public class RequestListener
{

    private int portNumber;

    public RequestListener(int portNumber)
    {
        this.portNumber = portNumber;
        listen();
    }

    private void listen()
    {

        ServerSocket server = null;
        Socket client = null;

        try
        {
            server = new ServerSocket(portNumber);
            System.out.println("Server socket created on port " + portNumber);
        }
        catch (IOException ex)
        {
            System.out.println("Could not listen on port " + portNumber);
            System.exit(1);
        }

        while(true)
        {
            try
            {
                client = server.accept();
                System.out.println("Client connected");

                new RequestParser(client).start();

            }
            catch(IOException ex)
            {
                System.out.println("I/O error" + ex);
            }
        }

    }
}
