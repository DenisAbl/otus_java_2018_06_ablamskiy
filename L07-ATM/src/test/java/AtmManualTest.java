import courses.otus.models.ATM;
import courses.otus.models.ATMImpl;

public class AtmManualTest {

    public static void main(String[] args) {
        ATMImpl atm = new ATMImpl(1);
        atm.startWork();
    }
}
