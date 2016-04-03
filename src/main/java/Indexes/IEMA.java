package Indexes;
import Tools.EMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Exponential Moving Average as index
public class IEMA extends Index implements IStockIndex{

    private int fastLength, slowLength;
    private ArrayList<Double> c_price= new ArrayList<Double>();

    public IEMA(int fastLength, int slowLength)
    {
        super("EMA");
        this.fastLength = fastLength;
        this.slowLength = slowLength;
    }

    public ArrayList<Result> calculate()
    {
        ArrayList<Double> fastEMA = new EMA(fastLength, c_price).calculate();
        ArrayList<Double> slowEMA = new EMA(slowLength, c_price).calculate();
        double diff=0,diffprev=0;
        int result=0;
        ArrayList<Result> results=new ArrayList<Result>();
        boolean intersect;

        //checking intersect
        for (int i=slowLength;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,fastEMA.get(i-1),i,fastEMA.get(i),i-1,slowEMA.get(i-1),i,slowEMA.get(i));
            if(intersect)
            {
                diff = fastEMA.get(i) - slowEMA.get(i);
                diffprev=fastEMA.get(i-1) - slowEMA.get(i-1);
                if(diffprev>0 && diff<0)
                    result=2; //sell
                else if (diffprev<0 && diff>0)
                    result=1; //buy
                results.add(new Result(slowLength-(i-1),result,this.getName())); //day (slowLength=actual day, so diff between them will be number of day from present) ,signal status, name
            }
        }
        return results;
    }

}