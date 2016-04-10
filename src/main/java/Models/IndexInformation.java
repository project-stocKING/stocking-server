package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michał on 2016-04-06.
 */
public class IndexInformation {
    private String indexName;
    private String stockName;
    private Map<String, String> parameters;

    public IndexInformation(){
        indexName = "";
        stockName = "";
        parameters = new HashMap<String, String>();
    }

    public IndexInformation(String indexName, String stockName)
    {
        this.indexName = indexName;
        this.stockName = stockName;
        parameters = new HashMap<String, String>();
    }

    public void addParameter(String parameter, String type){
        parameters.put(parameter, type);
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

    public Map<String, String> getParameters(){
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
