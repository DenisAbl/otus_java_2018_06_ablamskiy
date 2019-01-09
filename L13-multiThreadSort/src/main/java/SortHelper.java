import java.util.Random;

public class SortHelper {

    public static int[] createRandomArray(int numberOfElements){
        Random random = new Random(System.currentTimeMillis());
        int[] array = new int[numberOfElements];
        for (int i = 0; i < numberOfElements; i++){
            array[i] = -1000000 + random.nextInt(2000000);
        }
        return array;
    }


}
