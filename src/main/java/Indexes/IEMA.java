package Indexes;
import Models.StockCompany;
import Tools.EMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Exponential Moving Average as index
public class IEMA extends Index implements IStockIndex{

    private int period;
    private ArrayList<Double> c_price= new ArrayList<Double>();
    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();

    public IEMA(int period, ArrayList<StockCompany> list)
    {
        super("EMA");
        this.period = period;
        this.list = new ArrayList<StockCompany>(list);
        for(int i=0;i<list.size();i++)
        {
            this.c_price.add(list.get(i).getEndValue());
        }
    }

    public ArrayList<Result> calculate()
    {
        ArrayList<Double> EMA = new EMA(period, c_price).calculate();
        ArrayList<Result> results=new ArrayList<Result>();
        double diff,diffprev;
        boolean result; //signal status true- sell, false- buy

        //checking intersect between ema and c_price
        boolean intersect;
        for (int i=EMA.size()-1;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,EMA.get(i-1),i,EMA.get(i),i-1,EMA.get(i-1),i,EMA.get(i));
            if(intersect)
            {
                diff = c_price.get(i) - EMA.get(i);
                diffprev=c_price.get(i-1) - EMA.get(i-1);
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