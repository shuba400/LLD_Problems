package Cache.Policy;

import Cache.Storage.CacheStorage;
import Cache.Storage.DBStorage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

public class WriteThroughPolicy<K,V> implements WritePolicy<K,V> {
    @Override
    public void write(K key, V value, CacheStorage<K, V> cacheStorage, DBStorage<K, V> dbStorage) {
        CompletableFuture<Void> addToCache = CompletableFuture.runAsync(() -> {
            try {
                cacheStorage.put(key,value);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });

        CompletableFuture<Void> addToDb = CompletableFuture.runAsync(() -> {
            try {
                dbStorage.write(key,value);
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
        CompletableFuture.allOf(addToDb,addToCache).join();
    }
}
