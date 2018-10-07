package courses.otus.models;
import org.jetbrains.annotations.*;

public class Cassette {

    public static final int FIFTYS = 50;
    public static final int ONE_HUNDREDS = 100;
    public static final int TWO_HUNDREDS = 200;
    public static final int FIVE_HUNDREDS = 500;
    public static final int ONE_THOUSANDS = 1000;
    public static final int TWO_THOUSANDS = 2000;
    public static final int FIVE_THOUSANDS = 5000;

    private final int nominalValue;

    private int bankNoteAmount;

    Cassette(int denominationValue, int bankNoteAmount){
        this.nominalValue = denominationValue;
        this.bankNoteAmount = bankNoteAmount;
    }

    int getNominalValue() {
        return nominalValue;
    }

    int getBankNoteAmount() {
        return bankNoteAmount;
    }

    void setBankNoteAmount(int bankNoteAmount) {
        this.bankNoteAmount = bankNoteAmount;
    }

    int getCassetteSum(){
        return nominalValue * bankNoteAmount;
    }
}
