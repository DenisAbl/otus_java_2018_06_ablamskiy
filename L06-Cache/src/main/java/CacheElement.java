import java.lang.ref.SoftReference;
import java.util.Objects;

public class CacheElement<V>{

    private SoftReference<V> value;
    private long elementCreationTime;
    private long lastAccessTime;

    public CacheElement(V referent) {
        this.value = new SoftReference<>(referent);
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
