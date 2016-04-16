package Communication.Handlers;

import Indexes.IndexManager;
import Models.IndexInformation;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by mike on 16.04.16.
 */
public class HttpPostHandler
{
    private String response;
    private ObjectMapper objectMapper;

    public HttpPostHandler()
    {
        objectMapper = new ObjectMapper();
    }

    public String generateResponse(String value, String requestBody)
    {
        response = "";

        if(value.equals("indexes"))
        {
            try
            {
                IndexInformation indexInformation = objectMapper.readValue(requestBody, IndexInformation.class);
                System.out.println(indexInformation);

                IndexManager indexManager = new IndexManager(indexInformation);
                response = indexManager.calculateIndex();
            }
            catch (IOException ex)
            {
                response = null;
                ex.printStackTrace();
            }
            catch (Exception ex)
            {
                response = null;
                ex.printStackTrace();
            }
        }

        else
        {
            response = "error";
        }

        return response;
    }
}
