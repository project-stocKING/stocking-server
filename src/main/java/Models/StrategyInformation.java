package Models;

import Indexes.IStockIndicator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by mike on 21.04.16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyInformation {

    private ArrayList<IStockIndicator> indicators;
    private ArrayList<Map<String, Object>> parameters;
    private String stockCompany;

    public void addIndicator(IStockIndicator indicator)
    {
        indicators.add(indicator);
    }

    public void addParameters(Map<String, Object> param) {parameters.add(param); }
}
