package Indexes;
import Tools.SMMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Smoothed Moving Average as index
public class ISMMA extends Index implements IStockIndex{

    private int length;
    private ArrayList<Double> c_price= new ArrayList<Double>();

    public ISMMA(int length, ArrayList<Double> c_price)
    {
        super("SMMA");
        this.length = length;
        this.c_price = new ArrayList<Double>(c_price);
    }

    public ArrayList<Result> calculate()
    {
        ArrayList<Double> SMMA = new SMMA(length, c_price).calculate();
        ArrayList<Result> results=new ArrayList<Result>();
        double diff,diffprev;
        boolean result; //signal status true- sell, false- buy

        //checking intersect between smma and c_price
        boolean intersect;
        for (int i=length;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,SMMA.get(i-1),i,SMMA.get(i),i-1,SMMA.get(i-1),i,SMMA.get(i));
            if(intersect)
            {
                diff = c_price.get(i) - SMMA.get(i);
                diffprev=c_price.get(i-1) - SMMA.get(i-1);
                if(diffprev>0 && diff<0) {
                    result = true; //sell
                    results.add(new Result(length - (i - 1), result, this.getName()));
                }
                else if (diffprev<0 && diff>0) {
                    result = false; //buy
                    results.add(new Result(length-(i-1),result,this.getName()));
                }
                //day (slowLength=actual day, so diff between them will be number of day from present) ,signal status, name
            }
        }
        return results;
    }

}