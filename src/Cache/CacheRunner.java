package Cache;

import Cache.Policy.LRUEvictionPolicy;
import Cache.Util.DoubleLinkedList;

public class CacheRunner {
    public Cache<String,String> cache;

    public CacheRunner(){
        cache = new Cache<String, String>(4,2);
    }

    public void run() throws Exception {
        cache.put("A","Hey there my name is marko").join();
        cache.put("B","B style is here").join();
        cache.put("C","C is still alive").join();
        cache.put("D","D in the meeting").join();
        if(cache.cacheStorage.size() != 4){
            throw new RuntimeException("Cache not of expected size");
        }
        cache.put("E","E is coming iiin").join();
        if(cache.cacheStorage.contains("A")){
            throw new RuntimeException("A is there");
        }
        if(!cache.cacheStorage.contains("E")){
            throw new RuntimeException("E is not there");
        }


        String s = cache.access("B").join();
        cache.put("F","F is for Full").join();
        if(!cache.cacheStorage.contains("F")){
            throw new RuntimeException("F is not there");
        }
        if(cache.cacheStorage.contains("C")){
            throw new RuntimeException("C is there");
        }
        if(!cache.cacheStorage.contains("B")){
            throw new RuntimeException("B is not there 222222");
        }
        cache.shutDown();

    }
}
