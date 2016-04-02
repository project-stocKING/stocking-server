package Indexes;

import Tools.*;

import java.util.ArrayList;

//Moving Averages: SMA, SMMA, WMA and EMA
public class MA extends Index implements IStockIndex{

    private int fastLength, slowLength;
    private String type;
    private ArrayList<Double> c_price= new ArrayList<Double>();

    public MA(int fastLength, int slowLength, String type)
    {
        super(type);
        this.fastLength = fastLength;
        this.slowLength = slowLength;
        this.type=type;
    }

    public int calculate()
    {
        ArrayList<Double> fastMA=new ArrayList<Double>();
        ArrayList<Double> slowMA=new ArrayList<Double>();

        if(type=="EMA")
        {
            fastMA=new EMA(fastLength, c_price).calculate();
            slowMA= new EMA(slowLength, c_price).calculate();
        }
        else if(type=="SMA")
        {
            fastMA=new SMA(fastLength, c_price).calculate();
            slowMA= new SMA(slowLength, c_price).calculate();
        }
        else if(type=="SMMA")
        {
            fastMA=new SMMA(fastLength, c_price).calculate();
            slowMA= new SMMA(slowLength, c_price).calculate();
        }
        else if(type=="WMA")
        {
            fastMA=new WMA(fastLength, c_price).calculate();
            slowMA= new WMA(slowLength, c_price).calculate();
        }

        double diff=0,tmp=0;
        int result=0;

        //checking intersect
        /*for (int i=slowLength;i>0;i--) { idk if this is good :P
            diff = fastMA.get(i) - slowMA.get(i);
            if(tmp>0 && diff<0)
                result=1;
            if (tmp<0 && diff>0)
                result=2;
            tmp=diff;
        }
        */
        return result; //0 nothing, 1 buy signal, 2 sell signal
    }

}
