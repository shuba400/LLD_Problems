package Splitwise;

import java.util.ArrayList;

public class Expense {
    ArrayList<Balance> balances;
    String expenseName;
    SplitStrategy splitStrategy;

    public Expense(String expenseName,User user,double amount,ArrayList<User> owedUsers,SplitStrategy splitStrategy){
        this.expenseName = expenseName;
        balances = new ArrayList<>();
        this.splitStrategy = splitStrategy;
        balances.add(new Balance(user,amount));
        for(User user1: owedUsers){
            balances.add(new Balance(user1,0));
        }
        reBalance();
    }

    private void reBalance(){
        this.balances = this.splitStrategy.splitExpense(this.balances);
    }

}
