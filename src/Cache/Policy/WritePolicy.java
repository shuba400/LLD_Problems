package Cache.Policy;

import Cache.Storage.CacheStorage;
import Cache.Storage.DBStorage;

public interface WritePolicy<K,V> {
    void write(K key, V value, CacheStorage<K,V> cacheStorage,DBStorage<K,V> dbStorage);
}
