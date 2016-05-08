package Service;

import Database.psql.PsqlConnector;
import Database.psql.StrategyDatabase;
import Indexes.IndicatorResult;
import Models.IndicatorInformation;
import Models.Strategy;
import Parameters.StrategyParameters;
import Tools.Signal;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StrategyManager {

    private final StrategyDatabase psql;

    public StrategyManager(){
        psql = new StrategyDatabase();
    }

    public void calculateStrategies(){
        List<Strategy> strategies = psql.findAllStrategies();
        List<Strategy> updatedStrategies = new LinkedList<Strategy>();
        int counter = 0;

        for (Strategy strategy: strategies) {
            StrategyParameters strParam = strategy.getStrategyParameters();
            List<IndicatorInformation> indicatorsWithParams = strParam.getIndicatorsWithParams();
            for (IndicatorInformation indParam : indicatorsWithParams) {

                Date startDate = new Date();
                Date endDate = new Date();
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, -Integer.parseInt(indParam.getParameters().get("period").toString()));
                startDate.setTime(c.getTime().getTime());

                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                String startFormat = formatter.format(startDate);
                String endFormat = formatter.format(endDate);
                indParam.addParameter("endDate", endFormat);
                indParam.addParameter("startDate", startFormat);

                IndicatorManager indManager = new IndicatorManager(indParam);
                indManager.calculateIndex();
                IndicatorResult indResult = indManager.getTodaysResult();
                if(indResult != null) {
                    if(endFormat.equals(indResult.getDate())) {
                        counter++;
                    }
                }

            }
            if(counter >= strParam.getSignalsToPass()) {
                strategy.setUpdated_at(new Date());
                strategy.setSignal(Signal.buy);
                updatedStrategies.add(strategy);
            }
        }


        try {
            psql.updateStrategies(updatedStrategies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
