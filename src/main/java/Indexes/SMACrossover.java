package Indexes;
import Models.StockCompany;
import Tools.SMA;
import Tools.Signal;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Map;


//Simple Moving Averages crossover
public class  SMACrossover extends Indicator implements IStockIndicator {

    private int short_period, long_period;
    private ArrayList<Double> close_price = new ArrayList<Double>();
    private ArrayList<Double> open_price= new ArrayList<Double>();
    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();

    public SMACrossover() {
        super("SMA crossover");
    }


    public ArrayList<IndicatorResult> calculate()
    {
        ArrayList<Double> short_SMA = new SMA(short_period, close_price).calculate();
        ArrayList<Double> long_SMA = new SMA(long_period, close_price).calculate();
        ArrayList<IndicatorResult> results=new ArrayList<IndicatorResult>();
        double diff,diffprev,openprice;
        Signal result;
        int diff_period=long_period-short_period; //difference between periods which is use to determine indexes of smas

        //checking intersect between shorter sma and longer sma
        boolean intersect;
        for (int i=long_SMA.size()-1;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,long_SMA.get(i-1),i,long_SMA.get(i),i-1,short_SMA.get(i-1+diff_period),i,short_SMA.get(i+diff_period));
            if(intersect)
            {
                diff = short_SMA.get(i+diff_period)-long_SMA.get(i);
                diffprev= short_SMA.get(i-1+diff_period)-long_SMA.get(i-1);

                if(i==long_SMA.size()-1) openprice=0; //when signal appear in last day we can't take open price from future ;d
                else openprice=open_price.get(i+long_period); //cause list of open price is longer about long_period-1

                if(diffprev>0 && diff<0) {
                    result = Signal.sell;
                    results.add(new IndicatorResult(this.getName(), result,list.get(i+long_period-1).getDate(),close_price.get(i+long_period-1),openprice));
                }
                else if (diffprev<0 && diff>0) {
                    result = Signal.buy;
                    results.add(new IndicatorResult(this.getName(), result,list.get(i+long_period-1).getDate(),close_price.get(i+long_period-1),openprice));
                }
                //date ,signal status, name, close price, open price of next day
            }
        }
        close_price.clear();
        open_price.clear();
        return results;
    }

    public void initialize(Map<String, Object> parameters) {
        this.short_period = Integer.parseInt(parameters.get("short_period").toString());
        this.long_period = Integer.parseInt(parameters.get("long_period").toString());
        this.list = (ArrayList<StockCompany>)parameters.get("stockList");

        for(int i=0;i<list.size();i++)
        {
            this.close_price.add(list.get(i).getEndValue());
            this.open_price.add(list.get(i).getStartValue());
        }
    }

}