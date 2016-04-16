package Models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michał on 10.04.16.
 */

@Getter
@Setter
public class IndexParameters {
    private String indexName;
    private Map<String, String> parameters;

    public IndexParameters(){
        this.indexName = "";
        initiateParameters();
    }

    public IndexParameters(String name){
        this.indexName = name;
        initiateParameters();
    }

    private void initiateParameters(){
        parameters = new HashMap<String, String>();
        parameters.put("StartDate", "Date");
        parameters.put("endDate", "Date");
    }

    public Map<String, String> getParameters(){
        return parameters;
    }

    public void addParameter(String key, String type){
        parameters.put(key, type);
    }

    @Override
    public String toString(){
        return
                "Name='" + indexName + '\'' +
                        ", parameters=" + parameters.toString();
    }
}
