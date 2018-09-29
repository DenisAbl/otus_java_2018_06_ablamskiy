import courses.otus.exceptions.InsufficientFundsException;
import courses.otus.models.ATM;
import courses.otus.models.ATMImpl;
import courses.otus.models.Cassette;
import courses.otus.models.ClientAccount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ATMTest {

    ATMImpl atm;
    ClientAccount clientAccount;
    int initBanknoteAmount = 1;

    @Before
    public void setUp() {
       atm = new ATMImpl(initBanknoteAmount);
       clientAccount = atm.getClientAccount(4);
    }

    @Test
    public void testWithdrawMoney() throws InsufficientFundsException {
        int withdrawAmount = 8850;
        long startAtmSum = atm.getTotalSum();
        BigDecimal initialBalance = clientAccount.getBalance();
        atm.withdrawMoney(withdrawAmount,clientAccount);
        BigDecimal currentBalance = clientAccount.getBalance();
        long currentAtmSum = atm.getTotalSum();
        Assert.assertEquals("Account balance is incorrect", withdrawAmount, initialBalance.subtract(currentBalance).intValue());
        Assert.assertEquals("ATM balance is incorrect", withdrawAmount,startAtmSum - currentAtmSum);
    }

    @Test
    public void testDepositMoney(){
        long startSum = atm.getTotalSum();
        BigDecimal startBalance = new BigDecimal(atm.displayBalance(clientAccount));
        Map<Integer,Integer> depositedMoney = new HashMap<>();
        depositedMoney.put(Cassette.FIVE_THOUSANDS,20);
        depositedMoney.put(Cassette.FIVE_HUNDREDS,10);
        final long[] sum = {0};
        depositedMoney.forEach((k,v)-> sum[0] += k*v);

        atm.depositMoney(depositedMoney,clientAccount);
        BigDecimal finishBalance = new BigDecimal(atm.displayBalance(clientAccount));

        Assert.assertEquals("Account balance is incorrect", sum[0], finishBalance.subtract(startBalance).longValue());
        Assert.assertEquals("ATM balance is incorrect",sum[0]+startSum, atm.getTotalSum());

    }

    @Test
    public void testDisplayBalance(){
        String balance = clientAccount.getBalance().toString();
        Assert.assertEquals("Incorrect balance displaying", balance, atm.displayBalance(clientAccount));
    }

    @Test
    public void testTotalSum(){
        long totalSum = atm.getTotalSum();
        Assert.assertEquals("TotalSum failure", 8850*initBanknoteAmount,totalSum);

    }
}