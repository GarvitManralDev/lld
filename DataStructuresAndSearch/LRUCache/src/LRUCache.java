import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class LRUCache <K,V>{
    private final int capacity;
    private final Map<K,Node<K,V>> map;
    private final DoublyLL<K,V> dll;

    public LRUCache(int capacity){
        if(capacity <= 0){
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.dll = new DoublyLL<>();
    }

    public synchronized void put(K key, V value){

        if(map.containsKey(key)){
            Node<K,V> node = map.get(key);
            node.value = value;
            dll.moveToFront(node);
        }else{
            if(map.size() == this.capacity){
                Node<K,V> node = dll.getLRUNode();
                K nodeKey = node.key;
                map.remove(nodeKey);
                dll.removeNode(node);
            }
            Node<K,V> node = dll.insertAtHead(key,value);
            map.put(key,node);
        }
    }

    public synchronized V get(K key){
        if(!map.containsKey(key)){
            return null;
        }else{
            Node<K,V> node = map.get(key);
            dll.moveToFront(node);
            return node.value;
        }
    }

    public void printCache(){
        dll.printList();
    }
}
