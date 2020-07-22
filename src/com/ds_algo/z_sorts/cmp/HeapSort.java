package com.ds_algo.z_sorts.cmp;

import com.ds_algo.z_sorts.Sort;

/**
 * 堆排序: 对选择排序的一种优化，将给定的数据堆化处理，依次处理堆顶元素(最大值)
 * 的时间复杂度由O(n)降为O(logn)
 *
 *   二叉堆的性质 : 结构是个完全二叉树，且父节点的数据大于子节点，左右子节点没有大小要求
 *
 *                6(0)
 *        5(1)          4(2)
 *   ` 3(3)  2(4)     1(5)
 *
 *     设:  n为节点总数  i为节点索引
 *     如果i>0
 *     父节点 = (i-1) / 2;
 *     如果: i * 2 + 1 <= n - 1 左节点 = i * 2 + 1;
 *     如果: i * 2 + 1 > n - 1  没有左节点
 *
 *     如果: i * 2 + 2 <= n - 1 右节点 = i * 2 + 2;
 *     如果: i * 2 + 2 > n - 1  没有右节点
 *
 *
 */
public class HeapSort<T extends Comparable<T>> extends Sort<T> {

    private int heapSize;
    @Override
    protected void sort() {
        /**
         * 堆的大小
         */
        heapSize = array.length;

        /**
         * 原地堆化: 采用自下向上的下滤
         * 对所有的非叶子节点进行下滤操作
         *
         *             3(0)
         *       4(1)        5(2)
         *    3(3) 2(4)    6(5) 1(6)
         *
         */
        for (int i = (heapSize >> 1) - 1 ; i >=0 ; i--) {
            siftDown(i);
        }

        /**
         * 排序操作: 最大值在数组头处
         */
        while (heapSize > 1){
            // 将最大值换到最后位置
            swap(0,--heapSize);
            siftDown(0);
        }


    }

    /**
     * 下滤操作
     * @param index 要下滤的位置
     */
    public void siftDown(int index){
        T element = array[index];

        /**
         * 只有有子节点的index才能下滤
         */
        int half = heapSize >> 1;
        while (index < half){
            /**
             * 下滤操作,需要找到最大的子节点进行比较
             */
            // 左节点
            int childIndex = (index << 1) + 1;
            T childElement = array[childIndex];

            int rightIndex = childIndex+1;
            if (rightIndex < heapSize){ // 有右节点
                if (cmpElement(array[rightIndex], childElement) > 0){
                    childIndex = rightIndex;
                    childElement = array[childIndex];
                }
            }

            // 将大节点上移
            if (cmpElement(element , childElement) < 0){
                array[index] = childElement;
                index = childIndex;
            } else {
                break; /// 一定不要漏写。。。。 不然死循环
            }
        }
        // 将目标值移动到确定的位置
        array[index] = element;
    }
}
