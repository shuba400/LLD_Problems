package Splitwise;

public class Balance implements Comparable<Balance> {
    User user;
    double amount;

    public Balance(User user,double amount){
        this.user = user;
        this.amount = amount;
    }

    @Override
    public int compareTo(Balance other) {
        return Double.compare(this.amount, other.amount);
    }
}
