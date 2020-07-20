package com.ds_algo.e_queue;

import com.ds_algo.e_queue.list.LinkedList;

/**
 * 使用双向链表实现队列
 * 特点:只能单端插入删除数据，先进先出
 *
 * @param <T>
 */
public class Queue<T> {

    private LinkedList<T> list = new LinkedList<>();

    public int size() {
        return list.size;
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 每次都添加到链表的尾部,保证之前添加的在前面
     * @param element
     */
    public void enQueue(T element) {
        list.add(element);
    }

    /**
     * 每次都出队列都删除链表的第一个元素，保证先FIFO
     */
    public T deQueue() {
        return list.remove(0);
    }

    public T front() {
        return list.get(0);
    }

    public void clear() {
        list.clear();
    }
}

