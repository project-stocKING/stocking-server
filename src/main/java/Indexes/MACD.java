package Indexes;

import Models.StockCompany;
import Tools.EMA;
import Tools.Signal;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Map;

//Moving Average Convergence / Divergence
public class MACD extends Index implements IStockIndex
{
    private int fastLength, slowLength,signalLength;
    private ArrayList<Double> close_price= new ArrayList<Double>();
    private ArrayList<Double> open_price= new ArrayList<Double>();
    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();
    private ArrayList<Double> macd= new ArrayList<Double>(close_price.size()-slowLength+1);
    private ArrayList<Double> signal= new ArrayList<Double>(macd.size()-signalLength);

    public MACD() {
        super("MACD");
    }

    public ArrayList<IndexResult> calculate() {
        ArrayList<Double> fastEMA = new EMA(fastLength, close_price).calculate();
        ArrayList<Double> slowEMA = new EMA(slowLength, close_price).calculate();
        ArrayList<IndexResult> results=new ArrayList<IndexResult>();
        double avg=0,diff,diffprev, alpha=2/(signalLength+1),openprice;
        Signal result;
        boolean intersect;

        //calculating macd values
        for(int i=0; i< slowEMA.size();i++)
            macd.add(fastEMA.get(slowLength-fastLength+1)-slowEMA.get(0));
        //macd=shorterEMA-longerEMA


        //first value of signal is sma
        for (int i=0; i<signalLength;i++)
            avg+=macd.get(i);
        signal.add(avg/signalLength);

        //calculating signal values
        for (int i=1; i<signal.size();i++)
            signal.add(macd.get(signalLength+i)*alpha+signal.get(i-1)*(1-alpha));
        //signal(n)=macd(n)*alpha+signal(n-1)*(1-alpha)


        //checking intersect
        for (int i=signalLength;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,macd.get(i-1),i,macd.get(i),i-1,signal.get(i-1),i,signal.get(i));
            if(intersect)
            {
                diff = macd.get(i) - signal.get(i);
                diffprev= macd.get(i-1) - signal.get(i-1);

                if(i==signalLength) openprice=0; //when signal appear in last day we can't take open price from future ;d
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

        close_price.clear();
        open_price.clear();

        return results;
    }

    public void initialize(Map<String, Object> parameters) {

        this.fastLength = Integer.parseInt(parameters.get("fastLength").toString());
        this.slowLength = Integer.parseInt(parameters.get("slowLength").toString());
        this.signalLength=Integer.parseInt(parameters.get("signalLength").toString());

        this.list = (ArrayList<StockCompany>)parameters.get("stockList");

        for(int i=0;i<list.size();i++)
        {
            this.close_price.add(list.get(i).getEndValue());
            this.open_price.add(list.get(i).getStartValue());
        }
    }


}