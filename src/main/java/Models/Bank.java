package Models;

import Indexes.Index;
import Indexes.IndexResult;
import com.mongodb.util.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {

    int sharesAmount;
    double sharesCost, soldSharesCost;
    double restOfBank;
    IndexResult currentResult;

    public void calculateBank(ArrayList<IndexResult> indexResultArrayList){

        if(findFirstBuy(indexResultArrayList)){
            for(int i = indexResultArrayList.size() - 1; i >= 0; i--) {
                currentResult = indexResultArrayList.get(i);
                if (currentResult.getResult().getName().equals("buy")) {
                    buySignalAction(currentResult);

                } else {

                    sellSignalAction(currentResult);


                }


            }
        }else {
            for(int i=indexResultArrayList.size()-2; i>=0; i--){
                currentResult = indexResultArrayList.get(i);
                if(currentResult.getResult().getName().equals("buy")){
                   buySignalAction(currentResult);

                }else{
                    sellSignalAction(currentResult);

            }


            }
        }
    }

    private void buySignalAction(IndexResult currentResult)
    {
        sharesAmount = calculateSharesAmount(currentResult);
        sharesCost = sharesAmount*currentResult.getNextDayOpenValue();
        restOfBank = currentResult.getBudgetAmount() - sharesCost;
        currentResult.setBudgetAmount(Math.floor(restOfBank*100)/100);
        currentResult.setSharesAmount(sharesAmount);
    }

    private void sellSignalAction(IndexResult currentResult)
    {
        soldSharesCost = sharesAmount*currentResult.getNextDayOpenValue();
        currentResult.setBudgetAmount(Math.floor((soldSharesCost + restOfBank)*100)/100);
        currentResult.setSharesAmount(0);
    }

    public int calculateSharesAmount(IndexResult indexResult){

        int sharesAmount;

        sharesAmount = (int) Math.floor(indexResult.getBudgetAmount()/indexResult.getNextDayOpenValue());

        return sharesAmount;
    }


    public boolean findFirstBuy(ArrayList<IndexResult> indexResultArrayList){

        if(indexResultArrayList.get(indexResultArrayList.size()-1).getResult().getName().equals("buy")){
            return true;
        }
        else return false;
    }

}

