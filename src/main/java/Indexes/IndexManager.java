 package Indexes;

 import Collections.IndexCollection;
 import Database.EndOfDayDatabaseConnection;
 import Models.Bank;
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
    private IndexInformation indexInformation;
    private EndOfDayDatabaseConnection endOfDayDatabaseConnection;


    public IndexManager() {

    }

    public IndexManager(IndexInformation indexInformation) {

        this.indexInformation = indexInformation;
        this.indexResultArrayList = new ArrayList<IndexResult>();
        this.endOfDayDatabaseConnection = new EndOfDayDatabaseConnection();
        try {
            this.stockCompanyArrayList = endOfDayDatabaseConnection.findByDate(indexInformation.getParameters().get("StartDate").toString(), indexInformation.getParameters().get("endDate").toString(), indexInformation.getStockName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public String calculateIndex() {

        String json = null;

        IStockIndex index = IndexCollection.getIndex(indexInformation.getIndexName());
        indexInformation.addParameter("stockList" , stockCompanyArrayList);

        index.initialize(indexInformation.getParameters());
        indexResultArrayList = index.calculate();

        double budget = Double.parseDouble(indexInformation.getParameters().get("budget").toString());

        Bank bank = new Bank();

        indexResultArrayList.get(indexResultArrayList.size()-1).setBudgetAmount(budget);

        bank.calculateBank(indexResultArrayList);
        for (IndexResult indexResult : indexResultArrayList){
            System.out.println(indexResult);
        }

        try {
            json = JSONObject.valueToString(indexResultArrayList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return json;
    }
}



