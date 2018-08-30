import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.*;

public class SoftReferenceCacheTest {



    @Test
    public void addElementsToEternalCache() throws InterruptedException {
        SoftReferenceCache<Integer,BigObject> cache = new SoftReferenceCache<>(10,0,0,true);
        range(0, 20).forEach(i -> cache.addElement(i, new BigObject()));
        System.out.println("Cache size before delay = " + cache.getSize());
        for (int i = 0; i <cache.getSize(); i++) System.out.println(cache.getElement(i));
        cache.updateCache();
        Thread.sleep(2000);
        System.out.println("Cache size after delay = " + cache.getSize());
        for (int i = 0; i <cache.getSize(); i++){
            System.out.println(cache.getElement(i)); }
//        cache.cacheShutdown();
    }

    @Test
    public void restrictedLifeTimeElementsCache() throws InterruptedException {
        SoftReferenceCache<Integer,BigObject> cache = new SoftReferenceCache<>(10,200,0,false);
        for (int i = 0; i <20; i++) cache.addElement(i, new BigObject());
        System.out.println("Cache size before delay = " + cache.getSize());
        for (int i = 0; i <cache.getSize(); i++) System.out.println(cache.getElement(i));
        Thread.sleep(2000);
        System.out.println("Cache size after delay = " + cache.getSize());
//        cache.cacheShutdown();

    }

    static class BigObject {

        final byte[] array = new byte[1024 * 1024];

        public byte[] getArray() {
            return array;
        }


        @Override
        public String toString() {
            return "BigObject";

        }
    }
}
