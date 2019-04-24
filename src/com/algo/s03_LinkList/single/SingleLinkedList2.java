package com.algo.s03_LinkList.single;

import com.algo.s03_LinkList.AbstractList;

/**
 * 增加虚拟头结点：节点操作统一处理逻辑
 *
 * @param <T>
 */
public class SingleLinkedList2<T> extends AbstractList<T> {

    private Node<T> first;

    // 添加构造函数
    public SingleLinkedList2() {
        first = new Node<>(null, null);
    }

    // 节点结构（内部类）
    private static class Node<T> {
        T element;
        Node<T> next;

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

        Node<T> prev = index == 0 ? first : node(index - 1);
        prev.next = new Node<>(element, prev.next);
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

        Node<T> prev = index == 0 ? first : node(index - 1);
        Node<T> node = prev.next;
        prev.next = node.next;
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
            Node<T> node = first.next;
            for (int i = 0; i < size; i++) {
                if (node.element == element) {
                    return i;
                } else {
                    node = node.next;
                }
            }
        } else {
            Node<T> node = first.next;
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
        Node<T> node = first.next;
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
        sb.append("SingleLinkedList{size=").append(size).append(", elements=[");
        Node<T> node = first.next;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(node.element);
            node = node.next;
        }
        sb.append("]}");
        return sb.toString();
    }
}
