package Splitwise.strategy;

import Splitwise.entities.Split;
import Splitwise.entities.User;

import java.util.List;

public interface SplitStrategy {
    List<Split> calculateSplits(double totalAmount, User paidBy, List<User> participants, List<Double> splitValues);
}
