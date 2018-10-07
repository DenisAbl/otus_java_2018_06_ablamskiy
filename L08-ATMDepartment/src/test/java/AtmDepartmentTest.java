import courses.otus.exceptions.InsufficientFundsException;
import courses.otus.models.*;
import courses.otus.models.Interfaces.ATM;
import courses.otus.models.Interfaces.ATMDepartmentObserver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class AtmDepartmentTest {

    ATMDepartmentObserver atmDepartment;
    ClientAccount clientAccount = new ClientAccount(4,null,"50000");

    public final int FIRST_INITIAL_AMOUNT = 1;
    public final int SECOND_INITIAL_AMOUNT = 3;
    public final int THIRD_INITIAL_AMOUNT = 10;

    @Before
    public void setUp(){
        atmDepartment = new ATMDepartmentImpl(Arrays.asList(new ATMImpl(FIRST_INITIAL_AMOUNT),
                                                            new ATMImpl(SECOND_INITIAL_AMOUNT),
                                                            new ATMImpl(THIRD_INITIAL_AMOUNT)));
    }

    @Test
    public void getTotalSumTest(){
        long sum = 8850 * (FIRST_INITIAL_AMOUNT+SECOND_INITIAL_AMOUNT+THIRD_INITIAL_AMOUNT);
        Assert.assertEquals("TotalSum failure",sum ,atmDepartment.getTotalSum());
    }

    @Test
    public void restoreInitialStateTest(){
        //initial state
        List<ATM> atmList = atmDepartment.getAtmList();
        long initialTotalSum = atmDepartment.getTotalSum();

        //Withdraw some money
        atmList.forEach(atm-> {
            try {
                atm.withdrawMoney(8850,clientAccount);
            } catch (InsufficientFundsException e) {
                e.printStackTrace();
            }
        });
        long tempSum = atmDepartment.getTotalSum();

        //Restoring ATMs
        atmDepartment.restoreInitialState();
        long restoredTotalSum = atmDepartment.getTotalSum();
        Assert.assertEquals("Initial total sum and restoret total sum are different",initialTotalSum,restoredTotalSum);
        Assert.assertNotEquals("Initial total sum and restoret total sum are different",tempSum,restoredTotalSum);
    }
}
