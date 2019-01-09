import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;


public class MultiThreadSortTest {

     int[] c = {1,2,3,12,25,49,78,97,100,897,1000,1001,22,48,74,75,78,98,100,225,7856,8257,34,32,-3};

    @Test
    public void multiThreadSortTest() throws InterruptedException {
        int[] result;
        int[] source = SortHelper.createRandomArray(10_000_000);
        long start = System.nanoTime();
        result = MultiThreadSort.intArraySort(source,6);
        System.out.println((System.nanoTime() - start) / 1_000_000 + " ms");
        Arrays.sort(source);
        Assert.assertArrayEquals(result,source);

    }
    @Test
    public void SortTest() {
        long start = System.nanoTime();
        int[] source = SortHelper.createRandomArray(10_000_000);
        Arrays.sort(source);
        System.out.println((System.nanoTime() - start)/1_000_000 + " ms");
    }
}
