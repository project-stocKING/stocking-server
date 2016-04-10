package Indexes;

import Models.StockCompany;
import Tools.EMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Moving Average Convergence / Divergence
public class MACD extends Index implements IStockIndex
{
    private int fastLength, slowLength,signalLength;
    private ArrayList<Double> c_price= new ArrayList<Double>();
    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();
    private ArrayList<Double> macd= new ArrayList<Double>(c_price.size()-slowLength+1);
    private ArrayList<Double> signal= new ArrayList<Double>(macd.size()-signalLength);

    public MACD(int fastLength, int slowLength, int signalLength, ArrayList<StockCompany> list) {
        super("MACD");
        this.fastLength = fastLength;
        this.slowLength = slowLength;
        this.signalLength=signalLength;
        for(int i=0;i<list.size();i++)
        {
            this.c_price.add(list.get(i).getEndValue());
        }
    }

    public ArrayList<Result> calculate() {
        ArrayList<Double> fastEMA = new EMA(fastLength, c_price).calculate();
        ArrayList<Double> slowEMA = new EMA(slowLength, c_price).calculate();
        ArrayList<Result> results=new ArrayList<Result>();
        double avg=0,diff,diffprev, alpha=2/(signalLength+1);
        boolean result=false, intersect;

        for(int i=0; i< slowEMA.size();i++)
            macd.add(fastEMA.get(slowLength-fastLength+1)-slowEMA.get(0));

        for (int i=0; i<signalLength;i++)
            avg+=macd.get(i);
        signal.add(avg);

        for (int i=1; i<signal.size();i++)
            signal.add(macd.get(signalLength+i)*alpha+signal.get(i-1)*(1-alpha));


        //checking intersect
        for (int i=signalLength;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,macd.get(i-1),i,macd.get(i),i-1,signal.get(i-1),i,signal.get(i));
            if(intersect)
            {
                diff = macd.get(i) - signal.get(i);
                diffprev= macd.get(i-1) - signal.get(i-1);

                if(diffprev>0 && diff<0) {
                    result = true; //sell
                    results.add(new Result(list.get(i).getDate(), result, this.getName()));
                }
                else if (diffprev<0 && diff>0) {
                    result = false; //buy
                    results.add(new Result(list.get(i).getDate(),result,this.getName()));
                }
                //date ,signal status, name
            }
        }
        return results;
    }


}