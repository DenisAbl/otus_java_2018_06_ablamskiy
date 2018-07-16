import java.util.function.Supplier;

public class MySizeOf {

    private final static int NUMBER_OF_OBJECTS = 20_000_000;
    private static long startMemory = 0;
    private static long finishMemory = 0;

 //Метод на вход принимает в качестве фабрики реализацию функционального интрефейса Supplier

    public static void sizeOf(Supplier<?> supplier) throws InterruptedException {

        startMemory = getUsedMemory();
        Object[] arrayOfObjects = new Object[NUMBER_OF_OBJECTS];
        finishMemory = getUsedMemory();
        System.out.println("Reference size: " + (finishMemory - startMemory)/arrayOfObjects.length + " bytes");

        startMemory = getUsedMemory();
        for (int i = 0; i < arrayOfObjects.length; i++){
             arrayOfObjects[i] = supplier.get(); }
        finishMemory = getUsedMemory();
        System.out.println(supplier.get().getClass().getTypeName() + " size: " + (finishMemory - startMemory)/arrayOfObjects.length + " bytes" +'\n');

        arrayOfObjects = null;
    }

    private static long getUsedMemory() throws InterruptedException {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory());
    }

}
