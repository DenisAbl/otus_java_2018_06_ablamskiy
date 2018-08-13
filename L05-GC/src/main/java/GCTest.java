import java.util.ArrayList;
import java.util.List;

/*
-Xms512m
-Xmx512m
-XX:+UseSerialGC
-XX:+UseSerialGC

-Xms512m
-Xmx512m
-XX:+UseParallelGC
-XX:+UseParallelOldGC

-Xms512m
-Xmx512m
-XX:+UseConcMarkSweepGC

-Xms512m
-Xmx512m
-XX:+UseG1GC

 */

public class GCTest {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> integerList = new ArrayList<>();
        GcMonitor.startGCMonitoring();
        while (true){
            integerList.add(123);
            integerList.add(456);
            integerList.remove(integerList.size()-1);
            if(integerList.size()%1000==0) Thread.sleep(6);
        }
    }
}
