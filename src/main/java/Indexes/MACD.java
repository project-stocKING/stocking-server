package Indexes;

import Tools.EMA;


public class MACD implements IStockIndex
{
    private int fastLength, slowLength;
    private double value;
    private double []array;     //TODO: change to ArrayList of StockCompany (or just it's value)

    public MACD(int fastLength, int slowLength, double []array) 
    { 
        this.fastLength = fastLength; 
        this.slowLength = slowLength;
    } 

    public double calculate() 
    { 
        double fastEMA = new EMA(fastLength, array).calculate();
        double slowEMA = new EMA(slowLength, array).calculate();
        value = fastEMA - slowEMA; 
        return value; 
    } 
    
           
}
