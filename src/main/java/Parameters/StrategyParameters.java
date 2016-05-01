package Parameters;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class StrategyParameters {
    List<String> indicators;
    int signalsToPass;

    public StrategyParameters(){
        indicators = new LinkedList<String>();
    }

    public void addIndicator(String name){
        indicators.add(name);
    }
}
