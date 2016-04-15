package Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michał on 2016-04-06.
 */
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

    public void setIndexName(String name){
        this.indexName = name;
    }

    public void setStockName(String name){
        this.stockName = name;
    }

    public String getIndexName(){
        return indexName;
    }

    public String getStockName(){
        return stockName;
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
