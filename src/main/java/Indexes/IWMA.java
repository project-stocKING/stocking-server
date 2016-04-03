package Indexes;
import Tools.WMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Weighted Moving Average as index
public class IWMA extends Index implements IStockIndex{

    private int fastLength, slowLength;
    private ArrayList<Double> c_price= new ArrayList<Double>();

    public IWMA(int fastLength, int slowLength)
    {
        super("WMA");
        this.fastLength = fastLength;
        this.slowLength = slowLength;
    }

    public ArrayList<Result> calculate()
    {
        ArrayList<Double> fastWMA = new WMA(fastLength, c_price).calculate();
        ArrayList<Double> slowWMA = new WMA(slowLength, c_price).calculate();
        double diff=0,diffprev=0;
        int result=0;

        ArrayList<Result> results=new ArrayList<Result>();
        boolean intersect;
        //checking intersect
        for (int i=slowLength;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,fastWMA.get(i-1),i,fastWMA.get(i),i-1,slowWMA.get(i-1),i,slowWMA.get(i));
            if(intersect)
            {
                diff = fastWMA.get(i) - slowWMA.get(i);
                diffprev=fastWMA.get(i-1) - slowWMA.get(i-1);
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