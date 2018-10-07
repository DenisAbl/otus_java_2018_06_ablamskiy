package courses.otus.models.Interfaces;

import courses.otus.exceptions.InsufficientFundsException;
import courses.otus.models.ATMImpl.Memento;
import courses.otus.models.ClientAccount;

import java.util.Map;

public interface ATM {

    Map<Integer, Integer> withdrawMoney(int moneyAmount, ClientAccount clientAccount) throws InsufficientFundsException;

    void depositMoney(Map<Integer,Integer> insertedMoney, ClientAccount clientAccount);

    void displayBalance();

    long getTotalSum();

    void finishSession();

    Memento saveStateSnapshot();
}
