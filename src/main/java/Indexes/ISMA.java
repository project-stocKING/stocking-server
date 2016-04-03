package Indexes;
import Tools.SMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Simple Moving Average as index
public class ISMA extends Index implements IStockIndex{

    private int fastLength, slowLength;
    private ArrayList<Double> c_price= new ArrayList<Double>();

    public ISMA(int fastLength, int slowLength)
    {
        super("SMA");
        this.fastLength = fastLength;
        this.slowLength = slowLength;
    }

    public ArrayList<Result> calculate()
    {
        ArrayList<Double> fastSMA = new SMA(fastLength, c_price).calculate();
        ArrayList<Double> slowSMA = new SMA(slowLength, c_price).calculate();
        double diff=0,diffprev=0;
        int result=0;
        ArrayList<Result> results=new ArrayList<Result>();
        boolean intersect;

        //checking intersect
        for (int i=slowLength;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,fastSMA.get(i-1),i,fastSMA.get(i),i-1,slowSMA.get(i-1),i,slowSMA.get(i));
            if(intersect)
            {
                diff = fastSMA.get(i) - slowSMA.get(i);
                diffprev=fastSMA.get(i-1) - slowSMA.get(i-1);
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