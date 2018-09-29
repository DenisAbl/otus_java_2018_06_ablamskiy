package courses.otus.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class CardReader {

    private Scanner scanner = new Scanner((System.in));
    private Logger logger = LoggerFactory.getLogger(ATMImpl.class);
    private int accountId;

    public int getAccountId() {

        logger.info("(dummy-reader instead of reading accountId from the card(number 1-5))");
        logger.info("Enter account id:");
        accountId = scanner.nextInt();
        return accountId;
    }
}
