package Parameters;

import Models.IndicatorInformation;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class StrategyParameters {

    List<IndicatorInformation> indicatorsWithParams;

    int signalsToPass;

    public StrategyParameters(){
        indicatorsWithParams = new LinkedList<IndicatorInformation>();
    }

    public void addIndicator(IndicatorInformation indicator){
        indicatorsWithParams.add(indicator);
    }

}
