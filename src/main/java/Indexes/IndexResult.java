package Indexes;

import Models.Bank;
import Tools.Signal;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Beata on 2016-04-10.
 */
@Data
public class IndexResult implements Serializable {

    private String indexName;
    private Signal result;
    private Date date;
    private double endValue;
    private double nextDayOpenValue;
    private int sharesAmount;
    private double budgetAmount;

    public IndexResult(String indexName, Signal result, Date date, double endValue, double nextDayOpenValue) {
        this.indexName = indexName;
        this.result = result;
        this.date = date;
        this.endValue=endValue;
        this.nextDayOpenValue=nextDayOpenValue;
    }


}
