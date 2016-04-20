package Collections;

import Indexes.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 16.04.16.
 */
public class IndicatorCollection
{
    private static IndicatorCollection collection;

    private static Map<String, IStockIndicator> indexes;

    private IndicatorCollection(){
        initializeIndicators();
    }

    public static IStockIndicator getIndex(String indexName)
    {
        if(collection == null) collection = new IndicatorCollection();
        return indexes.get(indexName);
    }

    private static void initializeIndicators()
    {
        indexes = new HashMap<String, IStockIndicator>();

        indexes.put("MACD", new MACD());
        indexes.put("ISMA", new ISMA());
        indexes.put("IEMA", new IEMA());
        indexes.put("ISMMA", new ISMMA());
        indexes.put("IWMA", new IWMA());
    }
}
