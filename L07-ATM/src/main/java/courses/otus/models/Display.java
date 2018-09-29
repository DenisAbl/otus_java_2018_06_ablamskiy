package courses.otus.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Scanner;

public class Display {

    private Logger logger = LoggerFactory.getLogger(Display.class);
    private Scanner scanner = new Scanner(System.in);

    public void showStartMessage(){
        logger.info("Please insert your card (just press enter)");
        scanner.nextLine();
    }

    public void showCompletionMessage(){
        logger.info("Operation successfully completed"+"\n"+"Have a nice day!");
    }

    public void showMainMenu(){
        logger.info("Choose operation: \n 1. Withdraw. \n 2. Deposit. \n 3. Display balance. \n 4. Exit. \n ");
    }
    public void showBalance(String balance){
        logger.info("Your account balance is " + balance);
    }

    public void showWithdrawRequestMenu(){
        logger.info("Please enter money amount you want to retrieve");
    }

    public void showDepositRequestMenu(){
        logger.info("Please enter money amount you want to deposit");
    }
}
