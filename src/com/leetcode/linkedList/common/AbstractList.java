package com.leetcode.linkedList.common;

/**
 * 实现 接口List<T>部分抽象方法的抽象类
 * 该抽象类主要用来实现一些公共的方法、属性
 * @param <T>
 */
public abstract class  AbstractList<T> implements List<T>{

    /**
     * 记录元素数量的属性
     * protected 子类可以访问
     */
    protected int size;

    /**
     * 获取元素数量
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 是否包含某元素
     * @param element
     * @return
     */
    public boolean contains(T element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 添加元素到尾部
     * @param element
     */
    public void add(T element) {
        add(size, element);
    }

    /**
     * 边界检查
     * @param index
     * @return
     */
    protected void rangeCheck(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index" + index + ", Size" + size);
        }
    }
    protected void addRangeCheck(int index) {
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index" + index + ", Size" + size);
        }
    }

}
