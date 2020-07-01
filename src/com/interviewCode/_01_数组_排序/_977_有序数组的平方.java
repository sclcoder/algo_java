package com.interviewCode._01_数组_排序;

/**
 *
 * 给定一个按非递减顺序排序的整数数组 A，返回每个数字的平方组成的新数组，要求也按非递减顺序排序。
 *
 *
 *
 * 示例 1：
 *
 * 输入：[-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 *
 * 示例 2：
 *
 * 输入：[-7,-3,2,3,11]
 * 输出：[4,9,9,49,121]
 *
 *
 *
 * 提示：
 *
 *     1 <= A.length <= 10000
 *     -10000 <= A[i] <= 10000
 *     A 已按非递减顺序排序。
 *
 A 已经排好序了，负数部分平方值大到小，非负数部分小到大。
 问题就可以转化为类似于88. 合并两个有序数组。
 把数组分为两部分，一部分为前0-k的负数部分，另一部分为k+1 - length 的非负数部分。
 利用两个指针i，j和创建一个空数组，分别从头遍历负数部分，从尾遍历非负数部分，进行平方值比较。平方值较大的放入空数组尾部，直到 i <= j.
 *
 */
public class _977_有序数组的平方 {
    public int[] sortedSquares(int[] A) {
        if (A == null || A.length == 0) return new int[0];
        int l = 0;
        int r = A.length - 1;
        int cur = r;

        int[] result = new int[A.length];

        while (l <= r) { // 注意相等时的条件

            int lv =  A[l] * A[l];
            int rv =  A[r] * A[r];

            if (lv < rv){
                result[cur--] = rv;
                r--;
            } else {
                result[cur--] = lv;
                l++;
            }
        }
        return result;
    }


}
