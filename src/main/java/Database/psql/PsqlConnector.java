package Database.psql;

import Entities.StrategyInformation;
import Models.Strategy;
import Service.StrategyService;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PsqlConnector {

    private Connection connection;
    private Statement stmt;
    private StrategyService strategyService;

    //TODO: Change to desired database when it will be ready
    public void connect() {
        connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager
                    .getConnection("jdbc:postgresql://at-server.iiar.pwr.edu.pl:5432/mczarnecki",
                            "mczarnecki", "Analiza"); //TODO: find a way to protect data (change to localhost?)

            connection.setAutoCommit(false);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public void insert(StrategyInformation strategyInformation) throws SQLException{

            connect();

            stmt = null;
            stmt = connection.createStatement();

            String sql = new StringBuilder().append("INSERT INTO strategies (content, created_at, updated_at, user_id, signal) ")
                    .append("VALUES (")
                    .append("\'")
                    .append(strategyInformation.getContent()).append("\'")
                    .append(", \'").append(strategyInformation.getCreated_at())
                    .append("\', \' ")
                    .append(strategyInformation.getUpdated_at()).append("\', ")
                    .append(strategyInformation.getUser_id())
                    .append(", \'").append(strategyInformation.getSignal()).append("\');").toString();

            stmt.execute(sql);
            stmt.close();
            connection.commit();
            connection.close();
    }

    public List<Strategy> findAllStrategies() {
        List<Strategy> strategies = new LinkedList<Strategy>();
        strategyService = new StrategyService();

        try {
            connect();
            Statement stmt = connection.createStatement();

            String sql = "SELECT * FROM strategies";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                StrategyInformation si = new StrategyInformation();
                si.setContent(rs.getString("content"));
                si.setCreated_at(rs.getDate("created_at"));
                si.setUpdated_at(rs.getDate("updated_at"));
                si.setUser_id(rs.getInt("user_id"));
                si.setId(rs.getInt("id"));
                si.setSignal(rs.getString("signal"));

                try {
                    Strategy strategy = strategyService.StrategyInformationToStrategy(si);
                    strategies.add(strategy);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            stmt.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return strategies;
    }

    //TODO: correct updating
    public void updateStrategies(List<Strategy> strategies) throws SQLException{

            connect();
            stmt = connection.createStatement();
            for(Strategy strategy : strategies){

                String sql = new StringBuilder().append("UPDATE strategies ")
                        .append("SET updated_at = ")
                        .append("\'").append(strategy.getUpdated_at()).append("\' ")
                        .append("WHERE id = ").append(strategy.getId())
                        .append(";").toString();

                stmt.execute(sql);
            }
            stmt.close();
            connection.commit();
            connection.close();

    }


}
