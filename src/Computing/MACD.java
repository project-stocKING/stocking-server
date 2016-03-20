package Computing;
 
public class MACD implements Index
{
    private int fastLength, slowLength;
    private double value;
    private double []array;
    

    public MACD(int fastLength, int slowLength, double []array) 
    { 
        this.fastLength = fastLength; 
        this.slowLength = slowLength;
    } 
    
    @Override 
    public double calculate() 
    { 
        double fastEMA = new EMA(fastLength,array).calculate(); 
        double slowEMA = new EMA(slowLength,array).calculate(); 
        value = fastEMA - slowEMA; 
        return value; 
    } 
    
           
}
