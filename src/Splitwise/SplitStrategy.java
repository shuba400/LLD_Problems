package Splitwise;

import java.util.ArrayList;

public interface SplitStrategy {
    ArrayList<Balance> splitExpense(ArrayList<Balance> balances);
}
