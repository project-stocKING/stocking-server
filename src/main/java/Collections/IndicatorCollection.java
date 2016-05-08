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

    private static Map<String, IStockIndicator> indicators;

    private IndicatorCollection(){
        initializeIndicators();
    }

    public static IStockIndicator getIndex(String indexName)
    {
        if(collection == null) collection = new IndicatorCollection();
        return indicators.get(indexName);
    }

    private static void initializeIndicators()
    {
        indicators = new HashMap<String, IStockIndicator>();

        indicators.put("MACD", new MACD());
        indicators.put("ISMA", new ISMA());
        indicators.put("IEMA", new IEMA());
        indicators.put("ISMMA", new ISMMA());
        indicators.put("IWMA", new IWMA());
        indicators.put("EMACrossover", new EMACrossover());
        indicators.put("SMACrossover", new SMACrossover());
        indicators.put("SMMACrossover", new SMMACrossover());
        indicators.put("WMACrossover", new WMACrossover());
        indicators.put("ATR", new ATR());
    }
}
