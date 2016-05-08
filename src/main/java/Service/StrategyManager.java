package Service;

import Database.psql.PsqlConnector;
import Entities.StrategyInformation;

import java.sql.SQLException;
import java.util.List;

public class StrategyManager {

    private final PsqlConnector psql;

    public StrategyManager(){
        psql = new PsqlConnector();
    }

    public void calculateStrategies(){
        List<StrategyInformation> strategies = psql.findAllStrategies();

        //TODO: implement calculating strategies
        /**
         * For every strategy in database, retrive it's parameters and calculate
         * indicators that are used within it. Then check if required amount gave
         * signal to sell / buy
         *
         */

        for(StrategyInformation strategy : strategies){
            //TODO: do calculation for each strategy
        }

        try {
            psql.updateStrategies(strategies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
