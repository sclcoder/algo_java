package com.algo.s05_Queue.list;

public abstract class AbstractList<T> implements List<T> {

    /**
     * 元素的数量
     * protected 子类可以访问
     */

    public int size;
    /**
     * 元素的数量
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * 是否包含某个元素
     *
     * @param element
     * @return
     */
    public boolean contains(T element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 添加元素到尾部
     *
     * @param element
     */
    public void add(T element) {
        add(size, element);
    }

    /**
     * 边界条件检查
     */
    protected void rangeCheck(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index" + index + ", Size" + size);
        }
    }

    /**
     * 添加元素边界检查
     * @param index
     */
    protected  void addRangeCheck(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index" + index + ", Size" + size);
        }
    }
}

