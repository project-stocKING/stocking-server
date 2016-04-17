package Collections;

import Models.IndexParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mike on 16.04.16.
 */

public class IndexParametersCollection {

    private static IndexParametersCollection collection;

    private static ArrayList<IndexParameters> indexes;

    private IndexParametersCollection()
    {
        initializeIndexes();
    }

    public static ArrayList<IndexParameters> getIndexes()
    {
        if(collection == null) collection = new IndexParametersCollection();
        return indexes;
    }

    //Really long function
    private void initializeIndexes()
    {
        indexes = new ArrayList<IndexParameters>();

        IndexParameters IEMA = new IndexParameters("IEMA");
        IEMA.addParameter("period", "integer");
        indexes.add(IEMA);

        IndexParameters ISMA = new IndexParameters("ISMA");
        ISMA.addParameter("period", "integer");
        indexes.add(ISMA);

        IndexParameters ISMMA = new IndexParameters("ISMMA");
        ISMMA.addParameter("period", "integer");
        indexes.add(ISMMA);

        IndexParameters IWMA = new IndexParameters("IWMA");
        IWMA.addParameter("period", "integer");
        indexes.add(IWMA);

        IndexParameters MACD = new IndexParameters("MACD");
        MACD.addParameter("longLength", "integer");
        MACD.addParameter("shortLength", "integer");
        MACD.addParameter("signalLength", "integer");
        indexes.add(MACD);
    }





}
