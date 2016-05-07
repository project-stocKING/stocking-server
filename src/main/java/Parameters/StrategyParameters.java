package Parameters;

import Indexes.Indicator;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class StrategyParameters {

    List<IndicatorParameters> indicatorsWithParams;

    int signalsToPass;

    public StrategyParameters(){
        indicatorsWithParams = new LinkedList<IndicatorParameters>();
    }

    public void addIndicator(IndicatorParameters indicator){
        indicatorsWithParams.add(indicator);
    }

}
