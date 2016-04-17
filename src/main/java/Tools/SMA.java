package Tools;

import java.util.ArrayList;

//Simple Moving Average
public class SMA {
    private int N;  //period
    private int length;
    private ArrayList<Double> c_price = new ArrayList<Double>(); //list of closing prices
    private ArrayList<Double> sma; //list of sma values

    public SMA(int N, ArrayList<Double> c_price)
    {
        this.N = N; //period
        length=c_price.size()-N+1;
        this.c_price = new ArrayList<Double>(c_price);
        sma=new ArrayList<Double>(length);
    }

    public ArrayList<Double> calculate()
    {
        double sum=0;
        for (int i = 0; i < N; i++)
        sum += c_price.get(i);

        sma.add(sum/N);

        for (int i= 1; i < length; i++)
            sma.add(sma.get(i-1) + c_price.get(i-1+N)/N - c_price.get(i-1)/N);
        return sma;
    }
}
