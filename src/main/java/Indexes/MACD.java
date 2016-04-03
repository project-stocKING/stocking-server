package Indexes;

import Tools.EMA;

import java.awt.geom.Line2D;
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

    public ArrayList<Result> calculate() {
        ArrayList<Double> fastEMA = new EMA(fastLength, c_price).calculate();
        ArrayList<Double> slowEMA = new EMA(slowLength, c_price).calculate();
        double avg=0,diff=0,diffprev=0;
        double alpha=2/(signalLength+1);
        int result=0;

        for(int i=0; i< slowEMA.size();i++)
            macd.add(fastEMA.get(slowLength-fastLength+1)-slowEMA.get(0));

        for (int i=0; i<signalLength;i++)
            avg+=macd.get(i);
        signal.add(avg);

        for (int i=1; i<signal.size();i++)
            signal.add(macd.get(signalLength+i)*alpha+signal.get(i-1)*(1-alpha));

        ArrayList<Result> results=new ArrayList<Result>();
        boolean intersect;
        //checking intersect
        for (int i=signalLength;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,macd.get(i-1),i,macd.get(i),i-1,signal.get(i-1),i,signal.get(i));
            if(intersect)
            {
                diff = macd.get(i) - signal.get(i);
                diffprev= macd.get(i-1) - signal.get(i-1);
                if(diffprev>0 && diff<0)
                    result=2; //sell
                else if (diffprev<0 && diff>0)
                    result=1; //buy
                results.add(new Result(signalLength-(i-1),result,this.getName())); //day (signalLength=actual day, so diff between them will be number of day from present) ,signal status, name
            }
        }
        return results;
    }


}