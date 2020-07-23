package com.ds_algo.m_priorityQueue;

import com.ds_algo.k_heap.BinaryHeap;

import java.util.Comparator;

/**
 * 优先级队列
 * 普通队列 :FIFO原则，先进先出
 * 优先级队列 :按照优先级的高低进行 出队列, 比如将高优先级的元素最为队头
 *
 * 实现的数据结构 : 二叉堆
 */

public class PriorityQueue<E> {
    private BinaryHeap<E> heap;

    public PriorityQueue(Comparator<E> comparator){
        this.heap = new BinaryHeap<E>(comparator);
    }
    public PriorityQueue() {
        this(null);
    }

    public int size(){
        return heap.size();
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }
    public void clear(){
        heap.clear();
    }

    public void enQueue(E element){
        heap.add(element);
    }

    public E deQueue(){
        return heap.remove();
    }

    public E front(){
       return heap.get();
    }

}
