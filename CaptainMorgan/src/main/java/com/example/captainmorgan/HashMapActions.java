package com.example.captainmorgan;

public interface HashMapActions<K,V> {
    void put(K key, V value);
    V get(K key);
    boolean contains(K key);
    boolean containsValue(V value);
    V remove(K key);
    int size();
    void clear();
}
