package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Beata on 2016-03-17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockCompany {

    private String name;
    private Date date;
    private double startValue;
    private double maxValue;
    private double minValue;
    private double endValue;
    private double volume;

}
