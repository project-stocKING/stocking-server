package Models;

import Indexes.Indicator;
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
        if(checkLastSignalSell(indexResultArrayList)){
            operationsLastSell(indexResultArrayList);
        }else{
            operationsLastBuy(indexResultArrayList);
        }



    }
    private void buySignalAction(IndicatorResult currentResult)
    {

        if(budget<currentResult.getNextDayOpenValue()){
            System.out.print("To small budget");
            currentResult.setBudgetAmount(budget);
        }else {
            sharesAmount = calculateSharesAmount(currentResult);
            sharesCost = sharesAmount * currentResult.getNextDayOpenValue();
            restOfBank = budget - sharesCost;
            currentResult.setBudgetAmount(Math.floor(restOfBank * 100) / 100);
            currentResult.setSharesAmount(sharesAmount);
        }
    }
    private void sellSignalAction(IndicatorResult currentResult)
    {

        if(currentResult.getSharesAmount()==0){
            currentResult.setBudgetAmount(budget);
            currentResult.setSharesAmount(0);
        }
        else {
            soldSharesCost = sharesAmount * currentResult.getNextDayOpenValue();
            currentResult.setBudgetAmount(Math.floor((soldSharesCost + restOfBank) * 100) / 100);
            budget = currentResult.getBudgetAmount();
            currentResult.setSharesAmount(0);
        }

    }
    private void operationsLastSell (ArrayList<IndicatorResult> indexResultArrayList){

        if(findFirstBuy(indexResultArrayList)){
            for(int i = indexResultArrayList.size() - 1; i >= 0; i--) {
                currentResult = indexResultArrayList.get(i);

                if (currentResult.buyOrSell().equals("buy")) {

                    updateSharesAmount(currentResult);
                    updateBudget(currentResult);
                    buySignalAction(currentResult);

                } else {
                    updateSharesAmount(currentResult);
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

                    updateSharesAmount(currentResult);
                    updateBudget(currentResult);
                    buySignalAction(currentResult);


                } else{

                    updateSharesAmount(currentResult);
                    updateBudget(currentResult);
                    sellSignalAction(currentResult);

                }
            }
        }
    }
    private void operationsLastBuy(ArrayList<IndicatorResult> indexResultArrayList){

        if(findFirstBuy(indexResultArrayList)){
            for(int i = indexResultArrayList.size() - 1; i > 0; i--) {
                currentResult = indexResultArrayList.get(i);

                if (currentResult.buyOrSell().equals("buy")) {
                    updateSharesAmount(currentResult);
                    updateBudget(currentResult);
                    buySignalAction(currentResult);

                } else {
                    updateSharesAmount(currentResult);
                    updateBudget(currentResult);
                    sellSignalAction(currentResult);

                }


            }
        }
        else
        {
            for(int i=indexResultArrayList.size()-2; i>0; i--){

                currentResult = indexResultArrayList.get(i);


                if(currentResult.buyOrSell().equals("buy")){
                    updateSharesAmount(currentResult);
                    updateBudget(currentResult);
                    buySignalAction(currentResult);


                } else{
                    updateSharesAmount(currentResult);
                    updateBudget(currentResult);
                    sellSignalAction(currentResult);

                }
            }
        }
    }
    private int calculateSharesAmount(IndicatorResult indexResult){

        int sharesAmount;
        sharesAmount = (int) Math.floor(budget/indexResult.getNextDayOpenValue());

        return sharesAmount;
    }
    private boolean findFirstBuy(ArrayList<IndicatorResult> indexResultArrayList){
        if(indexResultArrayList.size()>0) {
            if (indexResultArrayList.get(indexResultArrayList.size() - 1).getResult().getName().equals("buy")) {
                return true;
            } else return false;
        }else return false;
    }
    private void updateBudget(IndicatorResult currentResult){
        currentResult.setBudgetAmount(budget);
    }
    private void updateSharesAmount(IndicatorResult currentResult){
        currentResult.setSharesAmount(sharesAmount);
    }
    private boolean checkLastSignalSell(ArrayList<IndicatorResult> indexResultArrayList){
        if(indexResultArrayList.get(0).getResult().equals("sell")){
            return true;
        }
        return false;
    }
}

