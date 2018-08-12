import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

import static java.lang.management.ManagementFactory.getGarbageCollectorMXBeans;

public class GcMonitor {

    public static void startGCMonitoring() {
        //Получение бинов сборщиков мусора для Young Generation и Old Generation
        List<GarbageCollectorMXBean> garbageCollectorBeansList = getGarbageCollectorMXBeans();

        //Установка слушателей событий для обоих сборщиков мусора
        for (GarbageCollectorMXBean gcBean : garbageCollectorBeansList) {
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            emitter.addNotificationListener(new GcNotificationListener(), null, null);
        }
    }
}
