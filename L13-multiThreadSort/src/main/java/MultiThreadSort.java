import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadSort {

    public static int[] multiThreadSort(int[] sourceArray) throws InterruptedException {
        List<int[]> arrays = getDividedArrays(sourceArray,4);
        sortDividedArrays(arrays);
        int[] temp1 = mergeSortedArrays(arrays.get(0), arrays.get(1));
        int[] temp2 = mergeSortedArrays(arrays.get(2),arrays.get(3));
        return mergeSortedArrays(temp1,temp2);
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

    private static void sortDividedArrays(List<int[]> arrays) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.submit(()->{
            arrays.forEach(Arrays::sort);
        });
        executor.shutdown();
        executor.awaitTermination(1000,TimeUnit.MILLISECONDS);
    }

}
