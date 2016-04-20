package Models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michał on 10.04.16.
 */

@Getter
@Setter
public class IndicatorParameters {
    private String indicatorName;
    private Map<String, String> parameters;

    public IndicatorParameters(){
        this.indicatorName = "";
        initiateParameters();
    }

    public IndicatorParameters(String name){
        this.indicatorName = name;
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
                "Name='" + indicatorName + '\'' +
                        ", parameters=" + parameters.toString();
    }
}
