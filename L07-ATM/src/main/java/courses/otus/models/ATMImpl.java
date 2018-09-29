package courses.otus.models;

import courses.otus.Operation;
import courses.otus.exceptions.IncorrectPinFormatException;
import courses.otus.exceptions.InsufficientFundsException;
import courses.otus.service.DBServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ATMImpl implements ATM {

    private final int INITIAL_BANKNOTE_AMOUNT;

    private long totalSum;
    private Map<Integer,Cassette> withdrawCassettes;
    private Cassette rejectedCassette;

    private CardReader cardReader;
    private Dispenser dispenser;
    private Display display;
    private Keyboard keyboard;

    private ClientAccount clientAccount;
    private DBServiceImpl dbService;
    private Operation operation;

    private int accountId;
    private boolean isPinValid = false;
    private boolean isSessionFinished = false;

    private Logger logger = LoggerFactory.getLogger(ATMImpl.class);

    public ATMImpl(){
        INITIAL_BANKNOTE_AMOUNT = 1000;
        init();
    }

    public ATMImpl(int initialBankNoteAmount){
        INITIAL_BANKNOTE_AMOUNT = initialBankNoteAmount;
        init();

    }

    private void init() {
        cardReader = new CardReader();
        dispenser = new Dispenser();
        display = new Display();
        keyboard = new Keyboard();
        withdrawCassettes = new TreeMap<>(Comparator.reverseOrder());
        rejectedCassette = new Cassette(0,0);
        dbService = new DBServiceImpl();

        initWithdrawCassettes();

    }
    //получается громоздко, переделать
    private void initWithdrawCassettes(){
        withdrawCassettes.put(Cassette.FIFTYS,new Cassette(Cassette.FIFTYS,INITIAL_BANKNOTE_AMOUNT));
        withdrawCassettes.put(Cassette.ONE_HUNDREDS,new Cassette(Cassette.ONE_HUNDREDS,INITIAL_BANKNOTE_AMOUNT));
        withdrawCassettes.put(Cassette.TWO_HUNDREDS,new Cassette(Cassette.TWO_HUNDREDS,INITIAL_BANKNOTE_AMOUNT));
        withdrawCassettes.put(Cassette.FIVE_HUNDREDS,new Cassette(Cassette.FIVE_HUNDREDS,INITIAL_BANKNOTE_AMOUNT));
        withdrawCassettes.put(Cassette.ONE_THOUSANDS, new Cassette(Cassette.ONE_THOUSANDS,INITIAL_BANKNOTE_AMOUNT));
        withdrawCassettes.put(Cassette.TWO_THOUSANDS, new Cassette(Cassette.TWO_THOUSANDS,INITIAL_BANKNOTE_AMOUNT));
        withdrawCassettes.put(Cassette.FIVE_THOUSANDS, new Cassette(Cassette.FIVE_THOUSANDS,INITIAL_BANKNOTE_AMOUNT));
    }

    public void startWork(){
        while(true) {
            display.showStartMessage();
            accountId = cardReader.getAccountId();

            while (!isPinValid) {
                try {
                    logger.info("Enter PIN");
                    validatePIN(keyboard.getPIN());
                } catch (IncorrectPinFormatException e) {
                    logger.error("Incorrect pin format. Make another try.");
                }
            }   if (isPinValid) startSession();
        }
    }

    @Override
    public void withdrawMoney() {

        if (getTotalSum() != 0) {
            display.showWithdrawRequestMenu();
            int moneyAmount;

            do {
                logger.info("Please enter amount aliquot 50");
                moneyAmount = Integer.parseInt(keyboard.getMoneyAmount());
            }
            while (moneyAmount % 50 != 0 || moneyAmount == 0);

            try {withdrawMoney(moneyAmount, clientAccount);}
            catch(InsufficientFundsException e){
                logger.info(e.getMessage());}
        }

        else logger.info("No money, encashment expected");
    }

    public Map<Integer,Integer> withdrawMoney(int moneyAmount, ClientAccount clientAccount) throws InsufficientFundsException {
        Map<Integer,Integer> banknoteMap;
        BigDecimal withdrawAmount = new BigDecimal(moneyAmount).setScale(2, RoundingMode.DOWN);
        BigDecimal currentBalance = dbService.getClientAccountById(clientAccount.getAccountId()).getBalance();
        if (withdrawAmount.compareTo(currentBalance) <= 0 ){
            if (prepareMoney(moneyAmount)){
                dbService.getClientAccountById(clientAccount.getAccountId()).setBalance(currentBalance.subtract(withdrawAmount));
                banknoteMap = dispenser.WithdrawMoney();
                display.showCompletionMessage();
                return banknoteMap;}
            else throw new InsufficientFundsException("Sorry, operation cannot be accomplished");
        }
        else throw new InsufficientFundsException("Not enough funds");
    }

    private boolean prepareMoney(int moneyAmount){
        int tempValue = moneyAmount;
        int banknoteAmount;
        Map<Integer,Integer> tempCassetteMap = new TreeMap<>(Comparator.reverseOrder());
        Map<Integer,Integer> tempDispenserMap = new HashMap<>();

        withdrawCassettes.forEach((k,v) -> tempCassetteMap.put(k,v.getBankNoteAmount()));

        for (Map.Entry<Integer,Integer> entry : tempCassetteMap.entrySet()) {
            banknoteAmount = 0;
            while(tempValue >= entry.getKey()) {
                if (entry.getValue() != 0) {
                    entry.setValue((entry.getValue()- 1));
                    tempDispenserMap.put(entry.getKey(),++banknoteAmount);
                    tempValue = tempValue - entry.getKey();
                }
                else break;
            }
        }
        if (tempValue == 0){
            ejectBanknotesFromCassettes(tempDispenserMap);
            dispenser.collectBanknotes(tempDispenserMap);

        }
        else logger.info("There are not enough funds to accomplish your request.");
        return tempValue == 0;

    }

    @Override
    public void depositMoney() {
        Map<Integer,Integer> insertedMoney = new HashMap<>();
        display.showDepositRequestMenu();
        int banknoteNominal;
        int banknoteAmount;
        while (true) {
            banknoteNominal = Integer.parseInt(keyboard.getMoneyAmount());
                if(banknoteNominal == -1) break;
             banknoteAmount = Integer.parseInt(keyboard.getMoneyAmount());
             insertedMoney.put(banknoteNominal,banknoteAmount);
        }

        depositMoney(insertedMoney, clientAccount);
    }

    public void depositMoney(Map<Integer,Integer> insertedMoney,ClientAccount clientAccount){
        int depositedSum = 0;
//        insertedMoney.forEach((k,v)->{
//            withdrawCassettes.get(k).setBankNoteAmount(withdrawCassettes.get(k).getBankNoteAmount() + v); });
        for (Map.Entry<Integer,Integer> pair : insertedMoney.entrySet()) {
            withdrawCassettes.get(pair.getKey()).setBankNoteAmount(withdrawCassettes.get(pair.getKey()).getBankNoteAmount() + pair.getValue());
            depositedSum += pair.getKey() * pair.getValue();
        }
        clientAccount.setBalance(clientAccount.getBalance().add(new BigDecimal(depositedSum)));
    }

    @Override
    public void displayBalance() {
        display.showBalance(displayBalance(clientAccount));
    }

    public String displayBalance(ClientAccount clientAccount){
        return dbService.getClientAccountById(clientAccount.getAccountId()).getBalance().toString();
    }

    @Override
    public long getTotalSum() {
        totalSum = 0;
        withdrawCassettes.forEach((k,v)-> totalSum += v.getCassetteSum());
        return totalSum;
    }

    @Override
    public void finishSession() {
        accountId = 0;
        operation = null;
        clientAccount = null;
        isPinValid = false;
        isSessionFinished = true;
    }

    private void validatePIN(String pin) throws IncorrectPinFormatException {
        if (pin.length() == 4) {
            if (pin.equals(dbService.getPinByAccountId(this.accountId))){
                isPinValid = true;
                logger.info("PIN is correct");
            }
            else{ isPinValid = false;
                logger.info("PIN isn't correct. Make another try.");}
        }
        else throw new IncorrectPinFormatException();
    }

    private void startSession(){
        isSessionFinished = false;
        clientAccount = dbService.getClientAccountById(accountId);

        while(!isSessionFinished) {
            display.showMainMenu();
            int userChoice = Integer.parseInt(keyboard.getOperationNumber());
            while (userChoice < 1 || userChoice > 4) {
                logger.info("Please enter valid operation number");
                userChoice = Integer.parseInt(keyboard.getOperationNumber());
            }
            operation = Operation.getOperationById(userChoice);
            processOperationChoice(operation);
        }
    }

    private void processOperationChoice(Operation operation){
        switch (operation){
            case WITHDRAW:
                withdrawMoney();
                break;
            case DEPOSIT:
                depositMoney();
                break;
            case BALANCE:
                displayBalance();
                break;
            case EXIT:
                finishSession();
                break;
        }
    }

    private void ejectBanknotesFromCassettes(Map<Integer, Integer> tempDispenserMap) {
        tempDispenserMap.forEach((k,v) -> withdrawCassettes.get(k).setBankNoteAmount(withdrawCassettes.get(k).getBankNoteAmount() - tempDispenserMap.get(k)));
    }

    public ClientAccount getClientAccount(int clientAccountId){
        clientAccount = dbService.getClientAccountById(clientAccountId);
        return clientAccount;
    }
}
