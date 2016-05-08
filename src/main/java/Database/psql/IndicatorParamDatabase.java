package Database.psql;

import java.sql.SQLException;
import java.util.List;

public class IndicatorParamDatabase extends PsqlConnector {

    public void addParam(String name, String params) throws SQLException {
        connect();

        stmt = null;
        stmt = connection.createStatement();

        String sql = new StringBuilder().append("INSERT INTO indicatorparameters (name, parameters) ")
                .append("VALUES(\'").append(name).append("\', ")
                .append("\'").append(params).append("\');").toString();

        stmt.execute(sql);
        stmt.close();
        connection.commit();
        connection.close();
    }

}
