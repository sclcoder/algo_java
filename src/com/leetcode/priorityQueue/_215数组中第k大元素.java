package com.leetcode.priorityQueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 215. 数组中的第K个最大元素
 *
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 *
 * 示例 1:
 *
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 *
 * 示例 2:
 *
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 *
 * 说明:
 *
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 */
public class _215数组中第k大元素 {


    Random random = new Random();
    public int findKthLargest(int[] nums, int k) {
        return quick_select(nums, 0, nums.length - 1, nums.length - k);
    }

    public int quick_select(int[] nums, int l, int r, int index){
        int i = random.nextInt(r - l + 1) + l;
        swap(nums, i, r);
        int q = sort(nums, l, r);
        if(q == index){
            return nums[q];
        }
        return q < index ? quick_select(nums, q + 1, r, index) : quick_select(nums, l, q - 1, index);
    }
    public int sort(int[] nums, int l, int r){
        int num = nums[r];
        int i = l;
        for(int j = l; j < r; j++){
            if(nums[j] <= num){
                swap(nums, i++, j);
            }
        }
        swap(nums, i, r);
        return i;
    }
    public void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static int findKthLargest_system(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }


    // 利用快排由大到小排序，在排序过程中当找到的轴点位置在第k-1个时,就找到了该元素。
    static int target = 0;
    public static int findKthLargest_quick(int[] nums, int k) {
        quickSort(0,nums.length,nums,k);
        System.out.println(Arrays.toString(nums));
        return nums[k-1];
    }

    /**    b           e
     *     1  2  3  4  5
     *     p
     *     5  2  3  4  1  找到1的index
     */
    public static void quickSort(int begin, int end, int[] nums, int k){
        /**
         * 这句代码写在getPivotIndex函数之后是为了正确计算边界情况下的pivotIndex
         * 写在getPivotIndex函数之后，LeetCode中数组越界了，本IEDA同样测试用例，没问题
         * 所以还是应该写在这。
         * 为了得到正确的pivotIndex, 此处的end - begin <= 1 改为 end - begin <= 0
         */
        if (end - begin <= 0) return;
        int pivotIndex = getPivotIndex(begin, end, nums);
        if (pivotIndex > k-1){
            quickSort(begin,pivotIndex,nums,k);
        } else if (pivotIndex < k-1){
            quickSort(pivotIndex + 1,end,nums,k);
        } else {
            return;
        }

    }

    public static int getPivotIndex(int begin, int end, int[] nums){
        end--;
        // 随机选择轴点
        int randomInxde = begin + (int) Math.random() * (begin - end);
        int tem = nums[begin];
        nums[begin] = nums[randomInxde];
        nums[randomInxde] = tem;

        // 区间前闭后开[begin, end)
        int pivot = nums[begin];
        // 将数据根据与轴点之间的大小 左右排布 这里使用降序排列
        while (begin < end){
            while (begin < end){
                if (nums[end] >= pivot){
                    nums[begin++] = nums[end];
                    break;
                } else {
                    end--;
                }
            }
            while (begin < end){
                if (nums[begin] <= pivot){
                    nums[end--] = nums[begin];
                    break;
                } else {
                    begin++;
                }
            }
        }
        nums[begin] = pivot;
        return begin;
    }

    /**
     * 使用优先级队列。本质为构建了一个小顶堆。
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest_priorityQueue(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        for (int i = 0; i < nums.length; i++) {
            if (queue.size() < k){
                queue.add(nums[i]);
            } else {
                if (nums[i] >= queue.peek()){
                    queue.remove();
                    queue.add(nums[i]);
                }
            }
        }
        return queue.peek();
    }

    public static void main(String[] args) {
        int[] nums  = new int[]{10,23,1,2,3,4,5,5,6,222,3,4,6,777,22,44,1,123,123,1,23,345,34,53,4,5,75,7,56,588};
//        System.out.println(findKthLargest_priorityQueue(nums, 7));
        int[] nums2  = new int[]{13,2,1,5,6,4};
        System.out.println(findKthLargest_quick(nums,2));
    }
}
