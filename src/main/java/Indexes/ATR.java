package Indexes;

import Entities.StockCompany;
import Tools.Signal;
import java.util.ArrayList;
import java.util.Map;


//Average True Range
public class  ATR extends Indicator implements IStockIndicator {

    private int period, ATR_period; //ATR_period is calculation periods for the ATR
    private ArrayList<Double> close_price = new ArrayList<Double>();
    private ArrayList<Double> open_price= new ArrayList<Double>();
    private ArrayList<Double> max_price = new ArrayList<Double>();
    private ArrayList<Double> min_price= new ArrayList<Double>();
    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();

    public ATR(){
        super("ATR");
    }


    public ArrayList<IndicatorResult> calculate()
    {
        ArrayList<Double> TR=new ArrayList<Double>(list.size()); //True Range
        ArrayList<Double> ATR=new ArrayList<Double>(); //Average True Range
        ArrayList<IndicatorResult> results=new ArrayList<IndicatorResult>();
        double avg=0, max,min,openprice;
        int maxIndex=0, minIndex=0;

        TR.add(max_price.get(0)-min_price.get(0)); //first value of TR is max-min of 1 day

        for(int i=1; i<list.size();i++)
            TR.add(Math.max(Math.abs(max_price.get(i)-min_price.get(i)),Math.max(Math.abs(max_price.get(i)-close_price.get(i-1)),Math.abs(min_price.get(i)-close_price.get(i-1)))));
            //max of abs(current high - current low), abs(current high - previous close), abs(current low - previous close)

        for(int i=0;i<period;i++)
            avg+=TR.get(i);

        ATR.add(avg/period); //first value of ATR is period length average of TR

        max=ATR.get(0);
        min=max;

        //other values are given by ATR(t)=ATR(t-1)*(period-1)+TR(t)/period
        for(int i=1,j=1; i<list.size()-period+1;i++,j++) //period +1 cause cause ATR'll be shorter about period-1
        {
            ATR.add(ATR.get(i-1)*(period-1)+TR.get(i)/period);

            if(ATR.get(i)>max) //sell
            {
                max=ATR.get(i);
                maxIndex=i;
            }

            if(ATR.get(i)<min) //buy
            {
                min=ATR.get(i);
                minIndex=i;
            }

            if(j==ATR_period) //cause we check only ATR_period values to find signal
            {
                if(i==list.size()-period) openprice=0; //when signal appear in last day we can't take open price from future ;d
                else openprice=open_price.get(i+period); //cause list of ATR is shorter than list about period-1

                results.add(new IndicatorResult(this.getName(), Signal.buy,list.get(minIndex+period-1).getDate(),close_price.get(minIndex+period-1),openprice));
                results.add(new IndicatorResult(this.getName(), Signal.sell,list.get(maxIndex+period-1).getDate(),close_price.get(maxIndex+period-1),openprice));
                //date ,signal status, name, close price, open price of next day
                max=0;
                j=0;
                min=999999999;
            }

        }
        close_price.clear();
        open_price.clear();
        max_price.clear();
        min_price.clear();
        return results;
    }

    public void initialize(Map<String, Object> parameters) {
        this.period = Integer.parseInt(parameters.get("period").toString());
        this.ATR_period = Integer.parseInt(parameters.get("ATRperiod").toString());
        this.list = (ArrayList<StockCompany>)parameters.get("stockList");

        for(int i=0;i<list.size();i++)
        {
            this.close_price.add(list.get(i).getEndValue());
            this.open_price.add(list.get(i).getStartValue());
            this.max_price.add(list.get(i).getMaxValue());
            this.min_price.add(list.get(i).getMinValue());
        }
    }

}