package courses.otus.models;

public interface ATM {

    void withdrawMoney();

    void depositMoney();

    void displayBalance();

    long getTotalSum();

    void finishSession();
}
