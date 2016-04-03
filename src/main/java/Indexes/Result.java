package Indexes;

/**
 * Created by daniel on 03.04.16.
 */
public class Result {
    private int day;
    private int signal; //0 nothing, 1 buy signal, 2 sell signal
    private String name;

    public Result(int day, int signal, String name){
        this.day=day;
        this.signal=signal;
        this.name=name;
    }
}
