package Indexes;


import java.util.ArrayList;
import java.util.Map;

public interface IStockIndicator
{
    ArrayList<IndicatorResult> calculate();
    void initialize(Map<String, Object> parameters);
}
