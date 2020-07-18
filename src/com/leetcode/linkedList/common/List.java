package com.leetcode.linkedList.common;

/**
 * 定义List接口
 * Control + I 重写接口中所有方法
 * @param <T>
 */
public interface List<T> {
    public static final int ELEMENT_NOT_FOUND = -1;

    /**
     * 清除所有元素
     */
    void clear();

    /**
     * 元素的数量
     *
     * @return
     */
    int size();

    /**
     * 是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 是否包含某个元素
     *
     * @param element
     * @return
     */
    boolean contains(T element);


    /**
     * 获取index位置的元素
     *
     * @param index
     * @return
     */
    T get(int index);

    /**
     * 设置index位置的元素
     *
     * @param index   索引
     * @param element 值
     * @return 原来的元素ֵ
     */
    T set(int index, T element);

    /**
     * 添加元素到链表尾部
     * @param element
     */
    void add(T element);

    /**
     * 在index位置插入一个元素
     *
     * @param index   要插入的位置
     * @param element 要插入的值
     */
    void add(int index , T element);


    /**
     * 删除index位置的元素
     *
     * @param index
     * @return 被删除的元素
     */

    T remove (int index);

    /**
     * 查看元素的索引
     *
     * @param element 值
     * @return 索引值
     */
    int indexOf(T element);
}
