package Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Created by Beata on 2016-03-17.
 */
@Data
@AllArgsConstructor
public class StockCompany {

    private String name;
    private Date date;
    private double startValue;
    private double maxValue;
    private double minValue;
    private double endValue;
    private double volume;

}
