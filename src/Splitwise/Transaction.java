package Splitwise;

public class Transaction {
    User payee;
    User receiver;
    Double amount;

    public Transaction(User payee,User receiver,Double amount){
        this.payee = payee;
        this.receiver = receiver;
        this.amount = amount;
    }

    public void printTransaction(boolean alreadyPaid){
        if(alreadyPaid){
            System.out.printf("%s Payed %s an amount of %f\n", payee.name,receiver.name,amount);
        } else{
            System.out.printf("%s owes %s an amount of %f\n", payee.name,receiver.name,amount);
        }
    }

    public void printTransaction(){
        printTransaction(false);
    }
}
