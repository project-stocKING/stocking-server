package Indexes;
import Models.StockCompany;
import Tools.SMMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Smoothed Moving Average as index
public class ISMMA extends Index implements IStockIndex{

    private int period;
    private ArrayList<Double> c_price= new ArrayList<Double>();
    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();

    public ISMMA(int period, ArrayList<StockCompany> list){
        super("SMMA");
        this.period = period;
        this.list = new ArrayList<StockCompany>(list);
        for(int i=0;i<list.size();i++)
        {
            this.c_price.add(list.get(i).getEndValue());
        }
    }

    public ArrayList<Result> calculate()
    {
        ArrayList<Double> SMMA = new SMMA(period, c_price).calculate();
        ArrayList<Result> results=new ArrayList<Result>();
        double diff,diffprev;
        boolean result; //signal status true- sell, false- buy

        //checking intersect between smma and c_price
        boolean intersect;
        for (int i=SMMA.size()-1;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,SMMA.get(i-1),i,SMMA.get(i),i-1,SMMA.get(i-1),i,SMMA.get(i));
            if(intersect)
            {
                diff = c_price.get(i) - SMMA.get(i);
                diffprev=c_price.get(i-1) - SMMA.get(i-1);
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