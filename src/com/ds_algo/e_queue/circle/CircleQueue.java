package com.ds_algo.e_queue.circle;
/**
 * 使用数组实现循环队列
 * @param <T>
 */
public class CircleQueue<T> {
    private int front; // 指向队头
    private int size;
    private T[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 构造函数
     */
    public CircleQueue(){
        elements = (T[])new Object[DEFAULT_CAPACITY];
        front = 0;
        size = 0;
    }

    public int size(){
        return size;
    }
    public void clear(){
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        front = 0;
        size = 0;
    }
    public boolean isEmpty(){
        return size == 0;
    }

    public void enQueue(T element){
        // 先检查是否需要扩容
        ensureCapacity(size+1);
        // 入队列逻辑 找到位置
        elements[index(size)] = element;
        size++;
    }

    public T deQueue(){
        T frontElement = elements[front];
        elements[front] = null;
        /**
         *         if (front + 1 < elements.length){
         *             front = front + 1;
         *         } else {
         *             front = (front + 1) % elements.length;
         *         }
         */
        front = index(1);
        size--;
        return frontElement;
    }

    public T front(){
        return elements[front];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CircleQueue{capacity=").append(elements.length).append(", size=").append(size).append(", front=").append(front).append(", [");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(elements[i]);
        }
        sb.append("]}");
        return sb.toString();
    }

    private void ensureCapacity(int capacity){
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        // 扩容1.5倍
        int newCapacity = oldCapacity + oldCapacity >> 1;
        T[] newElements = (T[]) new Object[newCapacity];
        for (int i = 0; i < elements.length; i++) {
            // 迁移数据
            newElements[i] = elements[index(i)];
        }
        elements = newElements;
        // 重置
        front = 0;
    }

    private int index(int index){
        int next = index + front;
        if (next < elements.length){
            return next;
        } else {
            return next % elements.length;
        }
    }
}
