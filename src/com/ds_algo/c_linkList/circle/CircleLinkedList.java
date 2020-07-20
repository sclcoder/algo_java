package com.ds_algo.c_linkList.circle;

import com.ds_algo.c_linkList.AbstractList;

/**
 * 双向循环链表的实现
 *
 * @param <T>
 */
public class CircleLinkedList<T> extends AbstractList<T> {

    private Node<T> first;
    private Node<T> last;
    private Node<T> current;
    // 节点结构（内部类）
    private static class Node<T> {
        T element;
        Node<T> prev;
        Node<T> next;

        // 构造函数
        public Node(Node<T> prev, T element, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (prev != null) {
                sb.append(prev.element);
            } else {
                sb.append("null");
            }
            sb.append("<-").append(element).append("->");
            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }

            return sb.toString();
        }
    }
/***************解决约思夫问题提供的接口**************************************/
    /**
     * 重置
     */
    public void reset(){
        current = first;
    }

    /**
     * 移除current指向的节点
     * @return
     */
    public T remove(){
        if (current == null) return null;
        Node<T> nextNode = current.next;
        T element = remove(current);
        if (size == 0){ // 如果删除了唯一的节点 current重置为null
            current = null;
        } else {
            current = nextNode;
        }
        return element;
    }
    /**
     * 下一个
     */
    public T next(){
        if (current == null) return null;
        current = current.next;
        return current.element;
    }
/********************************************/

    /**
     * 清除所有元素
     */
    @Override
    public void clear() {
        first = null;
        last = null;
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
        /**
         * 边界条件: 要更新first和last指针的地方
         */
        if (index == size) { // 向最后添加元素---为了更新last指针
            Node<T> tailNode = last;
            Node<T> newNode = new Node<>(tailNode, element, first);
            if (tailNode == null) { // index==0 size==0 第一个元素 为了更新first指针
                newNode.next = newNode; // 指向本身
                newNode.prev = newNode; // 指向本身
                first = newNode;
            } else {
                tailNode.next = newNode;
                first.prev = newNode;
            }
            last = newNode;
        } else {
            Node<T> nextNode = node(index);
            Node<T> prevNode = nextNode.prev;
            Node<T> newNode = new Node<>(prevNode, element, nextNode); // newNode的 prev 和 next指针处理完毕
            // 需要处理 prevNode的next和nextNode的prev指针
            prevNode.next = newNode; // 循环链表且index!=size,那么prevNode.next肯定值
            if (index == 0){ // index==0添加元素
                first = newNode;
            }
            nextNode.prev = newNode;
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
        return remove(node(index));
    }

    private T remove(Node<T> targetNode){
        /**
         * 边界条件: 要更新first和last指针的地方
         */
        if (size == 1){
            first = null;
            last = null;
        } else {
            Node<T> prevNode = targetNode.prev;
            Node<T> nextNode = targetNode.next;
            // 主要逻辑
            prevNode.next = nextNode;
            nextNode.prev = prevNode;

            // 特殊处理 -- 删除第一or最后一个 链表的first和last指针要更新
            if (first == targetNode) { // index == 0
                first = nextNode;
            }
            if (last == targetNode) { // index == size-1
                last = prevNode;
            }
        }
        size--;
        return targetNode.element;
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

        if (index < (size >> 1)) { // 从前边开始查找
            /**
             * 注意边界: next的次数和index的值是一致的
             */
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node;

        } else { // 从后面开始查找
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node;
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CircleLinkedList{size=").append(size).append(", elements=[");
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
