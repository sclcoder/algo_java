package com.ds_algo.i_set;

import com.ds_algo.c_linkList.LinkedList;
import com.ds_algo.c_linkList.List;

public class ListSet<E> implements Set<E>{
    // 双向链表
    private LinkedList<E> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    /**
     * 集合相同的元素需要覆盖
     * @param element
     */
    @Override
    public void add(E element) {
        int index = list.indexOf(element);
        if (index == list.ELEMENT_NOT_FOUND){ // 不存在就添加
            list.add(element);
        } else { // 覆盖
            list.set(index,element);
        }
    }

    @Override
    public void remove(E element) {
        int index = list.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND){
            list.remove(index);
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        for (int i = 0; i < list.size(); i++) {
           if ( visitor.visit(list.get(i))) return;
        }
    }
}
