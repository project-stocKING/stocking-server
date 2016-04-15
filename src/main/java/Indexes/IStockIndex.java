package Indexes;


import java.util.ArrayList;
import java.util.Map;

public interface IStockIndex
{
    ArrayList<IndexResult> calculate();
    void initialize(Map<String, Object> parameters);
}
