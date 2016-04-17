package Indexes;
import Models.StockCompany;
import Tools.SMA;
import Tools.Signal;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


//Simple Moving Average as index
public class  ISMA extends Index implements IStockIndex{

    private int period;
    private ArrayList<Double> close_price = new ArrayList<Double>();
    private ArrayList<Double> open_price= new ArrayList<Double>();

    private ArrayList<StockCompany> list= new ArrayList<StockCompany>();

    public ISMA() // pusty konstruktor
    {
        super("SMA");
    }


    public ArrayList<IndexResult> calculate() throws FileNotFoundException
    {
        ArrayList<Double> SMA = new SMA(period, close_price).calculate();
        ArrayList<IndexResult> results=new ArrayList<IndexResult>();
        double diff,diffprev,openprice;
        Signal result;

        PrintWriter zapis = new PrintWriter("test.txt");
        PrintWriter zapis2 = new PrintWriter("test2.txt");
        PrintWriter zapis3 = new PrintWriter("test3.txt");

        zapis.println("sma: ");

        //checking intersect between sma and close_price
        boolean intersect;
        for (int i=SMA.size()-1;i>0;i--) {
            intersect= Line2D.linesIntersect(i-1,SMA.get(i-1),i,SMA.get(i),i-1,close_price.get(i-2+period),i,close_price.get(i-1+period));
            zapis.println(SMA.get(i));
            if(intersect)
            {
                diff = close_price.get(i-1+period) - SMA.get(i);
                diffprev= close_price.get(i-2+period) - SMA.get(i-1); //-2 because close_price is longer about period-1
                zapis2.println(diff);
                zapis3.println(diffprev);


                if(i==SMA.size()-1) openprice=0; //when signal appear in last day we can't take open price from future ;d
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
        zapis.close();
        zapis2.close();
        zapis3.close();
        return results;
    }

    public void initialize(Map<String, Object> parameters) throws FileNotFoundException {
        this.period = Integer.parseInt(parameters.get("period").toString());
        this.list = (ArrayList<StockCompany>)parameters.get("stockList");

        PrintWriter zapis = new PrintWriter("test_c.txt");
        PrintWriter zapis2 = new PrintWriter("test_d.txt");
        zapis.println("daty: ");

        for(int i=0;i<list.size();i++)
        {
            zapis.println(list.get(i).getEndValue());
            zapis2.println(list.get(i).getDate());
            this.close_price.add(list.get(i).getEndValue());
            this.open_price.add(list.get(i).getStartValue());
        }
        zapis.close();
        zapis2.close();
    }

}