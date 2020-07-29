package com.ds_algo.k_hashTable.hashMap;

import java.util.Objects;

public class LinkedHashMap<K,V> extends HashMap<K,V>{

    LinkedNode<K,V> first;
    LinkedNode<K,V> last;

    /* 删除节点处理逻辑:
     * 1.删除节点时,红黑树中的删除仅仅删除了left 或 right指针，这里还需要处理next指针，这样才能完全断开连接
     * 2.因为删除度为2的节点时,真正删除是叶子节点,所以需要处理好以前的linked关系
     */

    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
        LinkedNode<K, V> node1 = (LinkedNode<K, V>) willNode;
        LinkedNode<K, V> node2 = (LinkedNode<K, V>) removedNode;

        // 删除红黑树节点后为了保证链表之前的顺序连接。
        // 这里将willNode和removedNode在链表中的位置做一下交换，然后再处理删除逻辑
        if (node1 != node2){
            // 可以确定两个是不同的节点,即删除的是个度为2的节点，导致的情况
            // 处理两者的prev
            LinkedNode<K, V> prevTem = node1.prev;
            node1.prev = node2.prev;
            node2.prev = prevTem;

            if (node1.prev == null){
                first = node1;
            } else {
                node1.prev.next = node1;
            }

            if (node2.prev == null){
                first = node2;
            } else {
                node2.prev.next = node2;
            }

            // 处理两者的next
            LinkedNode<K, V> nextTem = node1.next;
            node1.next = node2.next;
            node2.next = nextTem;
            if (node1.next == null){
                last = node1;
            } else {
                node1.next.prev = node1;
            }
            if (node2.next == null){
                last = node2;
            } else {
                node2.next.prev = node2;
            }
        }


        // 双向链表的节点删除
        LinkedNode<K, V> prev = node2.prev;
        LinkedNode<K, V> next = node2.next;

        if (prev == null){
            first = next;
        } else {
            prev.next = next;
        }

        if (next == null){
            last = prev;
        } else {
            next.prev = prev;
        }


    }

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (Objects.equals(value, node.value)) return true;
            node = node.next;
        }
        return false;
    }


    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        LinkedNode<K,V> node = first;
        while (node != null){
            if (visitor.visit(node.key,node.value)) return;
            node = node.next;
        }
    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode<K,V> linkedNode = new LinkedNode<>(key,value,parent);
        // 串起来
        if (first == null){
            first = last = linkedNode;
        } else {
            last.next = linkedNode;
            linkedNode.prev = last;
            last = linkedNode;
        }
        return linkedNode;
    }

    private static class LinkedNode<K,V> extends Node<K,V>{
        LinkedNode<K,V> prev;
        LinkedNode<K,V> next;
        public LinkedNode(K key, V value, Node<K, V> parent) {
            super(key, value, parent);
        }
    }
}
