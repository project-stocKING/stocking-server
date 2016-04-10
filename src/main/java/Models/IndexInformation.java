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
    private String startDate;
    private String endDate;
    private Map<String, String> parameters;

    public IndexInformation(){
        indexName = "";
        stockName = "";
        startDate = "";
        endDate = "";
        parameters = new HashMap<String, String>();
    }

    public IndexInformation(String indexName, String stockName,String startDate, String endDate)
    {
        this.indexName = indexName;
        this.stockName = stockName;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public void setEndDate(String nameEndDate){ this.stockName = endDate; }

    public String getIndexName(){
        return indexName;
    }

    public String getStockName(){
        return stockName;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public Map<String, String> getParameters(){
        return parameters;
    }

    @Override
    public String toString() {
        return
                "Name='" + indexName + '\'' +
                        ", parameters=" + parameters.toString();

    }


}
