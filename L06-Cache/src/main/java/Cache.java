public interface Cache<K,V> {

  void addElement(K key, V element);

  V getElement(K key);

  void updateCache(K key);

}
