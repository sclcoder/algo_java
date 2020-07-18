package com.leetcode.linkedList.list;

import com.leetcode.linkedList.common.AbstractList;


/**
 * 单向链表
 * @param <T>
 */
public class SingleLinkedList<T> extends AbstractList<T>{

    private Node<T> head;

    /**
     * 链表内部类 节点
     * @param <T>
     */
    private static class Node<T>{
        T element;
        Node<T> next;

        public Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }
    }


    /**
     * 清除所有元素
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * 获取index位置的元素
     *
     * @param index
     * @return
     */
    @Override
    public T get(int index) {
        Node<T> target = node(index);
        return target.element;
    }

    /**
     * 设置index位置的元素
     *
     * @param index   索引
     * @param element 值
     * @return 原来的元素ֵ
     */
    @Override
    public T set(int index, T element) {
        Node<T> target = node(index);
        T old = target.element;
        target.element = element;
        return old;
    }

    /**
     * 在index位置插入一个元素
     *
     * @param index   要插入的位置
     * @param element 要插入的值
     */
    @Override
    public void add(int index, T element) {
        addRangeCheck(index);
        if (index == 0){
            head = new Node<>(element,head);
        } else {
            Node<T> prev = node(index-1);
            prev.next = new Node<>(element,prev.next);
        }
        size++;
    }

    /**
     * 删除index位置的元素
     *
     * @param index
     * @return
     */
    @Override
    public T remove(int index) {
        rangeCheck(index);
        Node<T> target = head;
        if (index == 0){
            head = head.next;
        } else {
            Node<T> prev = node(index-1);
            target = prev.next;
            prev.next = target.next;
        }
        size--;
        return target.element;
    }

    @Override
    public int indexOf(T element) {
        Node<T> cur = head;
        if (element == null){
            for (int i = 0; i < size; i++) {
                if (element == cur.element){
                    return i;
                } else {
                    cur = cur.next;
                }
            }
        }
        if (element != null){
            for (int i = 0; i < size; i++) {
                if (element.equals(cur.element)){
                    return i;
                } else {
                    cur = cur.next;
                }
            }
        }


        return ELEMENT_NOT_FOUND;
    }

    /**
     * 获取指定位置节点
     *
     * @param index 指定位置
     * @return 节点
     */
    public Node<T> node(int index){
        rangeCheck(index);
        Node<T> target = head;
        while (index != 0){
            target = target.next;
            index--;
        }
        return target;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SingleLinkedList { Size=").append(size).append(", Elements[");
        if (head == null) {
            sb.append(",");
        }
        while (head != null){
            sb.append(head.element).append(",");
            head = head.next;
        }
        sb.deleteCharAt(sb.length()-1);

        sb.append("]}");

        return sb.toString();
    }
}
