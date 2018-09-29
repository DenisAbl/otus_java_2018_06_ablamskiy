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

    public Cassette(int denominationValue, int bankNoteAmount){
        this.nominalValue = denominationValue;
        this.bankNoteAmount = bankNoteAmount;
    }

    public int getNominalValue() {
        return nominalValue;
    }

    public int getBankNoteAmount() {
        return bankNoteAmount;
    }

    public void setBankNoteAmount(int bankNoteAmount) {
        this.bankNoteAmount = bankNoteAmount;
    }

    public int getCassetteSum(){
        return nominalValue * bankNoteAmount;
    }
}
