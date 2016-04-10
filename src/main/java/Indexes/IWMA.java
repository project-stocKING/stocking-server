package Indexes;
import Models.StockCompany;
import Tools.Signal;
import Tools.WMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Weighted Moving Average as index
public class IWMA extends Index implements IStockIndex{

    private int period;
    private ArrayList<Double> c_price= new ArrayList<Double>();
    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();

    public IWMA(int period, ArrayList<StockCompany> list)
    {
        super("WMA");
        this.period = period;
        this.list = new ArrayList<StockCompany>(list);
        for(int i=0;i<list.size();i++)
        {
            this.c_price.add(list.get(i).getEndValue());
        }
    }

    public ArrayList<IndexResult> calculate()
    {
        ArrayList<Double> WMA = new WMA(period, c_price).calculate();
        ArrayList<IndexResult> results=new ArrayList<IndexResult>();
        double diff,diffprev;
        Signal result;


        //checking intersect between wma and c_price
        boolean intersect;
        for (int i=WMA.size()-1;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,WMA.get(i-1),i,WMA.get(i),i-1,WMA.get(i-1),i,WMA.get(i));
            if(intersect)
            {
                diff = c_price.get(i) - WMA.get(i);
                diffprev=c_price.get(i-1) - WMA.get(i-1);
                if(diffprev>0 && diff<0) {
                    result = Signal.sell; //sell
                    results.add(new IndexResult(this.getName(), result,list.get(i).getDate()));
                }
                else if (diffprev<0 && diff>0) {
                    result = Signal.buy; //buy
                    results.add(new IndexResult(this.getName(), result,list.get(i).getDate()));
                }
                //date ,signal status, name
            }
        }

        return results;
    }

}