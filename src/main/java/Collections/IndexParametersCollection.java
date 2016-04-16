package Collections;

import Models.IndexParameters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 16.04.16.
 */

public class IndexParametersCollection {

    private static IndexParametersCollection collection;

    private static Map<String, IndexParameters> indexes;

    private IndexParametersCollection()
    {
        initializeIndexes();
    }

    public static IndexParameters getIndexParameter(String indexName)
    {
        if(collection == null) collection = new IndexParametersCollection();

        return indexes.get(indexName);
    }

    public static Map<String, IndexParameters> getIndexes()
    {
        if(collection == null) collection = new IndexParametersCollection();
        return indexes;
    }

    //Really long function
    private void initializeIndexes()
    {
        indexes = new HashMap<String, IndexParameters>();

        IndexParameters IEMA = new IndexParameters("IEMA");
        IEMA.addParameter("period", "integer");
        indexes.put("IEMA", IEMA);

        IndexParameters ISMA = new IndexParameters("ISMA");
        ISMA.addParameter("period", "integer");
        indexes.put("ISMA", ISMA);

        IndexParameters ISMMA = new IndexParameters("ISMMA");
        ISMMA.addParameter("period", "integer");
        indexes.put("ISMMA", ISMA);

        IndexParameters IWMA = new IndexParameters("IWMA");
        IWMA.addParameter("period", "integer");
        indexes.put("IWMA", ISMA);

        IndexParameters MACD = new IndexParameters("MACD");
        MACD.addParameter("longLength", "integer");
        MACD.addParameter("shortLength", "integer");
        MACD.addParameter("signalLength", "integer");
        indexes.put("MACD", MACD);
    }





}
