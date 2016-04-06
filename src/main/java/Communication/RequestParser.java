package Communication;

import Database.EndOfDayDatabaseConnection;
import Models.IndexInformation;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
    private ArrayList<String> request;

    private InputStream inp = null;
    private BufferedReader brinp = null;
    private DataOutputStream out = null;
    private PrintWriter pw;

    public RequestParser(Socket client)
    {
        this.client = client;
    }

    public void run()
    {
        initiateComponents();
        respond();
    }

    private void initiateComponents()
    {
        objectMapper = new ObjectMapper();
        jsonResponse = null;
        request = new ArrayList<String>();

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
        while (true)
        {
            try
            {
                String line  = brinp.readLine();
                if ((line == null) || line.equalsIgnoreCase(""))
                {
                    parse();
                    out.writeBytes(jsonResponse + "\r\n");
                    client.close();
                    return;
                }
                else
                {
                    request.add(line);
                    System.out.println(line);
                }
            }
            catch (IOException e)
            {
                //e.printStackTrace();
                return;
            }
            catch (Exception ex)
            {
                //ex.printStackTrace();
                return;
            }
        }
    }

    //TODO: add obtaining more than one value
    private void parse()
    {
        jsonResponse = request.get(0);                      //GET /value HTTP1.1
        jsonResponse = jsonResponse.replace("/", "");       //remove /

        String[] lines = jsonResponse.split(" ");

        jsonResponse = lines[1];                            //value
        jsonResponse = acquireJsonResponse(jsonResponse);
    }

    private String acquireJsonResponse(String toAcquire)
    {

        String returnValue = "";
        if(toAcquire.equals("companies"))
        {
            try
            {
                EndOfDayDatabaseConnection dbCon = new EndOfDayDatabaseConnection();
                returnValue = dbCon.companies();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

        //TODO: change when there will be indexes database
        else if(toAcquire.equals("indexes"))
        {
            ArrayList<IndexInformation> indexes = new ArrayList<IndexInformation>();

            IndexInformation IEMA = new IndexInformation("IEMA");
            IEMA.addParameter("length");
            indexes.add(IEMA);

            System.out.println(IEMA.toString());

            IndexInformation ISMA = new IndexInformation("ISMA");
            ISMA.addParameter("length");
            indexes.add(ISMA);

            IndexInformation ISMMA = new IndexInformation("ISMMA");
            ISMMA.addParameter("length");
            indexes.add(ISMMA);

            IndexInformation IWMA = new IndexInformation("IWMA");
            IWMA.addParameter("length");
            indexes.add(IWMA);

            IndexInformation MACD = new IndexInformation("MACD");
            MACD.addParameter("longLength");
            MACD.addParameter("shortLength");
            MACD.addParameter("signalLength");
            indexes.add(MACD);

            try {
                returnValue = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(indexes);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            //IEMA, ISMA, ISMMA, IWMA - length,
            //MACD - int fastLength, int slowLength, int signalLength,
        }
        else
        {
            returnValue = "Error";
        }
        return returnValue;
    }


}
