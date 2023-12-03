import java.util.ArrayList;

public class hashtab<K, V> {
    private static class LinkedListNode<K, V> {
        public LinkedListNode<K, V> next;
        public LinkedListNode<K, V> prev;
        public K key;
        public V value;
        public LinkedListNode(K k, V v) {
            key = k;
            value = v;
        }
        public String printForward() {//вывод узла
            String data = "(" + key + "," + value + ")";
            if (next != null) {
                return data + "->" + next.printForward();
            } else {
                return data;
            }
        }

    }
    private ArrayList<LinkedListNode<K, V>> arr;
    public hashtab(int capacity) {//hashtabHash()
        arr = new ArrayList<LinkedListNode<K, V>>();//создание связного списка и заполнение его null
        arr.ensureCapacity(capacity);
        for (int i = 0; i < capacity; i++) {
            arr.add(null);
        }
    }
    public V put(K key, V value) {//вставка в хештаблицу (hashtabAdd() )
        LinkedListNode<K, V> node = getNodeForKey(key);
        if (node != null) {
            V oldValue = node.value;
            node.value = value; // Просто обновить значение.
            return oldValue;
        }
        node = new LinkedListNode<K, V>(key, value);
        int index = getIndexForKey(key);
        if (arr.get(index) != null) {
            node.next = arr.get(index);
            node.next.prev = node;
        }
        arr.set(index, node);
        return null;
    }
    public V remove(K key) {//удаление из хештаблицы(hashtabDelete)
        LinkedListNode<K, V> node = getNodeForKey(key);
        if (node == null) {
            return null;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            /* Удаление начального узла - обновление. */
            int hashKey = getIndexForKey(key);
            arr.set(hashKey, node.next);
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }
        return node.value;
    }
    public V get(K key) {
        if (key == null) return null;
        LinkedListNode<K, V> node = getNodeForKey(key);
        return node == null ? null : node.value;
    }
    private LinkedListNode<K, V> getNodeForKey(K key) {//узел для заданного ключа
        int index = getIndexForKey(key);
        LinkedListNode<K, V> current = arr.get(index);
        while (current != null) {
            if (current.key == key) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
    public int getIndexForKey(K key) {//функция для связывания ключа с индексом
        return Math.abs(key.hashCode() % arr.size());
    }

    public void printTable() {//вывод всей таблицы
        for (int i = 1; i < arr.size(); i++) {
            String s = arr.get(i) == null ? "" : arr.get(i).printForward();
            System.out.println(i + ": " + s);
        }
    }
    public void hashtabLookup(){
        System.out.println(arr.get(0).printForward());
    }
}