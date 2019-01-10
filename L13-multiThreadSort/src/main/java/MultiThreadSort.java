import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadSort {

    public static int[] intArraySort(int[] sourceArray, int threadsNumber) throws InterruptedException {

        if (threadsNumber <= 0) {
            throw new IncorrectThreadNumberException("Thread quantity should be be a positive number.");
        }

        if(threadsNumber > sourceArray.length) threadsNumber = sourceArray.length;

        List<int[]> arrays = SortHelper.getDividedArrays(sourceArray,threadsNumber);
        sortDividedArrays(arrays, threadsNumber);
        while(arrays.size() != 1){
            arrays.add(SortHelper.mergeSortedArrays(arrays.get(0),arrays.get(1)));
            arrays.subList(0,2).clear();
        }
        return arrays.get(0);
    }

    private static void sortDividedArrays(List<int[]> arrays, int threadsNumber) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        arrays.forEach(array ->
                executor.submit(() -> Arrays.sort(array)));

        executor.shutdown();
        executor.awaitTermination(10000,TimeUnit.MILLISECONDS);
    }

}
