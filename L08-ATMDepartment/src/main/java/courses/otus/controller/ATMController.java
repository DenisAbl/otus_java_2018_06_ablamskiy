package courses.otus.controller;

import courses.otus.exceptions.InsufficientFundsException;
import courses.otus.models.ATMImpl;
import courses.otus.models.ClientAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ATMController {

    private ATMImpl atm;
    private Logger logger = LoggerFactory.getLogger(ATMController.class);

    public ATMController(ATMImpl atm) {
        this.atm = atm;
    }

    public void withdrawMoney(ClientAccount clientAccount){
        if (atm.getTotalSum() != 0) {
            atm.display.showWithdrawRequestMenu();
            int moneyAmount;
            do {
                atm.showMessage("Please enter amount aliquot 50");
                moneyAmount = Integer.parseInt(atm.keyboard.getMoneyAmount());
            }
            while (moneyAmount % 50 != 0 || moneyAmount == 0);
            try {atm.withdrawMoney(moneyAmount, clientAccount);}
            catch(InsufficientFundsException e){
                System.out.println(e.getMessage());
                logger.info(e.getMessage());}
        }
        else {
            atm.showMessage("No money, encashment expected");
        }
    }

    public void depositMoney(ClientAccount clientAccount) {
        Map<Integer,Integer> insertedMoney = new HashMap<>();
        atm.display.showDepositRequestMenu();
        int banknoteNominal;
        int banknoteAmount;
        while (true) {
            System.out.println("Enter banknote nominal value or -1 to finish:");
            banknoteNominal = Integer.parseInt(atm.keyboard.getMoneyAmount());
            if(banknoteNominal == -1) break;
            System.out.println("Enter banknotes amount:");
            banknoteAmount = Integer.parseInt(atm.keyboard.getMoneyAmount());
            insertedMoney.put(banknoteNominal,banknoteAmount);
        }
        atm.depositMoney(insertedMoney, clientAccount);
    }




}
