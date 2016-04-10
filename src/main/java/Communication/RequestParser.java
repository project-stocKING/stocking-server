package Communication;

import Database.EndOfDayDatabaseConnection;
import Indexes.Index;
import Indexes.IndexManager;
import Models.IndexInformation;
import Models.IndexParameters;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSON;
import org.json.JSONException;
import org.json.JSONObject;

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
            out.writeBytes("Access-Control-Allow-Methods: GET, POST, PUT\r\n");
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
        jsonResponse = acquireJsonResponse(headers.get(0));
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
            char[] content = new char[contentLength];
            brinp.read(content, 0, contentLength);

            jsonResponse = new String(content);
            // System.out.println(jsonResponse);
            //jsonResponse = JSON.serialize(jsonResponse);
            jsonResponse = acquireJsonResponse(headers.get(0));
        }
        else
        {
            jsonResponse = "error";
            return;
        }

    }

    private String acquireJsonResponse(String toAcquire)
    {
        toAcquire = toAcquire.replace("/", "");       //remove /
        String[] lines = toAcquire.split(" ");
        toAcquire = lines[1];                         //value

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
                ArrayList<IndexParameters> indexes = new ArrayList<IndexParameters>();

                IndexParameters IEMA = new IndexParameters("IEMA");
                IEMA.addParameter("length", "integer");
                indexes.add(IEMA);

                IndexParameters ISMA = new IndexParameters("ISMA");
                ISMA.addParameter("period", "integer");
                indexes.add(ISMA);

                IndexParameters ISMMA = new IndexParameters("ISMMA");
                ISMMA.addParameter("length", "integer");
                indexes.add(ISMMA);

                IndexParameters IWMA = new IndexParameters("IWMA");
                IWMA.addParameter("length", "integer");
                indexes.add(IWMA);

                IndexParameters MACD = new IndexParameters("MACD");
                MACD.addParameter("longLength", "integer");
                MACD.addParameter("shortLength", "integer");
                MACD.addParameter("signalLength", "integer");
                indexes.add(MACD);

                try {
                    returnValue = objectMapper.writeValueAsString(indexes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if(httpMethod.equals("post"))
            {

                returnValue = "Hello World";

                IndexInformation information  = null;

                try {
                    information = objectMapper.readValue(jsonResponse, IndexInformation.class);
                    IndexManager manager = new IndexManager(information);
                    returnValue = manager.calculateIndex();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        else if(toAcquire.equals("ISMA"))
        {
            IndexInformation ISMA = new IndexInformation();
            ISMA.setIndexName("ISMA");
            ISMA.addParameter("period", "integer");

            try {
                returnValue = objectMapper.writeValueAsString(ISMA);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else
        {
            returnValue = "Error";
        }

        return returnValue;
    }


}