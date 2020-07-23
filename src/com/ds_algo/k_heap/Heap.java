package com.ds_algo.k_heap;

/**
 * 堆接口
 */
public interface Heap<E> {
    /**
     * 堆大小
     * @return 堆大小
     */
    int size();

    /**
     * 是否为空
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 清空
     */
    void clear();

    /**
     * 添加元素
     * @param element 元素
     */
    void add(E element);

    /**
     * 获取堆顶元素
     * @return 堆顶元素
     */
    E get();

    /**
     * 删除堆顶元素
     * @return 堆顶元素
     */
    E remove();

    /**
     * 替换堆顶元素
     * @param element 新堆顶元素
     * @return 被替换的堆顶元素
     */
    E replace(E element);
}
