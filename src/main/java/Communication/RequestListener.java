package Communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        BufferedReader in = null;
        PrintWriter out = null;
        ServerSocket server = null;
        Socket client = null;

        try
        {
            server = new ServerSocket(portNumber);
            System.out.println("Server socket created");
        }
        catch (IOException ex)
        {
            System.out.println("Could not listen on port " + portNumber);
            System.exit(1);
        }

        try
        {
            client = server.accept();
            System.out.println("Client accepted");
        }
        catch (IOException ex)
        {
            System.out.println("Accept failed: 4321");
            System.exit(1);
        }

        try
        {
            in = new BufferedReader(new InputStreamReader(
                    client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(),
                    true);
        }
        catch (IOException e)
        {
            System.out.println("Read failed");
            System.exit(-1);
        }

        while (true)
        {
            try
            {
                String line = in.readLine();        //W linii zawarta byłaby nazwa spółki i dalej nazwy wskaźników
                if(line != null)
                {
                    out.println(line);
                }

            }
            catch (IOException e)
            {
                System.out.println("Read failed2");
                System.exit(-1);
            }
        }
    }
}
