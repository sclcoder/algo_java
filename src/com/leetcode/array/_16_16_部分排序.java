package com.leetcode.array;

/**
 * https://leetcode-cn.com/problems/sub-sort-lcci/
 * 给定一个整数数组，编写一个函数，找出索引m和n，只要将索引区间[m,n]的元素排好序，整个数组就是有序的。
 * 注意：n-m尽量最小，也就是说，找出符合条件的最短序列。函数返回值为[m,n]，若不存在这样的m和n（例如整个数组是有序的），请返回[-1,-1]。
 *
 * 示例：
 *
 * 输入： [1,2,4,7,10,11,7,12,6,7,16,18,19]
 * 输出： [3,9]
 *
 * 提示：
 *   0 <= len(array) <= 1000000
 *
 *  查找逆序对
 *  正序遍历一次找到升序的逆序对的最右边
 *  逆序遍历一次找到降序的逆序对的最左边
 *
 */
public class _16_16_部分排序 {

    public int[] subSort(int[] array) {
        int m = -1;
        int n = -1;

        if (array.length == 0) return new int[]{m,n};

        int max = array[0];
        // 正序查找逆序对的最右边
        for (int i = 1; i < array.length; i++) {
            if (max <= array[i]){
                max = array[i];
            } else {
                // array[i]值小于max，是个逆序对
                n = i;
            }
        }

        if (n == -1) return new int[]{m,n}; // 没有逆序对可以直接退出了
        // 逆序查找逆序对的最左边
        int min =  array[array.length - 1];
        for (int i = array.length - 2; i >= 0 ; i--) {
            if ( array[i] >= min){
                min = array[i];
            } else {
                // array[i]值大于min，是个逆序对
                m = i;
            }
        }
        return  new int[]{m,n};
    }
}
