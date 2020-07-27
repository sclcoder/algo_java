package com.leetcode.array;

/**
 * https://leetcode-cn.com/problems/sort-colors/
 *
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 *
 * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 *
 * 注意:
 * 不能使用代码库中的排序函数来解决这道题。
 *
 * 示例:
 *
 * 输入: [2,0,2,1,1,0]
 * 输出: [0,0,1,1,2,2]
 *
 * 进阶：
 *     一个直观的解决方案是使用计数排序的两趟扫描算法。
 *     首先，迭代计算出0、1 和 2 元素的个数，然后按照0、1、2的排序，重写当前数组。
 *     你能想出一个仅使用常数空间的一趟扫描算法吗？
 *
 *     一趟扫描出结果的算法
 *     使用多指针:
 *     1 2 0 2 1 2 0 1 2 0 1 2
 *     原理: 遇到2向后边交换 遇到0 向前交换 遇到1可以按兵不动
 *     定义指针 p0 、p2 分别指向头尾 p0用来标记存放0的空间 p2用来标记用来存放2的空间
 *     指针cur作为遍历时的游标
 *     当cur == 1 时 跳过 cur++
 *     当cur == 0 时 cur 和 p1 交换 p0++ ， cur++
 *     当cur == 2 时 cur 和 p2交换数据 p2-- , cur 需要和 p0 比较决定是否向前交换
 *     当cur > p2 时迭代结束
 *
 *
 */
public class _75_颜色分类 {
    public void sortColors(int[] nums) {
        int p0  = 0;
        int p2  = nums.length - 1;
        int cur = 0;

        while (cur <= p2) {
            int v = nums[cur];

            if (v == 1) {
                cur++;
            }
            if (v == 0) {
                swap(nums, cur, p0);
                cur++;
                p0++;
            }

            if (v == 2) {
                swap(nums, cur, p2);
                p2--;
            }
        }
    }

    public  void  swap(int[] nums, int i , int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
