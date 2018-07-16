import java.util.function.Supplier;

public class MySizeOf {


    final static int NUMBER_OF_OBJECTS = 20_000_000;
    private static long startMemory = 0;
    private static long finishMemory = 0;

    public static void sizeOf(Supplier<?> supplier){

        startMemory = getUsedMemory();
        Object[] arrayOfObjects = new Object[NUMBER_OF_OBJECTS];
        finishMemory = getUsedMemory();
        System.out.println("Reference size: " + (finishMemory - startMemory)/arrayOfObjects.length + " bytes");

        startMemory = getUsedMemory();

         for (int i = 0; i < arrayOfObjects.length; i++){
             arrayOfObjects[i] = supplier.get();
         }
         finishMemory = getUsedMemory();

        System.out.println("Object size: " + (finishMemory - startMemory)/arrayOfObjects.length + " bytes");
    }

    private static long getUsedMemory() {
        System.gc();
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.getRuntime().freeMemory());
    }

}
