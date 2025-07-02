package Cache.Policy;

public interface EvictionPolicy<K> {
    void keyAccessed(K key);
    K evictKey();
}
