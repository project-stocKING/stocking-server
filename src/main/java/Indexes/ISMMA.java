package Indexes;
import Tools.SMMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Smoothed Moving Average as index
public class ISMMA extends Index implements IStockIndex{

    private int fastLength, slowLength;
    private ArrayList<Double> c_price= new ArrayList<Double>();

    public ISMMA(int fastLength, int slowLength)
    {
        super("SMMA");
        this.fastLength = fastLength;
        this.slowLength = slowLength;
    }

    public ArrayList<Result> calculate()
    {
        ArrayList<Double> fastSMMA = new SMMA(fastLength, c_price).calculate();
        ArrayList<Double> slowSMMA = new SMMA(slowLength, c_price).calculate();

        double diff=0,diffprev=0;
        int result=0;
        ArrayList<Result> results=new ArrayList<Result>();
        boolean intersect;

        //checking intersect
        for (int i=slowLength;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,fastSMMA.get(i-1),i,fastSMMA.get(i),i-1,slowSMMA.get(i-1),i,slowSMMA.get(i));
            if(intersect)
            {
                diff = fastSMMA.get(i) - slowSMMA.get(i);
                diffprev=fastSMMA.get(i-1) - slowSMMA.get(i-1);
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