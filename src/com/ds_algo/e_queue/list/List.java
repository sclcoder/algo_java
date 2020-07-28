package com.ds_algo.e_queue.list;

/**
 * 定义List接口
 * Control + I 重写接口中所有方法
 * @param <T>
 */
public interface List<T> {
    int ELEMENT_NOT_FOUND = -1;
    /**
     * 清除所有元素
     */
    void clear();

    /**
     * 元素的数量
     *
     * @return 元素的数量
     */
    int size();

    /**
     * 是否为空
     *
     * @return 是否为空
     */
    boolean isEmpty();

    /**
     * 是否包含某个元素
     *
     * @param element 值
     * @return 是否包含
     */
    boolean contains(T element);

    /**
     * 添加元素到尾部
     *
     * @param element 值
     */
    void add(T element);

    /**
     * 获取index位置的元素
     *
     * @param index 索引
     * @return 获取的值
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
     * 在index位置插入一个元素
     *
     * @param index   要插入的位置
     * @param element 要插入的值
     */
    void add(int index, T element);

    /**
     * 删除index位置的元素
     *
     * @param index 索引
     * @return 被删除的值
     */
    T remove(int index);

    /**
     * 查看元素的索引
     *
     * @param element 值
     * @return 索引值
     */
    int indexOf(T element);
}
