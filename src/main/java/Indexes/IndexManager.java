package Indexes;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beata on 2016-04-10.
 */
public class IndexManager {

    //private ArrayList<StockCompany> stockCompanyArrayList;
    private String indexName;
    private String json;
    private ArrayList<IndexResult> indexResultArrayList;
    private Map<String, IStockIndex> indexes;


    public IndexManager() {

    }

    public IndexManager(String indexName, String json) {

        this.indexName = indexName;
        this.json = json;
        this.indexResultArrayList = new ArrayList<IndexResult>();
        this.indexes = new HashMap<String, IStockIndex>();

       // indexes.put("MACD", new MACD(json));
        //TODO: umiescic w mapie pozostale wskazniki jak ich implementacja zostanie ujednolicona, na razie jeden do testow
    }

    public String calculateIndex() {
        String json = null;

        IStockIndex index = indexes.get(indexName);
      //  indexResultArrayList = index.calculate();

        try {
            json = JSONObject.valueToString(indexResultArrayList);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        System.out.println(json);

        return json;
    }
}
