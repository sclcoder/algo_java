package com.ds_algo.c_linkList.circle;

import com.ds_algo.c_linkList.AbstractList;

/**
 * 单向循环链表
 *
 * @param <T>
 */
public class SingleCircleLinkedList<T> extends AbstractList<T> {

    private Node<T> first;

    // 节点结构（内部类）
    private static class Node<T> {
        T element;
        Node<T> next;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element).append("->");
            if (next != null) {
                sb.append(next.element);
            }
            return sb.toString();
        }

        // 构造函数
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
        first = null;
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
        Node<T> node = node(index);
        return node.element;
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
        Node<T> node = node(index);
        T old = node.element;
        node.element = element;
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
        if (index == 0) {
            Node<T> firstNode = new Node<>(element, first);
            Node<T> tailNode;
//            if (size == 0){
//                tailNode = firstNode;
//            } else {
//                tailNode = node(size-1);
//            }
            tailNode = (size == 0) ? firstNode : node(size - 1);
            tailNode.next = firstNode;
            first = firstNode;

        } else {
            // 循环链表: 不用特殊处理逻辑和单链表一样
            Node<T> prev = node(index - 1);
            prev.next = new Node<>(element, prev.next);
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
        Node<T> node;

        if (index == 0) {
            node = first;
            if (size == 1) { // size==1时 自己指自己不特殊处理删除不了
                first = null;
            } else {
                Node<T> tailNode = node(size - 1);
                first = first.next;
                tailNode.next = first;
            }
        } else {
            Node<T> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
        }
        size--;
        return node.element;
    }

    /**
     * 查看元素的索引
     *
     * @param element 值
     * @return 索引值
     */
    @Override
    public int indexOf(T element) {
        if (element == null) {
            Node<T> node = first;
            for (int i = 0; i < size; i++) {
                if (node.element == element) {
                    return i;
                } else {
                    node = node.next;
                }
            }
        } else {
            Node<T> node = first;
            for (int i = 0; i < size; i++) {
                if (node.element.equals(element)) return i;
                node = node.next;
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
    public Node<T> node(int index) {
        rangeCheck(index);
        Node<T> node = first;
        /**
         * 注意边界: next的次数和index的值是一致的
         */
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SingleCircleLinkedList{size=").append(size).append(", elements=[");
        Node<T> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(node);
            node = node.next;
        }
        sb.append("]}");
        return sb.toString();
    }
}
