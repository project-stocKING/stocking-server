package Indexes;

import java.util.Date;

public class Result {
    private Date date;
    private boolean signal; //true - sell, false - buy
    private String name;

    public Result(Date date, boolean signal, String name){
        this.date=date;
        this.signal=signal;
        this.name=name;
    }
}
