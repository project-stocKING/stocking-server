package Database.psql;

import Entities.StrategyInformation;
import Models.Strategy;
import Service.StrategyService;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PsqlConnector
{
    protected Connection connection;
    protected Statement stmt;
    protected StrategyService strategyService;

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




}
