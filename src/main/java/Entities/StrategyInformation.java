package Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private String signal;

}
