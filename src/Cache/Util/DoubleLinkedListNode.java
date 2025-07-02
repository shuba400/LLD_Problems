package Cache.Util;

public class DoubleLinkedListNode<K> {
    public DoubleLinkedListNode<K> prev;
    public DoubleLinkedListNode<K> next;
    private final K key;

    public DoubleLinkedListNode(K key){
        prev = null;
        next = null;
        this.key = key;
    }

    public K getKey(){
        return key;
    }
}
