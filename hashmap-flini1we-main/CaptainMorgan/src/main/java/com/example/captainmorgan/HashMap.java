package com.example.captainmorgan;

class HashMap<K,V> implements HashMapActions<K,V> {
    private Node<K, V>[] bucket;
    Node<K, V>[] newBucket;
    private int size;
    private final float loadFactor = 0.75F;

    public HashMap() {
        this.bucket = new Node[4];
        this.size = 0;
    }

    public void put(K key, V value){
        int hash = key.hashCode();

        if (hash == 0) {
            throw new IllegalArgumentException("Key must have a non-zero hash code.");
        }

        int bucketIndex = hash % bucket.length;

        Node<K, V> current = bucket[bucketIndex];
        while (current != null){
            if(current.getKey().hashCode() == hash) {
                if (current.getKey().equals(key)) {
                    current.setValue(value);
                    return;
                }
                current = current.getNext();
            }
        }

        Node<K,V> newNode = new Node<>(hash,key,value);
        newNode.setNext(bucket[bucketIndex]);
        bucket[bucketIndex] = newNode;
        size++;

        if(size > loadFactor * bucket.length){
            resize(bucket.length * 2);
        }
    }
    private void resize(int newSize){
        newBucket = new Node[newSize];
        for(Node<K, V> node : bucket){
            while (node != null) {
                int newIndex = node.getHash() % newSize;
                newBucket[newIndex] = node;
                node = node.getNext();
            }
        }
        bucket = newBucket;
    }

    public V get(K key){
        int hash = key.hashCode();

        int bucketIndex = hash % bucket.length;

        Node<K, V> current = bucket[bucketIndex];
        while (current != null){
            if(current.getKey().equals(key)){
                return current.getValue();
            }
            current = current.getNext();
        }
        return null;
    }

    public boolean contains(K key){
        int hash = key.hashCode();


        int bucketIndex = hash % bucket.length;

        Node<K , V> current = bucket[bucketIndex];
        while (current != null){
            if(current.getKey().equals(key)){
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean containsValue(V value){
        Node<K,V>[] current = this.bucket;

        for(Node<K,V> node : current){
            while (node != null){
                if(node.getValue().equals(value) ||
                        node.getValue() == value){
                    return true;
                }
                node = node.getNext();
            }
        }
        return false;
    }

    public V remove(K key) {
        int hash = key.hashCode();

        int indexTable = hash % size;

        Node<K, V> current = bucket[indexTable];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.getKey().hashCode() == hash && current.getKey().equals(key)) {
                if (prev == null) {
                    bucket[indexTable] = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                size--;

                if(size < loadFactor * bucket.length){
                    resize((int)(bucket.length / 1.5));
                }

                return current.getValue();
            }

            prev = current;
            current = current.getNext();
        }
        return null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        bucket = new Node[4];
        size = 0;
    }


    public void MaptoString() {
        StringBuilder sb = new StringBuilder("{");
        for (Node<K, V> node : bucket) {
            while (node != null) {
                sb.append(node.getKey()).append("=").append(node.getValue());
                if (node.getNext() != null) {
                    sb.append(", ");
                }
                node = node.getNext();
            }
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 2, sb.length());
        }
        System.out.println(sb.append("}"));
    }
}
