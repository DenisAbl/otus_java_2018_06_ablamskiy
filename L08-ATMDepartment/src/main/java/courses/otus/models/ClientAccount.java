package courses.otus.models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ClientAccount {

    private final int accountId;
    private final String accountPin;
    private BigDecimal balance;

    public ClientAccount(int accountId, String accountPin, String balance) {
        this.accountId = accountId;
        this.accountPin = accountPin;
        this.balance = new BigDecimal(balance).setScale(2, RoundingMode.DOWN);
    }

    int getAccountId() {
        return accountId;
    }

    public String getAccountPin() {
        return accountPin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
