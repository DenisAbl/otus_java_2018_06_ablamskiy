import java.util.function.Supplier;

public class TestMemory {

    final private static int NUMBER_OF_OBJECTS = 20_000_000;

    public static void main(String[] args) {
        Supplier<String> supplier = () -> new String(new byte[0]);
        System.out.println(MySizeOf.sizeOf(supplier));
    }
}
