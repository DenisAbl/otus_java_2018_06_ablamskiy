import java.lang.ref.SoftReference;
import java.util.Objects;

public class CacheElement<V> extends SoftReference<V> {

    private long elementCreationTime;
    private long lastAccessTime;

    public CacheElement(V referent) {
        super(referent);
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

}
