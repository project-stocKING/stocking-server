package Indexes;

import Database.EndOfDayDatabaseConnection;
import Models.IndexInformation;
import Models.StockCompany;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beata on 2016-04-10.
 */
public class IndexManager {

    private ArrayList<StockCompany> stockCompanyArrayList;
    private ArrayList<IndexResult> indexResultArrayList;
    private Map<String, IStockIndex> indexes;
    private IndexInformation indexInformation;
    private EndOfDayDatabaseConnection endOfDayDatabaseConnection;


    public IndexManager() {

    }

    public IndexManager(IndexInformation indexInformation) {

        this.indexInformation = indexInformation;
        this.indexResultArrayList = new ArrayList<IndexResult>();
        this.indexes = new HashMap<String, IStockIndex>();
        this.endOfDayDatabaseConnection = new EndOfDayDatabaseConnection();
        try {
            this.stockCompanyArrayList = endOfDayDatabaseConnection.findByDate(indexInformation.getStartDate(), indexInformation.getEndDate(), indexInformation.getStockName());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        indexes.put("ISMA", new ISMA(Integer.parseInt(indexInformation.getParameters().get(0)),stockCompanyArrayList ));
    }

    public String calculateIndex() {
        String json = null;

        IStockIndex index = indexes.get(indexInformation.getIndexName());
        indexResultArrayList = index.calculate();

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
