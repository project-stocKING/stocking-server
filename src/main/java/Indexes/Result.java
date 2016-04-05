package Indexes;

public class Result {
    private int day;
    private boolean signal; //true - sell, false - buy
    private String name;

    public Result(int day, boolean signal, String name){
        this.day=day;
        this.signal=signal;
        this.name=name;
    }
}
