import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SoftReferenceCache<K,V> implements Cache<K,V> {

    private final long size;
    private final int lifeTimeLimitMs;
    private final int idleTimeLimitMs;
    private final boolean isEternal;
    private long misses;
    private long hits;

    ScheduledExecutorService executor;

    private volatile Map<K,CacheElement<V>> cacheMap = new ConcurrentHashMap<>();

    private static Logger logger = LoggerFactory.getLogger(SoftReferenceCache.class);

    public SoftReferenceCache(long size, int lifeTimeLimitMs, int idleTimeLimitMs, boolean isEternal){
        this.size = size;
        this.lifeTimeLimitMs = lifeTimeLimitMs > 0 ? lifeTimeLimitMs : 0;
        this.idleTimeLimitMs = idleTimeLimitMs > 0 ? idleTimeLimitMs : 0;
        this.isEternal = (lifeTimeLimitMs == 0 && idleTimeLimitMs == 0) || isEternal;

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::updateCache,50,50,TimeUnit.MILLISECONDS);

        logger.info("Cache created");
    }

    @Override
    public void addElement(K key, V element) {
        if (cacheMap.size() == size){
            K firstElementKey = cacheMap.keySet().iterator().next();
            cacheMap.remove(firstElementKey);
        }
        cacheMap.put(key, new CacheElement<V>(element));
        logger.info("New element added");
    }

    @Override
    public V getElement(K key) {
        CacheElement<V> element = cacheMap.get(key);
        if (element == null || element.get() == null) {
            cacheMap.remove(key);
            misses++;
            return null;
        }
        else
            hits++;
            element.setLastAccessTime();
        return element.get();
    }

    @Override
    public synchronized void updateCache() {
        if (isEternal){
            cacheMap.entrySet().removeIf(entry -> (entry.getValue().get() == null));
            logger.info("Cache refreshing finished");}

        if(idleTimeLimitMs == 0){
            cacheMap.entrySet().removeIf(entry -> (entry.getValue().getElementLifeTime() >= lifeTimeLimitMs));
            logger.info("Cache refreshing finished");}

        if (lifeTimeLimitMs == 0){
            cacheMap.entrySet().removeIf(entry -> (entry.getValue().getElementIdleTime() >= idleTimeLimitMs));
            logger.info("Cache refreshing finished");}
    }

    public long getSize() {
        return cacheMap.size();
    }

    public void cacheShutdown() {
        executor.shutdown();
    }

    public long getMissCount() {
        return misses;
    }

    public long getHitCount() {
        return hits;
    }
}
