public class DoublyLL <K,V>{
    Node<K,V> head;
    Node<K,V> tail;

    DoublyLL(){
        this.head = null;
        this.tail = null;
    }

    public Node<K,V> insertAtHead(K key, V value){

        Node<K,V> node = new Node<K,V>(key,value);

        if(this.head == null){
            this.head = node;
            this.tail = node;
        }else{
            node.next = this.head;
            this.head.prev = node;
            this.head = node;
        }

        return node;
    }

    public void moveToFront(Node<K,V> node){
        removeNode(node);
        if(this.head == null){
            this.head = node;
            this.tail = node;
        }else{
            node.next = this.head;
            this.head.prev = node;
            this.head = node;
        }
    }

    public void removeNode(Node<K,V> node){
        if(node == this.head && node ==this.tail){
            this.head = null;
            this.tail = null;
        }else if(node == this.head){
            Node<K,V> nextNode = node.next;
            nextNode.prev = null;
            node.next = null;
            this.head = nextNode;
        }else if(node == this.tail){
            Node<K,V> prevNode = node.prev;
            prevNode.next = null;
            node.prev = null;
            this.tail = prevNode;
        }else{
            Node<K,V> nextNode = node.next;
            Node<K,V> prevNode = node.prev;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            node.next = null;
            node.prev = null;
        }
    }

    public void printList(){
        Node<K,V> node = head;
        while(node!=null){
            System.out.print(node.value + "-->");
            node = node.next;
        }
    }

    public Node<K,V> getLRUNode(){
        return this.tail;
    }
}
