package com.ds_algo.c_linkList;

/**
 * 双向链表的实现
 *
 * @param <T>
 */
public class LinkedList<T> extends AbstractList<T> {

    private Node<T> first;
    private Node<T> last;

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
            if (prev != null){
                sb.append(prev.element);
            } else {
                sb.append("null");
            }
            sb.append("<-").append(element).append("->");
            if (next != null){
                sb.append(next.element);
            } else {
                sb.append("null");
            }

            return sb.toString();
        }
    }


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
         * 边界条件: first和last指针需要更新的位置
         */
        if (index == size) { // 向最后添加元素--last指针需要更新
            Node<T> node = last;
            Node<T> newNode = new Node<>(node, element, null);
            if (node == null) { // index==0 size==0 第一个元素 first需要更新
                first = newNode;
            } else {
                node.next = newNode;
            }
            last = newNode;
        } else {
            Node<T> nextNode = node(index);
            Node<T> prevNode = nextNode.prev;
            Node<T> newNode = new Node<>(prevNode, element, nextNode); // newNode的 prev 和 next指针处理完毕
            // 需要处理 prevNode的next和nextNode的prev指针
            if (prevNode != null) {
                prevNode.next = newNode;
            } else { // index = 0 说明添加的是第一个节点
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
        Node<T> targetNode = node(index);
        Node<T> prevNode = targetNode.prev;
        Node<T> nextNode = targetNode.next;

        if (prevNode == null) { // index == 0
            first = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) { // index == size-1
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
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
        sb.append("LinkedList{size=").append(size).append(", elements=[");
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
