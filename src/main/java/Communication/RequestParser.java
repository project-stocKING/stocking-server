package Communication;

import Models.StockCompany;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSONParseException;

import java.io.*;
import java.net.Socket;

/**
 * Created by mike on 26.03.16.
 */

/*
        Request Parser will receive a result from calculation
        and will parse it to JSON and then return it to listener
        which will send it back to application
 */
public class RequestParser extends Thread
{
    private Socket client;

    private ObjectMapper objectMapper;  //Mapping the object to JSON
    private String jsonResponse;

    private InputStream inp = null;
    private BufferedReader brinp = null;
    private DataOutputStream out = null;
    private PrintWriter pw;

    public RequestParser(Socket client)
    {
        objectMapper = new ObjectMapper();
        jsonResponse = null;
        this.client = client;
    }

    public void run()
    {
        initiateComponents();
        respond();
    }

    private void initiateComponents()
    {
        try
        {
            inp = client.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(client.getOutputStream());
        }
        catch (IOException e)
        {
            return;
        }
    }

    private void respond()
    {
        String line;
        while (true)
        {
            try
            {
                line = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT"))
                {
                    client.close();
                    return;
                }
                else
                {
                    jsonResponse = objectMapper.writeValueAsString(new StockCompany());
                    out.writeBytes(jsonResponse + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }


}
