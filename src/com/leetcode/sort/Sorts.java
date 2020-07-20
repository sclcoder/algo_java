package com.leetcode.sort;


import java.util.Arrays;

public class Sorts {
    /**
     * 冒泡排序: 两两比较  思路很简单啊
     * 但自己写代码的时候还是想了一下
     *
     */
    // TODO: 冒泡可以继续优化
    public static void bubbleSort(int[] nums){
        int count = nums.length;
        for (int i = 0; i < count; i++) { /// 计数层
            for (int j = 1; j < count - i; j++) { /// 迭代层
                if (nums[j] < nums[j-1]){
                   swap(j,j-1,nums);
                }
            }
        }
    }

    /**
     * 选择排序: 每次遍历都找出一个最大值
     * 我自己写代码竟然又错了。。。    int max = nums[0]; int maxIndex = 0;在外层循环时没重置。。。。。
     */
    // TODO: 选择排序可以使用大顶堆来优化最大值的获取
    public static void selectSort(int[] nums){
        int count = nums.length;
        for (int i = 0; i < count; i++) {
            int max = nums[0];
            int maxIndex = 0;
            for (int j = 0; j < count - i; j++) {
                if (max < nums[j]){
                    max = nums[j];
                    maxIndex = j;
                }
            }
            swap(maxIndex, count-i-1,nums);
        }
    }


    /**
     * 插入排序: 就像排序扑克牌一样
     */
    public static void insertSort(int[] nums){
        int len = nums.length;

        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i ; j++) {
                if (nums[i] < nums[j]){
                    // (j,i-1)这个范围的元素依次向后移动 i处元素插入j处
                   int k = i;
                   int tem = nums[i];
                   while (k >= j){
                       nums[k] = nums[k-1];
                       k--;
                   }
                   nums[j] = tem;
                }
            }
        }
    }

    /**
     * 归并排序
     * 采用前闭后开区间来计算
     * 0       mid   8
     * 1 2 3 4 5 6 7   mid = （0+8）/ 2 = 4   [0,4) [4,8)
     *
     * 0    mid    7
     * 1 2 3 4 5 6     mid = (0+7) / 2 = 3   [0,3) [3,7)
     */
    public static void mergeSort(int[] nums){
        // 分治
        sortHelper(0,nums.length,nums);
    }

    /**
     * 分治部分
     */
    public static void sortHelper(int begin, int end, int[] nums) {
        if (end - begin <= 1) return; // 只有一个元素了
        // 先二分，分别排序
        int mid = (end + begin) >> 1;
        sortHelper(begin, mid, nums);
        sortHelper(mid, end, nums);
        mergeHelper(begin,mid,end,nums);
    }
    /**
     * 合并部分
     *  1 4 5   2 3 6
     *  合并左右两边都有序的部分
     *  需要的参数有 begin mid end
     */
    public static void mergeHelper(int begin, int mid, int end, int[]nums){
        int ls = begin;
        int le = mid-1;
        int rs = mid;
        int re = end-1;

        int[] tempArray = new int[re-ls + 1];
        int k = 0;
        // 将左右两部分数据按顺序合并到tempArray中
        while (ls <= le && rs <= re){
            tempArray[k++] = nums[ls] <= nums[rs] ? nums[ls++] : nums[rs++];
        }
        while (ls<=le){
            tempArray[k++] = nums[ls++];
        }
        while (rs<=re){
            tempArray[k++] = nums[rs++];
        }

        for (int i = 0; i < tempArray.length; i++) {
            nums[begin + i] = tempArray[i];
        }
    }

    /**
     * 交换数组中两个元素
     * @param i
     * @param j
     * @param nums
     */
    public static void swap(int i , int j, int[] nums){
        int tem = nums[j];
        nums[j] = nums[i];
        nums[i] = tem;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,3,5,61,3,6333333,4,32,1123,34,4,3};
//        bubbleSort(nums);
//        selectSort(nums);
//        insertSort(nums);
        mergeSort(nums);
        System.out.println(Arrays.toString(nums));



    }

}
