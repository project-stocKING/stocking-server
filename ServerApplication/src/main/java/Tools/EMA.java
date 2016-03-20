package Tools;

//Exponential Moving Average
public class EMA
{ 
    private int N;                  //Okres
    private double alpha;
    private double value;           //Końcowa średnia wartość
    private double []c_price;       //Kolejne wartości średniej

 public EMA(int N, double [] c_price)
 {
    this.N = N;
    alpha = 2.0 / (N + 1);

    c_price = new double [N];

    for (int i = 0; i < N; i++)
    {
        this.c_price=c_price;
    }
 } 

 public double calculate() 
 { 
    double numerator = 0;
    double denominator = 0;
    
    double exp = 1.0 - alpha;


    for (int i = 1; i <= N; i++)
    {
        numerator += c_price[i] * Math.pow(exp,i);
        denominator += Math.pow(exp,i);
    }
    
    numerator += c_price[0]; //ostatnia wartosc
    denominator += 1;
    value=numerator / denominator;
    return value;
 }

 
 
}
