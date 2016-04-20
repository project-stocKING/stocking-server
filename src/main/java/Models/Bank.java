package Models;

import Indexes.IndicatorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {

    int sharesAmount;
    double sharesCost, soldSharesCost;
    double restOfBank;
    double budget;
    IndicatorResult currentResult;


    public void calculateBank(ArrayList<IndicatorResult> indexResultArrayList){

        budget = indexResultArrayList.get(indexResultArrayList.size()-1).getBudgetAmount();



        if(findFirstBuy(indexResultArrayList)){
            for(int i = indexResultArrayList.size() - 1; i >= 0; i--) {
                currentResult = indexResultArrayList.get(i);

                if (currentResult.buyOrSell().equals("buy")) {

                    updateBudget(currentResult);
                    buySignalAction(currentResult);

                } else {
                    updateBudget(currentResult);
                    sellSignalAction(currentResult);

                }


            }
        }
        else
        {
            for(int i=indexResultArrayList.size()-2; i>=0; i--){

                currentResult = indexResultArrayList.get(i);


                if(currentResult.buyOrSell().equals("buy")){

                    updateBudget(currentResult);
                    buySignalAction(currentResult);


                } else{
                    updateBudget(currentResult);
                    sellSignalAction(currentResult);

                }
            }
        }
    }

    private void buySignalAction(IndicatorResult currentResult)
    {

        sharesAmount = calculateSharesAmount(currentResult);
        sharesCost = sharesAmount*currentResult.getNextDayOpenValue();
        restOfBank = budget - sharesCost;
        currentResult.setBudgetAmount(Math.floor(restOfBank * 100) / 100);
        currentResult.setSharesAmount(sharesAmount);
    }

    private boolean checkMoneyAvailable(IndicatorResult currentResult){

        if((currentResult.getBudgetAmount()>0) || (currentResult.getBudgetAmount() >= currentResult.getNextDayOpenValue())){
            return true;
        } else return false;
    }

    private void sellSignalAction(IndicatorResult currentResult)
    {


            soldSharesCost = sharesAmount * currentResult.getNextDayOpenValue();
            currentResult.setBudgetAmount(Math.floor((soldSharesCost + restOfBank) * 100) / 100);
            budget = currentResult.getBudgetAmount();
            currentResult.setSharesAmount(0);


    }

    public int calculateSharesAmount(IndicatorResult indexResult){

        int sharesAmount;
        sharesAmount = (int) Math.floor(budget/indexResult.getNextDayOpenValue());

        return sharesAmount;
    }

    public boolean findFirstBuy(ArrayList<IndicatorResult> indexResultArrayList){
        if(indexResultArrayList.size()>0) {
            if (indexResultArrayList.get(indexResultArrayList.size() - 1).getResult().getName().equals("buy")) {
                return true;
            } else return false;
        }else return false;
    }

    private void updateBudget(IndicatorResult currentResult){
        currentResult.setBudgetAmount(budget);
    }

    private boolean checkLastSignalSell(ArrayList<IndicatorResult> indexResultArrayList){
        if(indexResultArrayList.get(0).getResult().equals("sell")){
            return true;
        }
        return false;
    }
}

