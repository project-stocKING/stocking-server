package Communication;

import Models.StockCompany;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.util.JSONParseException;

/**
 * Created by mike on 26.03.16.
 */

/*
        Request Parser will receive a result from calculation
        and will parse it to JSON and then return it to listener
        which will send it back to application
 */
public class RequestParser
{
    private ObjectMapper objectMapper;  //Mapping the object to JSON
    private String jsonResponse;

    public RequestParser()
    {
        objectMapper = new ObjectMapper();
        jsonResponse = null;
    }

    //
    public String returnJSON(String toJSON)
    {
        try
        {
            jsonResponse = objectMapper.writeValueAsString(toJSON);
            System.out.println(jsonResponse);
        }
        catch (JSONParseException ex)
        {

        }
        catch (Exception ex)
        {

        }

        return jsonResponse;
    }
}
