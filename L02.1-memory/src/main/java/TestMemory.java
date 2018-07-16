import java.util.function.Supplier;

public class TestMemory {


    public static void main(String[] args) {

        MySizeOf.sizeOf(() -> new String(new byte[0]));
        MySizeOf.sizeOf(() -> new Integer(123));

    }
}
