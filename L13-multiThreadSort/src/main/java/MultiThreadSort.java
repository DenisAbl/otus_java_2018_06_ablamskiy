import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadSort {


    public static int[] multiThreadSort(int[] sourceArray, int threadsNumber) throws InterruptedException {

        List<int[]> arrays = getDividedArrays(sourceArray,threadsNumber);
        sortDividedArrays(arrays, threadsNumber);
        while(arrays.size() != 1){
            arrays.add(mergeSortedArrays(arrays.get(0),arrays.get(1)));
            arrays.subList(0,2).clear();
        }
        return arrays.get(0);
    }

    private static int[] mergeSortedArrays(int[] a, int[] b){

        int[] mergedArray = new int[a.length + b.length];
        int indexA = 0;
        int indexB = 0;
        int mergedArrayIndex = 0;
        while (indexA < a.length && indexB < b.length){
            if (a[indexA] < b[indexB]){
                mergedArray[mergedArrayIndex++] = a[indexA++];
            }
            else {
                mergedArray[mergedArrayIndex++] = b[indexB++];
            }
        }
        while (indexA < a.length){
            mergedArray[mergedArrayIndex++] = a[indexA++];
        }
        while (indexB < b.length){
            mergedArray[mergedArrayIndex++] = b[indexB++];
        }
        return mergedArray;
    }

    private static List<int[]> getDividedArrays(int[] sourceArray, int partsNumber){
        List<int[]> list = new ArrayList<>();
        int partSize = sourceArray.length / partsNumber;
        int finishIndex = 0;
        for (int startIndex = 0; startIndex + partSize <= sourceArray.length; startIndex += partSize){
            finishIndex += partSize;
            if((finishIndex + partSize) > sourceArray.length)
                 list.add(Arrays.copyOfRange(sourceArray,startIndex,sourceArray.length));
            else list.add(Arrays.copyOfRange(sourceArray,startIndex,finishIndex));
        }
         return list;
    }

    private static void sortDividedArrays(List<int[]> arrays, int threadsNumber) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        arrays.forEach(array ->
                executor.submit(() -> Arrays.sort(array)));

        executor.shutdown();
        executor.awaitTermination(10000,TimeUnit.MILLISECONDS);
    }

}
