package Splitwise;

import java.util.ArrayList;
import java.util.List;

public class EqualSplitStrategy implements SplitStrategy {
    @Override
    public ArrayList<Balance> splitExpense(ArrayList<Balance> balances) {
        double mean = 0;
        for(final Balance balance: balances){
            mean += balance.amount;
        }
        mean =  mean/(double) balances.size();
        for(final Balance balance: balances){
            balance.amount -= mean;
        }
        return balances;
    }
}
