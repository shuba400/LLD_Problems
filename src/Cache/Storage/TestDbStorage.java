package Cache.Storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestDbStorage<K,V> implements DBStorage<K,V> {
    Map<K,V> map = new ConcurrentHashMap<>();

    @Override
    public void write(K key, V value) throws Exception {
        map.put(key,value);
    }

    @Override
    public V read(K key) throws Exception {
        if(!map.containsKey(key)){
            throw new Exception("Key not existing");
        }
        return map.get(key);
    }

    @Override
    public void delete(K key) throws Exception {
        if(!map.containsKey(key)){
            throw new Exception("Key not existing");
        }
        map.remove(key);
    }
}
