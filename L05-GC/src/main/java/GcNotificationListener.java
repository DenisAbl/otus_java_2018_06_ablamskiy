import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GcNotificationListener implements NotificationListener {

   private static long totalGcDuration = 0;
   private static int youngGenCount = 0;
   private static int oldGenCount = 0;
   private static int youngGenGCDuration = 0;
   private static int oldGenGCDuration = 0;


    static{Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
        System.out.println("Young Generation collections quantity: " + youngGenCount + " per minute. Duration: " + youngGenGCDuration + " ms");
        System.out.println("Old Generation collections quantity: " + oldGenCount + " per minute. Duration: " + oldGenGCDuration + " ms");
        resetCounters();

        totalGcDuration = 0;
        },
            1, 1, TimeUnit.MINUTES);}

    @Override
    public void handleNotification(Notification notification, Object handback) {

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            //получаем данные о событии от GC
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            String gcType = info.getGcAction();
            if (gcType.equals("end of minor GC")){
                youngGenCount++;
                youngGenGCDuration += info.getGcInfo().getDuration();
            }
            else if(gcType.equals("end of major GC")){
                oldGenCount++;
                oldGenGCDuration += info.getGcInfo().getDuration();
            }

            long duration = info.getGcInfo().getDuration();
            totalGcDuration += duration;

            //Логирование каждой сборки мусора в формате:
            // <тип сборщика> <тип сборки> <номер сборки по порядку для соответствующего типа> <продолжительность>
            //System.out.println(info.getGcName() + " - number of GC: " + info.getGcInfo().getId() + ". Duration: " + duration + " milliseconds");
        }
    }

    public long getTotalGcDuration() {
        return totalGcDuration;
    }

    private static void resetCounters(){
        youngGenGCDuration = 0;
        youngGenCount = 0;
        oldGenGCDuration = 0;
        oldGenCount = 0;
    }
}
