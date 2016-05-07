package Communication.Handlers;

import Service.IndicatorManager;
import Models.IndicatorInformation;
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
                System.out.println(requestBody);
                IndicatorInformation indicatorInformation = objectMapper.readValue(requestBody, IndicatorInformation.class);
                IndicatorManager indicatorManager = new IndicatorManager(indicatorInformation);
                response = indicatorManager.calculateIndex();
            }
            catch (IOException ex)
            {
                response = "error";
                ex.printStackTrace();
            }
            catch (Exception ex)
            {
                response = "error";
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
