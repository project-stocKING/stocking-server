package Communication;

import Communication.Handlers.HttpGetHandler;
import Communication.Handlers.HttpPostHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by mike on 26.03.16.
 */

public class RequestParser extends Thread
{
    private Socket client;

    private HttpGetHandler getHandler;  //For getting answer for HTTP GET requests
    private HttpPostHandler postHandler;

    private String headerValue;

    private ObjectMapper objectMapper;  //Mapping the object to JSON
    private String jsonResponse;

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
        jsonResponse = "";
        headers = new ArrayList<String>();

        getHandler = new HttpGetHandler();
        postHandler = new HttpPostHandler();

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
            out.writeBytes(jsonResponse);
            client.close();
        }
        catch (IOException ex)
        {
            return;
        }

    }

    private void readHeaders() throws IOException
    {
        String line = brinp.readLine();
        System.out.println(line);

        while(!line.isEmpty())
        {

            headers.add(line);
            line = brinp.readLine();
        }
    }

    private void getOrPost() throws IOException
    {
        readHeaders();
        getHeaderValue();
        String line = headers.get(0);

        if(line.contains("GET"))
        {
            jsonResponse = getHandler.generateResponse(headerValue);
        }
        else if(line.contains("POST"))
        {
            String requestBody = readPostBody();
            jsonResponse = postHandler.generateResponse(headerValue, requestBody);
        }
    }

    private void getHeaderValue()
    {
        String line = headers.get(0);
        headerValue = line.replace("/", "");       //remove /
        String[] lines = headerValue.split(" ");
        headerValue = lines[1];
    }

    private String readPostBody() throws IOException
    {
        String body = null;
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

            body = new String(content);

        }

        return body;
    }

}