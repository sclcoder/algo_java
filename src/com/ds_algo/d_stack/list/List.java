package com.ds_algo.d_stack.list;

/**
 * 定义List接口
 * Control + I 重写接口中所有方法
 * @param <T>
 */
public interface List<T> {
    int ELEMENT_NOT_FOUND = -1;
    void clear();
    int size();
    boolean isEmpty();
    boolean contains(T element);
    void add(T element);
    T get(int index);
    T set(int index, T element);
    void add(int index, T element);
    T remove(int index);
    int indexOf(T element);
}
