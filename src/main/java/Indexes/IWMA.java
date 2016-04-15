package Indexes;
import Models.StockCompany;
import Tools.Signal;
import Tools.WMA;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Map;

//Weighted Moving Average as index
public class IWMA extends Index implements IStockIndex{

    private int period;
    private ArrayList<Double> close_price = new ArrayList<Double>();
    private ArrayList<Double> open_price= new ArrayList<Double>();
    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();

    public IWMA()
    {
        super("WMA");
    }

    public ArrayList<IndexResult> calculate()
    {
        ArrayList<Double> WMA = new WMA(period, close_price).calculate();
        ArrayList<IndexResult> results=new ArrayList<IndexResult>();
        double diff,diffprev,openprice;
        Signal result;


        //checking intersect between wma and close_price
        boolean intersect;
        for (int i=WMA.size()-1;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,WMA.get(i-1),i,WMA.get(i),i-1,WMA.get(i-1),i,WMA.get(i));
            if(intersect)
            {
                diff = close_price.get(i) - WMA.get(i);
                diffprev= close_price.get(i-1) - WMA.get(i-1);

                if(i==WMA.size()-1) openprice=0; //when signal appear in last day we can't take open price from future ;d
                else openprice=open_price.get(i+1);

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

    public void initialize(Map<String, Object> parameters) {

        this.period = Integer.parseInt(parameters.get("period").toString());
        this.list = (ArrayList<StockCompany>)parameters.get("stockList");

        for(int i=0;i<list.size();i++)
        {
            this.close_price.add(list.get(i).getEndValue());
            this.open_price.add(list.get(i).getStartValue());
        }
    }

}