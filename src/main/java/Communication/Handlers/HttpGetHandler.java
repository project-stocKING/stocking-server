package Communication.Handlers;

import Database.mongo.EndOfDayDatabaseConnection;
import Parameters.IndicatorParameters;
import Collections.IndicatorParametersCollection;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

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
            getIndicators();

        }
        else if(value.equals("calculateDailyStrategies")){

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

    private void getIndicators()
    {
        ArrayList<IndicatorParameters> indicators = IndicatorParametersCollection.getIndicators();

        try {
            response = objectMapper.writeValueAsString(indicators);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
