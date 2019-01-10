import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class SortHelper {

    public static int[] createRandomArray(int numberOfElements){
        Random random = new Random(System.currentTimeMillis());
        int[] array = new int[numberOfElements];
        for (int i = 0; i < numberOfElements; i++){
            array[i] = -1000000 + random.nextInt(2000000);
        }
        return array;
    }

   public static List<int[]> getDividedArrays(int[] sourceArray, int partsNumber){
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

    public static int[] mergeSortedArrays(int[] a, int[] b){

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


}
