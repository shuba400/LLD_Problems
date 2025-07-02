package Cache.Storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCacheStorage<K,V> implements CacheStorage<K,V> {

    private Map<K,V> cache;
    private final int capacity;

    public InMemoryCacheStorage(int capacity){
        this.capacity = capacity;
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void put(K key, V value) throws Exception {
        cache.put(key,value);
    }

    @Override
    public V get(K key) throws Exception {
        if(!cache.containsKey(key)){
            throw new Exception("Key not in map");
        }
        return cache.get(key);
    }

    @Override
    public void delete(K key) throws Exception {
        if(!cache.containsKey(key)){
            throw new Exception("Key not in map");
        }
        cache.remove(key);
        return;
    }

    @Override
    public int size() throws Exception {
        return cache.size();
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public boolean contains(K key) {
        return cache.containsKey(key);
    }
}
