 package Service;

 import Collections.IndicatorCollection;
 import Database.mongo.EndOfDayDatabaseConnection;
 import Indexes.IStockIndicator;
 import Indexes.IndicatorResult;
 import Models.Bank;
 import Models.IndicatorInformation;
 import Entities.StockCompany;
 import org.json.JSONObject;
 import java.text.ParseException;
 import java.util.ArrayList;

 /**
 * Created by Beata on 2016-04-10.
 */
public class IndicatorManager {

    private ArrayList<StockCompany> stockCompanyArrayList;
    private ArrayList<IndicatorResult> indicatorResultArrayList;
    private IndicatorInformation indicatorInformation;
    private EndOfDayDatabaseConnection endOfDayDatabaseConnection;


    public IndicatorManager() {

    }

    public IndicatorManager(IndicatorInformation indicatorInformation) {

        this.indicatorInformation = indicatorInformation;
        this.indicatorResultArrayList = new ArrayList<IndicatorResult>();
        this.endOfDayDatabaseConnection = new EndOfDayDatabaseConnection();
        try {
            this.stockCompanyArrayList = endOfDayDatabaseConnection.findByDate(indicatorInformation.getParameters().get("startDate").toString(),indicatorInformation.getParameters().get("endDate").toString(), indicatorInformation.getStockName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public String calculateIndex() {

        String json = null;

        IStockIndicator indicator = IndicatorCollection.getIndex(indicatorInformation.getIndicatorName());
        indicatorInformation.addParameter("stockList", stockCompanyArrayList);

        indicator.initialize(indicatorInformation.getParameters());
        indicatorResultArrayList = indicator.calculate();
        Bank bank = new Bank();
        indicatorResultArrayList.get(indicatorResultArrayList.size()-1).setBudgetAmount(Double.parseDouble(indicatorInformation.getParameters().get("budget").toString()));
        bank.calculateBank(indicatorResultArrayList);

        try {
            json = JSONObject.valueToString(indicatorResultArrayList);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return json;


    }
}



