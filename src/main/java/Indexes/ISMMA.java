package Indexes;
import Models.StockCompany;
import Tools.SMMA;
import Tools.Signal;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Map;

//Smoothed Moving Average as index
public class ISMMA extends Index implements IStockIndex{

    private int period;
    private ArrayList<Double> close_price= new ArrayList<Double>();
    private ArrayList<Double> open_price= new ArrayList<Double>();

    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();

    public ISMMA()
    {
        super("SMMA");
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
            intersect= Line2D.linesIntersect(i-1,SMMA.get(i-1),i,SMMA.get(i),i-1,close_price.get(i-2+period),i,close_price.get(i-1+period));
            if(intersect)
            {
                diff = close_price.get(i) - SMMA.get(i);
                diffprev=close_price.get(i-1) - SMMA.get(i-1);

                if(i==SMMA.size()-1) openprice=0; //when signal appear in last day we can't take open price from future ;d
                else openprice=open_price.get(i+period);

                if(diffprev>0 && diff<0) {
                    result = Signal.sell;
                    results.add(new IndexResult(this.getName(), result,list.get(i+period-1).getDate(),close_price.get(i+period-1),openprice));
                }
                else if (diffprev<0 && diff>0) {
                    result = Signal.buy;
                    results.add(new IndexResult(this.getName(), result,list.get(i+period-1).getDate(),close_price.get(i+period-1),openprice));
                }
                //date ,signal status, name, close price, open price of next day
            }
        }
        close_price.clear();
        open_price.clear();

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