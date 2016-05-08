package Service;

import Entities.StrategyInformation;
import Models.Strategy;
import Parameters.StrategyParameters;
import Tools.Signal;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class StrategyService {

    private ObjectMapper objectMapper;

    public Strategy StrategyInformationToStrategy(StrategyInformation si) throws IOException {
        Strategy strategy = new Strategy();

        objectMapper = new ObjectMapper();

        StrategyParameters strategyParameters = objectMapper.readValue(si.getContent(), StrategyParameters.class);

        strategy.setUser_id(si.getUser_id());
        strategy.setCreated_at(si.getCreated_at());
        strategy.setId(si.getId());
        strategy.setUpdated_at(si.getUpdated_at());
        strategy.setSignal(Signal.valueOf(si.getSignal()));
        strategy.setStrategyParameters(strategyParameters);

        return strategy;
    }

}
