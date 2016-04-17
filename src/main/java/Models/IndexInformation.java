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
public class IndexInformation {
    private String indexName;
    private String stockName;
    private Map<String, Object> parameters;

    public IndexInformation(){
        indexName = "";
        stockName = "";
        parameters = new HashMap<String, Object>();
    }

    public IndexInformation(String indexName, String stockName)
    {
        this.indexName = indexName;
        this.stockName = stockName;
        parameters = new HashMap<String, Object>();
    }

    public void addParameter(String type, Object parameter ){
        parameters.put(type ,parameter);
    }

    public Map<String, Object> getParameters(){
        return parameters;
    }

    @Override
    public String toString() {
        return
                "Name='" + indexName + '\'' +
                        "StockName='" + stockName + '\'' +
                        ", parameters=" + parameters.toString();

    }


}
