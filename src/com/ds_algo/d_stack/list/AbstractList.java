package com.ds_algo.d_stack.list;

import com.ds_algo.c_linkList.List;

public abstract class AbstractList<T> implements List<T> {

    protected int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public boolean contains(T element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }


    public void add(T element) {
        add(size, element);
    }


    protected void rangeCheck(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index" + index + ", Size" + size);
        }
    }


    protected  void addRangeCheck(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index" + index + ", Size" + size);
        }
    }
}

