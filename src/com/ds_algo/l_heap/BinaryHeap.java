package com.ds_algo.l_heap;


import com.tool.binaryTree.printer.BinaryTreeInfo;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 二叉堆: 结构为完全二叉树
 * 性质: 任意节点的值 <= or >= 其子节点的值(但上一层的值不一定比下一层的值大 如下图 第二层的58 就比第三层60小)
 * 大顶堆 : 任意节点的值 >=  其子节点的值 为大顶堆
 * 小顶堆 : 任意节点的值 <=  其子节点的值 为小顶堆
 * 物理结构： 使用数组可以存储数据即可实现
 *
 *          一个可视化大顶堆  n = 10
 *                      72(0)
 *            64(1)                   58(2)
 *    54(3)          60(4)        44(5)   38(6)
 *  32(7)  18(8)   59(9)
 *
 *  编号顺序: 从上向下从左到右即二叉树层次遍历的顺序
 *  存储: heap = [72,64,58,54,60,44,38,32,18,59]
 *
 *  索引i的规律， n是元素数量
 *  一、i = 0， 根节点
 *  二、i > 0 时
 *  1. 父节点的编号为  floor(i-1) / 2 ;
 *  2. 如果 2i + 1 <= n - 1 , 左节点索引 2i+1 ,否则没有左节点
 *     如果 2i + 2 <= n - 1,  右节点索引 2i+2 ,否则没有右节点
 *
 *
 */
@SuppressWarnings({"unchecked"})

// 默认以大顶堆方式构建
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {

    private static final int DEFAULT_CAPACITY = 50;
    private E[] elements;

    public BinaryHeap(E[] elements,Comparator<E> comparator){
        super(comparator);

        if (elements == null || elements.length == 0){
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            size = elements.length;
            int capacity = Math.max(elements.length, DEFAULT_CAPACITY);
//            this.elements = (E[]) new Object[capacity];
//            for (int i = 0; i < elements.length; i++) {
//                this.elements[i] = elements[i];
//            }
            this.elements = Arrays.copyOf(elements,capacity);
            heapify();
        }
    }

    public BinaryHeap(E[] elements){
        this(elements,null);
    }

    public BinaryHeap(Comparator<E> comparator){
        this(null,comparator);
    }

    public BinaryHeap(){
        this(null,null);
    }

    /**
     * 对数组进行大顶堆化处理
     */
    public void heapify(){
        /*
         * 采用自底向上的下滤
         * 步骤: 从后向前依次对非叶子节点进行下滤操作
         * 非叶子节点索引范围:  index < size/2
         */
        for (int i = (size >> 1) - 1; i >= 0; i--) {
                siftDown(i);
        }

    }

    /**
     * 上滤
     * @param index 需要上滤的位置
     *
     * 步骤:
     *  1.找到父节点parent, 如果大于父节点的值就交换值，继续向上找父节点进行比较
     *  2.当没有父节点时结束
     */
    private void siftUp(int index){
        E v = elements[index];
        int parentIndex = (index - 1) >> 1;
        while (parentIndex >= 0){
            E parentV = elements[parentIndex];
            if (compare(v,parentV) > 0){
                elements[index] = elements[parentIndex];
                index = parentIndex;
                parentIndex = (parentIndex - 1) >> 1;
            } else {
                break;
            }
        }
        elements[index] = v;
    }

    /**
     * 下滤
     * @param index 需要下滤的位置
     *
     * 步骤:
     *  1.记录要下滤的元素的索引 index , 值 V 并备份
     *  2.找较大子节点,记录索引 childIndex , 值 childV
     *    a.如果 childV > V , 将index处的值用childV覆盖, index更新为childIndex
     *    b.如果 没有子节点结束 或 childV <= V, 结束
     *    c.重复 a、b
     *
     */
    private void siftDown(int index){
        E v = elements[index];
        int half = size >> 1;
        while (index < half){ // 有子节点
            // 左节点
            int childIndex = (index << 1) + 1;
            E childV = elements[childIndex];
            if (childIndex + 1 <= size - 1){ // 有右节点
                if (compare(elements[childIndex + 1],childV) > 0){ // 右节点大
                    childIndex = childIndex + 1;
                    childV = elements[childIndex];
                }
            }
            // 找到最大子节点
            if (compare(v, childV) >= 0) break;
            elements[index] = childV;
            index = childIndex;
        }
        elements[index] = v;
    }

    /**
     * 保证数组可以容纳capacity大小
     * @param capacity 新容量
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (capacity <= oldCapacity) return;
        // 1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
//        E[] newElements = (E[]) new Object[newCapacity];
//        for (int i = 0; i < size; i++) {
//            newElements[i] = elements[i];
//        }
//        elements = newElements;
        //
        elements = Arrays.copyOf(elements,newCapacity);
    }



    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size++] = element;
        // 上滤
        siftUp(size-1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[size-1];
    }

    @Override
    public E remove() {
        emptyCheck();
        int lastIndex = --size;
        E top = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);
        return top;
    }

    @Override
    public E replace(E element) {
        elementNotNullCheck(element);
        E top = null;
        if (size == 0){
            elements[0] = element;
            size++;
        } else {
            top = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return top;
    }



    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = ((int)node << 1) + 1;
        return index <= (size - 1) ? index : null;
    }

    @Override
    public Object right(Object node) {
        int index = ((int)node << 1) + 2;
        return index <= (size - 1) ? index : null;
    }

    @Override
    public Object string(Object node) {
        return elements[(int)node];
    }
}
