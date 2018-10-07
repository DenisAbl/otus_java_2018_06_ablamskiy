package courses.otus.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class Display {

    private Logger logger = LoggerFactory.getLogger(Display.class);
    private Scanner scanner = new Scanner(System.in);
    private String msg;

    void showStartMessage(){
        msg = "Please insert your card (just press enter)";
        showMessage(msg);
        scanner.nextLine();
    }

    void showCompletionMessage(){
        msg = "Operation successfully completed"+"\n"+"Have a nice day!";
        showMessage(msg);
    }

    void showMainMenu(){
        msg = "Choose operation: \n 1. Withdraw. \n 2. Deposit. \n 3. Display balance. \n 4. Exit. \n ";
        showMessage(msg);
    }
    void showBalance(String msg){
        showMessage(msg);
    }

    public void showWithdrawRequestMenu(){
        msg = "Please enter money amount you want to retrieve";
        showMessage(msg);
    }

    public void showDepositRequestMenu(){
        msg = "Please enter money amount you want to deposit";
        showMessage(msg);
    }

    private void showMessage(String msg){
        System.out.println(msg);
        logger.info(msg);
    }

}
