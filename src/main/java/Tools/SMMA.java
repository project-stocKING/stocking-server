package Tools;

import java.util.ArrayList;

//Smoothed Moving Average
public class SMMA {
    private int N;  //period
    private int length;
    private ArrayList<Double> c_price = new ArrayList<Double>(); //list of closing prices
    private ArrayList<Double> smma; //list of smma values

    public SMMA(int N, ArrayList<Double> c_price) {
        this.N = N;
        length = c_price.size() - N + 1;
        this.c_price = (ArrayList<Double>) c_price.clone();
        smma = new ArrayList<Double>(length);
    }

    public ArrayList<Double> calculate() //last value in list is the newest value
    {
        double sum = 0;

        for (int s = 0; s < N; s++)
            sum += c_price.get(s);

        smma.add(sum / N);

        for (int i = 1; i < length; i++) {
            sum = smma.get(i - 1) * N;
            smma.add((sum - smma.get(i - 1) + c_price.get(i + N - 1)) / N);
        }


        return smma;
    }
}
