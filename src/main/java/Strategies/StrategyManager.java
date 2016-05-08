package Strategies;

import Database.EndOfDayDatabaseConnection;
import Indexes.IStockIndicator;
import Indexes.IndicatorResult;
import Models.StockCompany;
import Models.StrategyInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Created by Beata on 2016-04-24.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyManager {
    private ArrayList<StrategyInformation> strategyInformations;
    private ArrayList<StockCompany> stockCompanyArrayList;
    private ArrayList<IndicatorResult> indicatorResultArrayList;
    private EndOfDayDatabaseConnection endOfDayDatabaseConnection;

    public ArrayList<IndicatorResult> calculateStrategy() {
        for(int i = 0 ; i <strategyInformations.size() ; i++)
        {
            for(int j = 0 ; i <strategyInformations.get(i).getIndicators().size() ; j++)
            {
              //  stockCompanyArrayList =  odczyt z bazy rekordow ilosc = period
                IStockIndicator indicator = strategyInformations.get(i).getIndicators().get(j);
                strategyInformations.get(i).getParameters().get(j).put("companies" , stockCompanyArrayList);
                indicator.initialize(strategyInformations.get(i).getParameters().get(j));
                indicatorResultArrayList = indicator.calculate();
            }
        }
        return indicatorResultArrayList;
    }

}
