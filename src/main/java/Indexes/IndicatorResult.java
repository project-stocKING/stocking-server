package Indexes;

import Tools.Signal;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Beata on 2016-04-10.
 */
@Data
public class IndicatorResult implements Serializable {

    private String indicatorName;
    private Signal result;
    private Date date;
    private double endValue;
    private double nextDayOpenValue;
    private int sharesAmount;
    private double budgetAmount;

    public IndicatorResult(String indicatorName, Signal result, Date date, double endValue, double nextDayOpenValue) {
        this.indicatorName = indicatorName;
        this.result = result;
        this.date = date;
        this.endValue=endValue;
        this.nextDayOpenValue=nextDayOpenValue;
    }

    public String buyOrSell(){
        return result.getName();
    }


}
