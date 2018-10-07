package courses.otus.models;


import java.util.Scanner;

public class Keyboard {

    private Scanner scanner = new Scanner(System.in);


    String getPIN(){
        return scanner.nextLine();

    }

    String getOperationNumber(){
        return scanner.nextLine();

    }

    public String getMoneyAmount() {
        return scanner.nextLine();
    }
}
