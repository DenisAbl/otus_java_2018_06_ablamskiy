import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GcNotificationListener implements NotificationListener {

    private long totalGcDuration = 0;
    private int youngGenCount = 0;
    private int oldGenCount = 0;

    @Override
    public void handleNotification(Notification notification, Object handback) {

        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            //получаем информацию связанную с событием GC
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

            String gcType = info.getGcAction();
            if (gcType.equals("end of minor GC")) youngGenCount++;
            else if(gcType.equals("end of major GC")) oldGenCount++;

            long duration = info.getGcInfo().getDuration();
            totalGcDuration += duration;

            //Логирование каждой сборки мусора в формате:
            // <тип сборщика> <тип сборки> <номер сборки по порядку для соответствующего типа> <продолжительность>
            System.out.println(info.getGcName() + " - number of GC: " + info.getGcInfo().getId() + ". Duration: " + duration + " milliseconds");
        }
    }

    public long getTotalGcDuration() {
        return totalGcDuration;
    }
}
