package com.ds_algo.e_queue;
import com.ds_algo.e_queue.list.LinkedList;
import com.ds_algo.e_queue.list.List;

/**
 * 双端队列（ double end queue）:能在头尾两端添加删除的队列。每一端都像是一个单独的队列.
 * 两端都可以入队和出队。
 *
 * @param <T>
 */
@SuppressWarnings("unused")
public class Deque<T> {
    /**
     * 双向链表
     */
    private final List<T> list = new LinkedList<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 从尾部入队列
     * @param element 尾部
     */
    public void enQueueRear(T element) {
        // 添加到链表的尾部 add(size()-1,element)
        list.add(element);
    }
    /**
     * 从头出队列
     */
    public T deQueueFront() {
        return list.remove(0);
    }

    /**
     * 从头部入队列
     * @param element 元素
     */
    public void enQueueFront(T element) {
        // 添加到链表的头部
        list.add(0,element);
    }

    /**
     * 从尾部出队列
     * @return 尾部
     */
    public T deQueueRear() {
        return list.remove(list.size()-1);
    }

    /**
     * 头部
     * @return 头部
     */
    public T front() {
        return list.get(0);
    }

    /**
     * 尾部
     * @return 尾部
     */
    public T rear() {
        return list.get(list.size()-1);
    }
}
