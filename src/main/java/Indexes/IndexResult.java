package Indexes;

import Tools.Signal;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Beata on 2016-04-10.
 */
public class IndexResult implements Serializable {

    private String indexName;
    private Signal result;
    private Date date;
    private double endValue;
    private double nextDayOpenValue;


    public IndexResult(String indexName, Signal result, Date date, double endValue, double nextDayOpenValue) {
        this.indexName = indexName;
        this.result = result;
        this.date = date;
        this.endValue=endValue;
        this.nextDayOpenValue=nextDayOpenValue;
    }

    public String getIndexName() {
        return indexName;
    }

    public Signal getResult() {
        return result;
    }

    public Date getDate() {
        return date;
    }

    public double getEndValue() {
        return endValue;
    }

    public double getNextDayOpenValue() {
        return nextDayOpenValue;
    }
}
