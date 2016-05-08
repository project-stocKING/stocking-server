package Models;

import Parameters.StrategyParameters;
import Tools.Signal;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Strategy {

    private int id;
    private StrategyParameters strategyParameters;
    private Date created_at;
    private Date updated_at;
    private int user_id;
    private Signal signal;

}
