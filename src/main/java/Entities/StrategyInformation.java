package Entities;

import Indexes.IStockIndicator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by mike on 21.04.16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyInformation {

    private int id;
    private String content;
    private Date created_at;
    private Date updated_at;
    private int user_id;



}
