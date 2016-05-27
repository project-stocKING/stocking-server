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

        if(checkLastSignalBuy(indexResultArrayList)){
            operationsLastBuy(indexResultArrayList);
            indexResultArrayList.remove(0);
        } else{
            operationsLastSell(indexResultArrayList);
        }



    }
    private void buySignalAction(IndicatorResult currentResult)
    {

        if(budget<currentResult.getNextDayOpenValue()){
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

                    update(currentResult);
                    buySignalAction(currentResult);

                } else {
                    update(currentResult);
                    sellSignalAction(currentResult);

                }


            }
        }
        else
        {
            for(int i=indexResultArrayList.size()-2; i>=0; i--){

                currentResult = indexResultArrayList.get(i);
                if(currentResult.buyOrSell().equals("buy")){

                    update(currentResult);
                    buySignalAction(currentResult);


                } else{

                    update(currentResult);
                    sellSignalAction(currentResult);

                }
            }
        }

    }
    private void operationsLastBuy(ArrayList<IndicatorResult> indexResultArrayList){
        if(findFirstBuy(indexResultArrayList)){
            for(int i = indexResultArrayList.size() - 1; i >=0; i--) {
                currentResult = indexResultArrayList.get(i);

                if (currentResult.buyOrSell().equals("buy")) {
                    update(currentResult);
                    buySignalAction(currentResult);

                } else {
                    update(currentResult);
                    sellSignalAction(currentResult);

                }


            }
        }
        else
        {
            for(int i=indexResultArrayList.size()-2; i>=0; i--){

                currentResult = indexResultArrayList.get(i);


                if(currentResult.buyOrSell().equals("buy")){
                    update(currentResult);
                    buySignalAction(currentResult);


                } else{
                    update(currentResult);
                    sellSignalAction(currentResult);

                }
            }
        }

    }
    private int calculateSharesAmount(IndicatorResult indexResult){
        return (int) Math.floor(budget/indexResult.getNextDayOpenValue());
    }
    private boolean findFirstBuy(ArrayList<IndicatorResult> indexResultArrayList){
        return indexResultArrayList.get(indexResultArrayList.size() - 1).buyOrSell().equals("buy");
    }
    private void update(IndicatorResult currentResult){
        currentResult.setSharesAmount(sharesAmount);
        currentResult.setBudgetAmount(budget);
    }
    private boolean checkLastSignalBuy(ArrayList<IndicatorResult> indexResultArrayList){
        return indexResultArrayList.get(0).buyOrSell().equals("buy");
    }
}

