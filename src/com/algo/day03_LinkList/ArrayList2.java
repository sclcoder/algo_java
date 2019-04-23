package com.algo.day03_LinkList;

@SuppressWarnings("unchecked")

public class ArrayList2<T> extends AbstractList<T> {

    /**
     * 所有的元素i
     */
    private T[] elements;

    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 带参数的构造函数
     *
     * @param capacity 初始容量
     */
    public ArrayList2(int capacity) {
        capacity = (capacity < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capacity;
//        elements = new T[capacity]; 报错
        elements = (T[]) new Object[capacity]; // 使用Object然后强转
    }

    /**
     * 无参数构造函数
     */
    public ArrayList2() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 清除所有元素
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
           elements[i] = null;
        }
        size = 0;
        // 缩容
        elements = (T[]) new Object[DEFAULT_CAPACITY];
    }


    /**
     * 获取index位置的元素
     *
     * @param index
     * @return
     */
    @Override
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
    @Override
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
    @Override
    public void add(int index, T element) {
        addRangeCheck(index);
        // 检查是否需要扩容
        ensureCapacity(size+1);
        /**
         * 方法1: 变量i代表要移动元素位置的下一个位置
         * 所以判断条件是 i=size i>index, 因为是要移动元素的下一个位置
         * 这种方式更好 因为条件判断中比方式2少了size-1操作
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
     * 需要缩容
     *
     * @param index
     * @return
     */
    @Override
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
        // 缩容函数
        trim();
        return old;
    }

    /**
     * 查看元素的索引
     *
     * @param element
     * @return
     */
    @Override
    public int indexOf(T element) {
        // 传入的元素都是对象
        if (element == null){
            for (int i = 0; i < size; i++) {
                if (elements[i]==null)return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                // 仅仅比较内存地址太局限了
//            if (element == elements[i]) {
//                return i;
//            }
                // 允许外部自定义比较规则 使用equals函数
                if (element.equals(elements[i])){
                    return i;
                }
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
     * 缩容
     * 扩容倍数、缩容时机设计不当，可能引起复杂度震荡
     * 如 扩容倍数:为原容量的2倍 缩容时机: 为剩余空间的占总容量的一半时。 这样在边界情况下,数组会一会扩容一会缩容
     */
    private void trim(){
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity >> 1; // 缩小为当前大小的一半
        // 剩余元素数量大于容量的1/4或者小于默认容量 不需要缩容
        if (size >= (newCapacity >> 1) ||  oldCapacity <= DEFAULT_CAPACITY) return;

        T[] newElements = (T[])new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
        System.out.println(oldCapacity +"缩容为"+ newCapacity);
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

