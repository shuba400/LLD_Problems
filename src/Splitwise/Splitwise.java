package Splitwise;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Splitwise {
    HashSet<User> users;
    ArrayList<Expense> expenses;
    ArrayList<Transaction> transactions;
    SplitStrategy splitStrategy;

    public Splitwise(){
        users = new HashSet<>();
        expenses = new ArrayList<>();
        transactions = new ArrayList<>();
        splitStrategy = new EqualSplitStrategy();
    }

    public User addUser(String userName){
        User user = new User(userName);
        users.add(user);
        return user;
    }

    public void addTransaction(User userA,User userB,double amt){
        final Transaction transaction = new Transaction(userA,userB,amt);
        transaction.printTransaction(true);
        transactions.add(transaction);
    }

    public void addExpense(String expenseName,User user,double amount,ArrayList<User> owedUsers){
        if(!users.contains(user)){
            System.out.printf("User %s is not registered",user.name);
            return;
        }
        for(User owedUser: owedUsers){
            if(!users.contains(owedUser)){
                System.out.printf("User %s is not registered",owedUser.name);
                return;
            }
        }
        expenses.add(new Expense(expenseName,user,amount,owedUsers,this.splitStrategy));
    }

    public void getSimplifiedTransaction(){
        HashMap<User,Balance> userBalanceHashMap = new HashMap<>();
        for(Expense expense: expenses){
            for(Balance balance: expense.balances){
                userBalanceHashMap.putIfAbsent(balance.user,new Balance(balance.user,0));
                Balance currentUserBalance = userBalanceHashMap.get(balance.user);
                currentUserBalance.amount += balance.amount;
                userBalanceHashMap.put(balance.user,currentUserBalance);
            }
        }
        for(Transaction transaction: transactions){
            userBalanceHashMap.putIfAbsent(transaction.payee,new Balance(transaction.payee,0));
            Balance currentUserBalance = userBalanceHashMap.get(transaction.payee);
            currentUserBalance.amount += transaction.amount;
            userBalanceHashMap.put(transaction.payee,currentUserBalance);

            userBalanceHashMap.putIfAbsent(transaction.payee,new Balance(transaction.receiver,0));
            currentUserBalance = userBalanceHashMap.get(transaction.receiver);
            currentUserBalance.amount -= transaction.amount;
            userBalanceHashMap.put(transaction.receiver,currentUserBalance);
        }

        PriorityQueue<Balance> postiveBalance = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Balance> negativeBalance =  new PriorityQueue<>();
        userBalanceHashMap.forEach((k,v) -> {
            if(Double.compare(v.amount,0.0) > 0 ){
                postiveBalance.add(v);
            }
            else if(Double.compare(v.amount,0.0) < 0) {
                negativeBalance.add(v);
            }
        });
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (!negativeBalance.isEmpty() && !postiveBalance.isEmpty()){
            Balance highestPosBalance = postiveBalance.poll();
            Balance lowestNegBalance = negativeBalance.poll();
            double amt = Math.min(Math.abs(lowestNegBalance.amount),highestPosBalance.amount);
            lowestNegBalance.amount += amt;
            highestPosBalance.amount -= amt;
            transactions.add(new Transaction(lowestNegBalance.user,highestPosBalance.user,amt));
            if(Double.compare(lowestNegBalance.amount,0) != 0){
                negativeBalance.add(lowestNegBalance);
            }
            if(Double.compare(highestPosBalance.amount,0) != 0) {
                postiveBalance.add(highestPosBalance);
            }
        }
        System.out.println("Settle Up Stats\n");
        transactions.forEach(transaction -> {
            transaction.printTransaction();
        });
    }

}
