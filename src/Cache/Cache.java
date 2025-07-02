package Cache;

import Cache.Policy.EvictionPolicy;
import Cache.Policy.LRUEvictionPolicy;
import Cache.Policy.WritePolicy;
import Cache.Policy.WriteThroughPolicy;
import Cache.Storage.CacheStorage;
import Cache.Storage.DBStorage;
import Cache.Storage.InMemoryCacheStorage;
import Cache.Storage.TestDbStorage;
import Cache.Util.Executor;

import java.security.Key;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Supplier;

public class Cache<K,V> {
    WritePolicy<K,V> writePolicy;
    DBStorage<K,V> dbStorage;
    public CacheStorage<K,V> cacheStorage;
    EvictionPolicy<K> evictionPolicy;
    Executor<K> executor;
    int capacity;

    public Cache(int capacity,int thread_capacity){
        this.capacity = capacity;
        writePolicy = new WriteThroughPolicy<>();
        evictionPolicy = new LRUEvictionPolicy<>();
        cacheStorage = new InMemoryCacheStorage<>(capacity);
        dbStorage = new TestDbStorage<>();
        executor = new Executor<>(thread_capacity);
    }

    public CompletableFuture<Void> put(K key, V value){
        return executor.execute(key,() -> {
//            System.out.println("Executing put from " + Thread.currentThread().threadId());
            try {
                if(cacheStorage.contains(key)) {
                    writePolicy.write(key, value, cacheStorage, dbStorage);
                    evictionPolicy.keyAccessed(key);
                    return null;
                }
                if(cacheStorage.size() == cacheStorage.getCapacity()) {
                    K evictedKey = evictionPolicy.evictKey();
                    cacheStorage.delete(evictedKey);
                }
                writePolicy.write(key,value,cacheStorage,dbStorage);
                evictionPolicy.keyAccessed(key);
//                System.out.println("Finishing put from " + Thread.currentThread().threadId());
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<V> access(K key){
        return executor.execute(key,() -> {
            try {
//                System.out.println("Executing access from " + Thread.currentThread().threadId());
                if(!cacheStorage.contains(key)){
                    throw new Exception("Key not present");
                }
                evictionPolicy.keyAccessed(key);
//                System.out.println("Finishing access from " + Thread.currentThread().threadId());
                return cacheStorage.get(key);
            } catch (Exception e) {
//                System.out.printf("Rasied err %s",e);
                throw new RuntimeException(e);
            }
        });
    }

    public void shutDown(){
        executor.shutDown();
        return;
    }
}
