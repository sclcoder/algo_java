package com.leetcode.array;

/**
 * https://leetcode-cn.com/problems/merge-sorted-array/
 *  给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
 *  说明:
 *     初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
 *     你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素
 * 示例:
 *
 * 输入:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6],       n = 3
 *
 * 输出: [1,2,2,3,5,6]
 *
 *  使用双(三)指针:  指针p1、p2分别指向数组1、2的元素尾部 指针cur指向数组1的尾部。从后向前遍历比较大小，大者存放的数组1的尾部并更新cur指针位置
 *  具体逻辑看代码 画图看
 */
public class _88_合并两个有序数组 {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            int p1 = m - 1;
            int p2 = n - 1;
            int cur = m + n - 1;
            while (p2 >= 0) { // p2 < 0 时 说明排序完成，此时num1数组已经全部有序了，即便p1还大于0
                if (p1 >= 0 && nums2[p2] < nums1[p1]){ // num1数组数据移动
                    nums1[cur--] = nums1[p1--];
                } else {
                    nums1[cur--] = nums2[p2--]; // num2数组数据移动
                }
            }
        }
}
