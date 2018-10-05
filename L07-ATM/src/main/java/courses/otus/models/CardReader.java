package courses.otus.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class CardReader {

    private Scanner scanner = new Scanner((System.in));
    private Logger logger = LoggerFactory.getLogger(ATMImpl.class);
    private int accountId;
    private String msg;

    public int getAccountId() {

        msg = "(dummy-reader instead of reading accountId from the card(number 1-5))\nEnter account id:" ;
        logger.info(msg);
        System.out.println(msg);
        accountId = scanner.nextInt();
        return accountId;
    }
}
