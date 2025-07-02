package Cache.Policy;

import Cache.Util.DoubleLinkedList;
import Cache.Util.DoubleLinkedListNode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRUEvictionPolicy<K> implements EvictionPolicy<K>{
    public final DoubleLinkedList<K> doubleLinkedList;
    private final Map<K, DoubleLinkedListNode<K>> keyToNodeMap;

    public LRUEvictionPolicy(){
        doubleLinkedList = new DoubleLinkedList<>();
        keyToNodeMap = new HashMap<>();
    }

    @Override
    public synchronized void keyAccessed(K key) {
        DoubleLinkedListNode<K> node;
        if(keyToNodeMap.containsKey(key)){
            doubleLinkedList.removeNode(keyToNodeMap.get(key));
            node = keyToNodeMap.get(key);
        }
        else{
            node = new DoubleLinkedListNode<>(key);
        }
        doubleLinkedList.addNodeFront(node);
        keyToNodeMap.put(key,node);
    }

    @Override
    public synchronized K evictKey() {
        DoubleLinkedListNode<K> evictedNode = doubleLinkedList.removeNodeBack();
        keyToNodeMap.remove(evictedNode.getKey());
        return evictedNode.getKey();
    }
}
