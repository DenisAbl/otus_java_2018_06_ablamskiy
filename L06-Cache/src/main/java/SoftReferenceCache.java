import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import java.util.function.Function;

public class SoftReferenceCache<K,V> implements Cache<K,V> {
    private static final int TIME_THRESHOLD_MS = 5;

    private final long size;
    private final int lifeTimeLimitMs;
    private final int idleTimeLimitMs;
    private final boolean isEternal;
    private int misses;
    private int hits;

    private final Timer timer = new Timer();

    private volatile Map<K,CacheElement<V>> cacheMap = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(SoftReferenceCache.class);

    public SoftReferenceCache(long size, int lifeTimeLimitMs, int idleTimeLimitMs, boolean isEternal){
        this.size = size;
        this.lifeTimeLimitMs = lifeTimeLimitMs > 0 ? lifeTimeLimitMs : 0;
        this.idleTimeLimitMs = idleTimeLimitMs > 0 ? idleTimeLimitMs : 0;
        this.isEternal = (lifeTimeLimitMs == 0 && idleTimeLimitMs == 0) || isEternal;

        logger.info("Cache created");
    }

    @Override
    public void addElement(K key, V element) {
        if (cacheMap.size() == size){
            K firstElementKey = cacheMap.keySet().iterator().next();
            cacheMap.remove(firstElementKey);
        }
        cacheMap.put(key, new CacheElement<V>(element));
        updateCache(key);
        logger.info("New element added");
    }

    @Override
    public V getElement(K key) {
        CacheElement<V> element = cacheMap.get(key);
        if (element == null || element.get() == null) {
            misses++;
            return null;
        }
        else{
            element.setLastAccessTime();
            hits++;
            return element.get();}
    }

    @Override
    public void updateCache(K key) {

        if (!isEternal) {
            if (lifeTimeLimitMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getElementCreationTime() + lifeTimeLimitMs);
                timer.schedule(lifeTimerTask, lifeTimeLimitMs);
            }
            if (idleTimeLimitMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeLimitMs);
                timer.schedule(idleTimerTask, idleTimeLimitMs, idleTimeLimitMs);
            }
        }
        else {cacheMap.entrySet().removeIf(entry -> entry.getValue() == null || entry.getValue().get() == null);}

    }

    public long getSize() {
        return cacheMap.size();
    }

    public void cacheShutdown() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<V> element = cacheMap.get(key);
                if (element == null) {cacheMap.remove(key);}
                else if (element.get() == null || isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                   cacheMap.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    public int getHitCount() {
        return hits;
    }

    public int getMissCount() {
        return misses;
    }

}
