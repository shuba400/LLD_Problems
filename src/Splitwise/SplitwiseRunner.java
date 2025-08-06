package Splitwise;

/*
    SplitWise Interface

    SplitWise --> addUser() addTransaction()
    User -> amount owed (+,-), userName ,
    SplitStrategy
    Expense --> User

    User --> UserID
    Expense - Id, Array[UserID], Array[Balance].
    SplitWise --> addUser() , addExpense(User, List<User>, Name,Amount,Strategy) , showTransactions, settleTransaction()
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SplitwiseRunner {
    final Splitwise splitwise;
    public SplitwiseRunner(){
        splitwise = new Splitwise();
    }

    public void run(){
        final User userA = splitwise.addUser("userA");
        final User userB = splitwise.addUser("userB");
        final User userC = splitwise.addUser("userC");
        final User userD = splitwise.addUser("userD");
        final User userE = splitwise.addUser("userE");

        ArrayList<User> owedUser;

        //transaction 1 userA  {userB,userC,userD} 100
        owedUser = new ArrayList<>(Arrays.asList(userB,userC,userD));
        splitwise.addExpense("Expense 1",userA,100,owedUser);
        splitwise.getSimplifiedTransaction();

        owedUser = new ArrayList<>(Arrays.asList(userB,userE));
        splitwise.addExpense("Expense 2",userD,100,owedUser);
        splitwise.getSimplifiedTransaction();

        splitwise.addTransaction(userB,userD,50);

        splitwise.getSimplifiedTransaction();
    }
}
