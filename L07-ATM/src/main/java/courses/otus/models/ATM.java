package courses.otus.models;

import courses.otus.exceptions.InsufficientFundsException;

import java.util.Map;

public interface ATM {

    Map<Integer, Integer> withdrawMoney(int moneyAmount, ClientAccount clientAccount) throws InsufficientFundsException;

    void depositMoney(Map<Integer,Integer> insertedMoney, ClientAccount clientAccount);

    void displayBalance();

    long getTotalSum();

    void finishSession();
}
