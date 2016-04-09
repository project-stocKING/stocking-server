package Tools;


import java.util.ArrayList;

//Weighted Moving Average
public class WMA {
    private int N;  //period
    private double alpha; //denominator
    private int length;
    private ArrayList<Double> c_price = new ArrayList<Double>(); //list of closing prices
    private ArrayList<Double> wma; //list of wma values

    public WMA(int N, ArrayList<Double> c_price)
    {
        this.N = N;
        length=c_price.size()-N+1;
        this.c_price = new ArrayList<Double>(c_price);
        wma= new ArrayList<Double>(length);
    }

    public ArrayList<Double> calculate()
    {
        double avg;
        for(int i=1; i<N;i++)
            alpha+=i; //sum of the weights

        for(int i=0; i<length;i++)
        {
            avg=0;
            for(int w=0;w<N;w++)
                avg += c_price.get(i + w) * (w + 1);

            avg/=alpha;
            wma.add(avg);
        }
        return wma;
    }

}
