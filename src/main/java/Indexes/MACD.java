package Indexes;

import Tools.EMA;

import java.util.ArrayList;

//Moving Average Convergence / Divergence
public class MACD extends Index implements IStockIndex
{
    private int fastLength, slowLength,signalLength;
    private ArrayList<Double> c_price= new ArrayList<Double>();
    private ArrayList<Double> macd= new ArrayList<Double>(c_price.size()-slowLength+1);
    private ArrayList<Double> signal= new ArrayList<Double>(macd.size()-signalLength);

    public MACD(int fastLength, int slowLength, int signalLength, ArrayList<Double> c_price) {
        super("MACD");
        this.fastLength = fastLength;
        this.slowLength = slowLength;
        this.signalLength=signalLength;
        this.c_price = (ArrayList<Double>)c_price.clone();
    }

    public int calculate() {
        ArrayList<Double> fastEMA = new EMA(fastLength, c_price).calculate();
        ArrayList<Double> slowEMA = new EMA(slowLength, c_price).calculate();
        double avg=0,diff=0,tmp=0;
        double alpha=2/(signalLength+1);
        int result=0;

        for(int i=0; i< slowEMA.size();i++)
            macd.add(fastEMA.get(slowLength-fastLength+1)-slowEMA.get(0));

        for (int i=0; i<signalLength;i++)
            avg+=macd.get(i);
        signal.add(avg);

        for (int i=1; i<signal.size();i++)
            signal.add(macd.get(signalLength+i)*alpha+signal.get(i-1)*(1-alpha));

        //checking intersect
        for (int i=signalLength;i>0;i--) {
            diff = signal.get(i) - macd.get(i);
            if(tmp>0 && diff<0)
                result=1;
            if (tmp<0 && diff>0)
                result=2;
            tmp=diff;
        }
        return result; //0 nothing, 1 buy signal, 2 sell signal
    }


}