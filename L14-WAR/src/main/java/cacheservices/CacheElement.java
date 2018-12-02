package cacheservices;

import java.lang.ref.SoftReference;

public class CacheElement<V>{

    private SoftReference<V> value;
    private long elementCreationTime;
    private long lastAccessTime;

    public CacheElement(V referent) {
        value = new SoftReference<V>(referent);
        elementCreationTime = lastAccessTime = System.currentTimeMillis();
    }

    public long getElementLifeTime() {
        return System.currentTimeMillis() - elementCreationTime;
    }

    public long getElementIdleTime() {
        return System.currentTimeMillis() - lastAccessTime;
    }

    public void setLastAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }

    public long getElementCreationTime() {
        return elementCreationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public V get(){
        return value.get();
    }

}
