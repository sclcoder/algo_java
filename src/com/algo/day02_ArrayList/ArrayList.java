package com.algo.day02_ArrayList;

@SuppressWarnings("unchecked")

public class ArrayList<T> {
    /**
     * 元素的数量
     */
    private int size;

    /**
     * 所有的元素i
     */
    private T[] elements;

    private static final int DEFAULT_CAPACITY = 10;
    private static final int ELEMENT_NOT_FOUND = -1;

    /**
     * 带参数的构造函数
     *
     * @param capacity 初始容量
     */
    public ArrayList(int capacity) {
        capacity = (capacity < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
//        elements = new T[capacity]; 报错
        elements = (T[]) new Object[capacity]; // 使用Object然后强转
    }

    /**
     * 无参数构造函数
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 清除所有元素
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
           elements[i] = null;
        }
        size = 0;
    }

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
     * 获取index位置的元素
     *
     * @param index
     * @return
     */
    public T get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    /**
     * 设置index位置的元素
     *
     * @param index
     * @param element
     * @return 原来的元素ֵ
     */
    public T set(int index, T element) {
        rangeCheck(index);
        T old = elements[index];
        elements[index] = element;
        return old;
    }

    /**
     * 在index位置插入一个元素
     *
     * @param index
     * @param element
     */
    public void add(int index, T element) {

        addRangeCheck(index);
        // 检查是否需要扩容
        ensureCapacity(size+1);
        /**
         * 方法1: 变量i代表要移动元素位置的下一个位置
         * 所以判断条件是 i=size i>index, 因为是要移动元素的下一个位置
         */
//        for (int i = size; i > index; i--) {
//            elements[i] = elements[i-1];
//        }
        
        /**
         * 方法2: 变量i代表数组最后一个元素
         * 所以判断条件是 i=size-1 i>=index, 因为是要移动元素的下一个位置
         */
        for (int i = size -1; i >= index; i--) {
            elements[i+1] = elements[i];
        }
        elements[index] = element;
        size++;
    }

    /**
     * 删除index位置的元素
     *
     * @param index
     * @return
     */
    public T remove(int index) {
        rangeCheck(index);
        T old = elements[index];
        /** 方式1
         * 注意边界：i代表要移动元素的前一个位置所以 i < size-1
         */
//        for (int i = index; i < size-1 ; i++) {
//            elements[i] = elements[i+1];
//        }
//        size -= 1;
        /** 方式2
         * 注意边界：i代表要移动元素的位置 i < size
         */
        for (int i = index + 1; i < size; i++) {
            elements[i-1] = elements[i];
        }
        // 最后一个位置将指针清空不然会有内存泄漏
        elements[--size] = null;
        return old;
    }

    /**
     * 查看元素的索引
     *
     * @param element
     * @return
     */
    public int indexOf(T element) {

        for (int i = 0; i < size; i++) {
            if (element == elements[i]) {
                return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 保证数组可以容纳capacity大小
     * @param capacity 
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (capacity <= oldCapacity) return;
        
        if (capacity > oldCapacity){
            // 1.5倍
            int newCapacity = oldCapacity + (oldCapacity>>1); 
            T[] newElements = (T[])new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
            System.out.println(oldCapacity +"扩容为"+ newCapacity);
        }
    }

    /**
     * 边界条件检查
     */
    private void rangeCheck(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index" + index + ", Size" + size);
        }
    }

    /**
     * 添加元素边界检查
     * @param index
     */
    private  void addRangeCheck(int index){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index" + index + ", Size" + size);
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArrayList{size=").append(size).append(", elements=[");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(elements[i]);
        }
        sb.append("]}");
        return sb.toString();
    }
}

