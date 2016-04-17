package Communication.Handlers;

import Database.EndOfDayDatabaseConnection;
import Models.IndexParameters;
import Collections.IndexParametersCollection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mike on 16.04.16.
 */
public class HttpGetHandler
{
    private String response;
    private  ObjectMapper objectMapper;

    public HttpGetHandler()
    {
        objectMapper = new ObjectMapper();
    }

    public String generateResponse(String value)
    {
        response = "";
        if(value.equals("companies"))
        {
           getCompanies();
        }

        else if(value.equals("indexes"))
        {
          getIndexes();

        }
        else
        {
                response = "Wrong request";
        }

        return response;
    }

    private void getCompanies()
    {
        try
        {
            EndOfDayDatabaseConnection dbCon = new EndOfDayDatabaseConnection();
            response = dbCon.companies();
        }
        catch (Exception ex)
        {
            response = "Error";
            ex.printStackTrace();
        }
    }

    private void getIndexes()
    {
        ArrayList<IndexParameters> indexes = IndexParametersCollection.getIndexes();

        try {
            response = objectMapper.writeValueAsString(indexes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
