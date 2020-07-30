package com.ds_algo.o_trie;

import com.ds_algo.k_hashTable.hashMap.HashMap;

public class Trie<V> {
    private int size; // 单词的数量
    private Node<V> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node == null ? null : node.value;
    }

    public boolean contains(String key) {
        Node<V> node = node(key);
        return node == null ? false : node.word;
    }

    public V add(String key, V value) {
        keyCheck(key);
        // 创建根节点
        if (root == null){
            root = new Node<>(null);
        }

        Node<V> node = root;
        char[] chars = key.toCharArray();
        for (char c : chars) {
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if (childNode == null){
                childNode = new Node<>(node);
                childNode.character = c;
                if (emptyChildren){
                    node.children = new HashMap<>();
                }
                node.children.put(c,childNode);
            }
            node = childNode;
        }

        if (node.word){
            // 已经存在这个单词
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        // 新增一个单词
        node.word = true;
        node.value = value;
        size++;
        return null;
    }

    public V remove(String key) {
        keyCheck(key);
        // 找到最后一个节点
        Node<V> node = node(key);
        // 如果不是单词结尾，不用作任何处理
        if (node == null || !node.word) return null;
        size--;
        V oldValue = node.value;

        // 如果还有子节点
        if (node.children != null && !node.children.isEmpty()){
            node.word = false;
            node.value = null;
            return oldValue;
        }
        // 如果没有子节点
        Node<V> parent = null;
        while (node.parent != null){
            parent = node.parent;
            parent.children.remove(node.character);
            if (parent.word || !parent.children.isEmpty()) break;
            node = parent;
        }
        return oldValue;
    }

    public boolean startsWith(String prefix) {
        Node<V> node = node(prefix);
        return node != null;
    }

    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        char[] chars = key.toCharArray();
        for (char c : chars) {
            if (node == null || node.children == null || node.children.isEmpty()) return null;
            node = node.children.get(c);
        }
        return node;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    private static class Node<V> {
        Character character;
        boolean word;  // 是否为单词的结尾（是否为一个完整的单词）
        HashMap<Character,Node<V>> children;
        Node<V> parent;
        V value;
        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }
}
