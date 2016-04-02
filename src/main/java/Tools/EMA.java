package Tools;

import java.util.ArrayList;

//Exponential Moving Average
public class EMA
{
    private int N;  //period
    private double alpha; //multiplier
    private int length;
    private ArrayList<Double> c_price = new ArrayList<Double>(); //list of closing prices
    private ArrayList<Double> ema;

    public EMA(int N, ArrayList<Double> c_price)
    {
        this.N = N;
        alpha = 2.0 / (N + 1);
        length=c_price.size()-N+1;
        this.c_price = (ArrayList<Double>)c_price.clone();
        ema = new ArrayList<Double>(length);
    }

    public ArrayList<Double> calculate() //last value in list is the newest value
    {
        double avg=0;
        for(int i=0; i<N-1;i++)
            avg += c_price.get(i);
        avg/=N-1;
        ema.add(avg); //first value in list is a avg of (period-1) previous days
        for(int i=1; i<length;i++)
            ema.add((c_price.get(N-1+i)*alpha)+(ema.get(i-1)*(1-alpha))); //ema(n)=c_price(n) * multiplier + ema(n-1) * (1-multiplier)
        return ema;
    }



}