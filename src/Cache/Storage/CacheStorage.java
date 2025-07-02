package Cache.Storage;

public interface CacheStorage<K,V> {
    void put(K key,V value) throws Exception;
    V get(K key) throws Exception;
    void delete(K key) throws  Exception;
    int size() throws Exception;
    int getCapacity();
    boolean contains(K key);
}
