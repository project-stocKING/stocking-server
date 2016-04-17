package Collections;

import Indexes.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 16.04.16.
 */
public class IndexCollection
{
    private static IndexCollection collection;

    private static Map<String, IStockIndex> indexes;

    private IndexCollection(){
        initializeIndexes();
    }

    public static IStockIndex getIndex(String indexName)
    {
        if(collection == null) collection = new IndexCollection();
        return indexes.get(indexName);
    }

    private static void initializeIndexes()
    {
        indexes = new HashMap<String, IStockIndex>();

        indexes.put("MACD", new MACD());
        indexes.put("ISMA", new ISMA());
        indexes.put("IEMA", new IEMA());
        indexes.put("ISMMA", new ISMMA());
        indexes.put("IWMA", new IWMA());
    }
}
