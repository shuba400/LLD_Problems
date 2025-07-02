package Cache.Util;

import org.w3c.dom.Node;

public class DoubleLinkedList<K> {
    private final DoubleLinkedListNode<K> head = new DoubleLinkedListNode<K>(null);
    private final DoubleLinkedListNode<K> tail = new DoubleLinkedListNode<K>(null);

    public DoubleLinkedList(){
        head.next = tail;
        tail.prev = head;
    }

    public void addNodeFront(DoubleLinkedListNode<K> node){
        DoubleLinkedListNode<K> tmp = head.next;
        head.next = node;
        node.prev = node;
        node.next = tmp;
        tmp.prev = node;
    }



    public void removeNode(DoubleLinkedListNode<K> node){
        DoubleLinkedListNode<K> tmpBefore = node.prev;
        DoubleLinkedListNode<K> tmpAfter = node.next;

        tmpAfter.prev = tmpBefore;
        tmpBefore.next = tmpAfter;

        node.prev = null;
        node.next = null;
    }


    public DoubleLinkedListNode<K> getNodeBack(){
        return tail.prev;
    }
    public DoubleLinkedListNode<K> getNodeFront(){
        return head.next;
    }

    public DoubleLinkedListNode<K> removeNodeBack(){
        DoubleLinkedListNode<K> node = getNodeBack();
        removeNode(getNodeBack());
        return node;
    }

}
