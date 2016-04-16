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


    public ArrayList<IndexResult> calculateBank(ArrayList<IndexResult> indexResultArrayList){

        DecimalFormat df = new DecimalFormat("#.##");


        if(findFirstBuy(indexResultArrayList)){
            for(int i=indexResultArrayList.size()-1; i>=0; i--) {

                if (indexResultArrayList.get(i).getResult().getName().equals("buy")) {
                    sharesAmount = (int) Math.floor(indexResultArrayList.get(i).getBudgetAmount() / indexResultArrayList.get(i).getNextDayOpenValue());
                    sharesCost = sharesAmount * indexResultArrayList.get(i).getNextDayOpenValue();
                    restOfBank = indexResultArrayList.get(i).getBudgetAmount() - sharesCost;
                    indexResultArrayList.get(i).setBudgetAmount(Math.floor(restOfBank*100)/100);
                    indexResultArrayList.get(i).setSharesAmount(sharesAmount);

                } else {

                    soldSharesCost = sharesAmount * indexResultArrayList.get(i).getNextDayOpenValue();
                    indexResultArrayList.get(i).setBudgetAmount(Math.floor((soldSharesCost + restOfBank)*100)/100);
                    indexResultArrayList.get(i).setSharesAmount(0);

                }


            }
        }else {
            for(int i=indexResultArrayList.size()-2; i>=0; i--){

                if(indexResultArrayList.get(i).getResult().getName().equals("buy")){
                    sharesAmount = (int) Math.floor(indexResultArrayList.get(i).getBudgetAmount()/indexResultArrayList.get(i).getNextDayOpenValue());
                    sharesCost = sharesAmount*indexResultArrayList.get(i).getNextDayOpenValue();
                    restOfBank = indexResultArrayList.get(i).getBudgetAmount() - sharesCost;
                    indexResultArrayList.get(i).setBudgetAmount(restOfBank);
                    indexResultArrayList.get(i).setSharesAmount(sharesAmount);

                }else{

                    soldSharesCost = sharesAmount*indexResultArrayList.get(i).getNextDayOpenValue();
                    indexResultArrayList.get(i).setBudgetAmount(Math.floor((soldSharesCost + restOfBank)*100)/100);
                    indexResultArrayList.get(i).setSharesAmount(0);
            }


            }
        }





        return indexResultArrayList;
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

