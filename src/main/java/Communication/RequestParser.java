package Communication;

import Communication.Handlers.HttpGetHandler;
import Database.EndOfDayDatabaseConnection;
import Indexes.IndexManager;
import Models.IndexInformation;
import Models.IndexParameters;
import Tools.IndexParametersCollection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mike on 26.03.16.
 */

public class RequestParser extends Thread
{
    private Socket client;

    private HttpGetHandler getHandler;  //For getting answer for HTTP GET requests

    private String headerValue;

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
        getHandler = new HttpGetHandler();

        try
        {
            inp = client.getInputStream();
            out = new DataOutputStream(client.getOutputStream());
            brinp = new BufferedReader(new InputStreamReader(inp));

            sendCORSHeaders();

        }
        catch (IOException e)
        {
            return;
        }
    }

    private void sendCORSHeaders() throws IOException
    {
        out.writeBytes("HTTP/1.1 200 \r\n");                  // Version & status code
        out.writeBytes("Access-Control-Allow-Origin: *\r\n");
        out.writeBytes("Content-Type: application/json\r\n"); // The type of data
        out.writeBytes("Connection: keep-alive\r\n");              // Will close stream
        out.writeBytes("Access-Control-Allow-Methods: GET, POST, PUT\r\n");
        out.writeBytes("Access-Control-Allow-Headers: *\r\n");
        out.writeBytes("\r\n");                               // End of headers
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
        getHeaderValue();

        if(line.contains("GET"))
        {
            jsonResponse = getHandler.generateResponse(headerValue);
        }
        else if(line.contains("POST"))
        {
            httpMethod = "post";
            HTTP_POST();
        }
    }

    private void getHeaderValue()
    {
        String line = headers.get(0);
        headerValue = line.replace("/", "");       //remove /
        String[] lines = headerValue.split(" ");
        headerValue = lines[1];
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

        String returnValue = "";
        if(headerValue.equals("companies"))
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

        else if(headerValue.equals("indexes"))
        {
            if(httpMethod.equals("get")) {

                Map<String, IndexParameters> indexes = IndexParametersCollection.getIndexes();

                try {
                    returnValue = objectMapper.writeValueAsString(indexes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if(httpMethod.equals("post"))
            {

                //returnValue = "Hello World";
                System.out.println(jsonResponse);


                try {
                    IndexInformation indexInformation = objectMapper.readValue(jsonResponse, IndexInformation.class);
                    System.out.println(indexInformation);

                    IndexManager indexManager = new IndexManager(indexInformation);
                    returnValue = indexManager.calculateIndex();


                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

        }
        else if(toAcquire.equals("ISMA"))
        {
            IndexParameters ISMA = new IndexParameters("ISMA");
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