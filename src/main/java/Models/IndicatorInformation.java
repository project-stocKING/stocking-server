package Models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michał on 2016-04-06.
 */

@Getter
@Setter
public class IndicatorInformation {
    private String indicatorName;
    private String stockName;
    private Map<String, Object> parameters;

    public IndicatorInformation(){
        indicatorName = "";
        stockName = "";
        parameters = new HashMap<String, Object>();
    }

    public IndicatorInformation(String indicatorName, String stockName)
    {
        this.indicatorName = indicatorName;
        this.stockName = stockName;
        parameters = new HashMap<String, Object>();
    }

    public void addParameter(String type, Object parameter ){
        parameters.put(type ,parameter);
    }
    public void updateParam (String key, Object value) {
        parameters.replace(key, value);
    }

    public Map<String, Object> getParameters(){
        return parameters;
    }

    @Override
    public String toString() {
        return
                "Name='" + indicatorName + '\'' +
                        "StockName='" + stockName + '\'' +
                        ", parameters=" + parameters.toString();

    }


}
