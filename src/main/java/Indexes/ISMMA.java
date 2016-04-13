package Indexes;
import Models.StockCompany;
import Tools.SMMA;
import Tools.Signal;

import java.awt.geom.Line2D;
import java.util.ArrayList;

//Smoothed Moving Average as index
public class ISMMA extends Index implements IStockIndex{

    private int period;
    private ArrayList<Double> close_price= new ArrayList<Double>();
    private ArrayList<Double> open_price= new ArrayList<Double>();

    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();

    public ISMMA(int period, ArrayList<StockCompany> list){
        super("SMMA");
        this.period = period;
        this.list = new ArrayList<StockCompany>(list);
        for(int i=0;i<list.size();i++)
        {
            this.close_price.add(list.get(i).getEndValue());
            this.open_price.add(list.get(i).getEndValue());

        }
    }

    public ArrayList<IndexResult> calculate()
    {
        ArrayList<Double> SMMA = new SMMA(period, close_price).calculate();
        ArrayList<IndexResult> results=new ArrayList<IndexResult>();
        double diff,diffprev,openprice;
        Signal result;

        //checking intersect between smma and c_price
        boolean intersect;
        for (int i=SMMA.size()-1;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,SMMA.get(i-1),i,SMMA.get(i),i-1,SMMA.get(i-1),i,SMMA.get(i));
            if(intersect)
            {
                diff = close_price.get(i) - SMMA.get(i);
                diffprev=close_price.get(i-1) - SMMA.get(i-1);

                if(i==SMMA.size()-1) openprice=0; //when signal appear in last day we can't take open price from future ;d
                else openprice=open_price.get(i);

                if(diffprev>0 && diff<0) {
                    result = Signal.sell;
                    results.add(new IndexResult(this.getName(), result,list.get(i).getDate(),close_price.get(i),openprice));
                }
                else if (diffprev<0 && diff>0) {
                    result = Signal.buy;
                    results.add(new IndexResult(this.getName(), result,list.get(i).getDate(),close_price.get(i),openprice));
                }
                //date ,signal status, name, close price, open price of next day
            }
        }
        return results;
    }

}