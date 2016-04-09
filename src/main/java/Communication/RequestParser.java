package Communication;

import Database.EndOfDayDatabaseConnection;
import Models.IndexInformation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;

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

    private String httpMethod;
    private ArrayList<String> headers;

    private InputStream inp = null;
    private BufferedReader brinp = null;
    private DataOutputStream out = null;

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
        headers = new ArrayList<String>();


        try
        {
            inp = client.getInputStream();
            out = new DataOutputStream(client.getOutputStream());
            brinp = new BufferedReader(new InputStreamReader(inp));

            out.writeBytes("HTTP/1.1 200 \r\n");                  // Version & status code
            out.writeBytes("Access-Control-Allow-Origin: *\r\n");
            out.writeBytes("Content-Type: application/json\r\n"); // The type of data
            out.writeBytes("Connection: keep-alive\r\n");              // Will close stream
            out.writeBytes("\r\n");                               // End of headers

        }
        catch (IOException e)
        {
            return;
        }
    }

    private void respond()
    {
        try
        {
            getOrPost();
            out.writeBytes(jsonResponse + "\r\n");
            client.close();
        }
        catch (IOException ex)
        {
            return;
        }

    }

    private void getOrPost() throws IOException
    {
        String line  = brinp.readLine();
        headers.add(line);

        if(line.contains("GET"))
        {
            httpMethod = "get";
            HTTP_GET();
        }
        else if(line.contains("POST"))
        {
            httpMethod = "post";
            HTTP_POST();
        }


    }

    private void HTTP_GET()
    {
        jsonResponse = headers.get(0);
        jsonResponse = jsonResponse.replace("/", "");       //remove /

        String[] lines = jsonResponse.split(" ");

        jsonResponse = lines[1];                            //value
        jsonResponse = acquireJsonResponse(jsonResponse);

    }

    private void HTTP_POST() throws IOException
    {
        String line = headers.get(0);
        while(!line.isEmpty()){
            headers.add(line);
            line = brinp.readLine();
        }

        int contentLength = -1;

        for(String header : headers){
            if(header.contains("Content-Length")){
                String cl = header;
                String []cl2 = cl.split(" ");
                contentLength = Integer.parseInt(cl2[1]);
            }
        }

        if(contentLength != -1){
            char[] bodys = new char[contentLength];
            brinp.read(bodys, 0, contentLength);

            jsonResponse = new String(bodys);
            jsonResponse = JSON.serialize(jsonResponse);
        }
        else
        {
            jsonResponse = "error";
            return;
        }

    }


    private String acquireJsonResponse(String toAcquire)
    {

        String returnValue = "";
        if(toAcquire.equals("companies"))
        {
            if(httpMethod.equals("get"))
            {
                try
                {
                    EndOfDayDatabaseConnection dbCon = new EndOfDayDatabaseConnection();
                    returnValue = dbCon.companies();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }

        //TODO: change when there will be indexes database
        else if(toAcquire.equals("indexes"))
        {
            if(httpMethod.equals("get")) {
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if(httpMethod.equals("post"))
            {
                //TODO: send received JSON body to index manager
            }

        }
        else
        {
            returnValue = "Error";
        }

        return returnValue;
    }


}
