package courses.otus.models;


import java.util.Scanner;

public class Keyboard {

    private Scanner scanner = new Scanner(System.in);


    public String getPIN(){
        return scanner.nextLine();

    }

    public String getOperationNumber(){
        return scanner.nextLine();

    }

    public String getMoneyAmount() {
        String moneyAmount = scanner.nextLine();
        return moneyAmount;
    }
}
