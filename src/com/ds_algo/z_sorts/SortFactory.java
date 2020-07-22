package com.ds_algo.z_sorts;


import com.tool.common.Asserts;
import com.tool.common.Integers;
import com.tool.common.TimeTool;

import java.util.Arrays;

public class SortFactory {
    /**
     * 冒泡排序: 两两比较  思路很简单啊
     * 但自己写代码的时候还是想了一下
     * 冒泡效率低的一逼
     */
    // TODO: 冒泡可以继续优化
    public static void bubbleSort(Integer[] nums){
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
     *
     * 选择排序比冒泡强点
     */
    public static void selectSort(Integer[] nums){
        int count = nums.length;
        for (int end = count-1; end > 0; end--) {
            int max = nums[0];
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin++) {
                if (max <= nums[begin]){  // 此处使用 <= 而不是 < 是为了保证算法的稳定性
                    max = nums[begin];
                    maxIndex = begin;
                }
            }
            swap(maxIndex,end,nums);
        }
    }

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
     *     通过该性质来建立大顶堆，使用数组结构即可解决
     *
     */
    public static void heapSort(Integer[] nums){
        int heapSize = nums.length;
        // 堆化为大顶堆
        for (int i = (heapSize >> 1) - 1; i >= 0 ; i--) {
            /// 有子节点的位置才需要下滤
            siftDown(i,nums,heapSize);
        }

        // 数组的起始位置就是最大值，这次将数据进行选择排序即可
        while (heapSize > 1){
            swap(0, heapSize-1,nums);
            heapSize--;
            siftDown(0, nums,heapSize);
        }
    }

    /**
     * 将数据堆化，数组顺序按照大顶堆排列
     *
     *              2(0)
     *        4(1)        3(2)
     *     6(3)  2(4)   7(5)
     *   可视化堆化
     *
     *   大顶堆的构建采用 自底而上的下滤操作
     *   下滤操作流程
     *   如果 1.node < 最大的子节点 (根据大顶堆的定义可知 父节点比左右子节点都大)
     *          node 与最大子节点交换
     *       2.node >= 最大的子节点
     *          退出
     */

    public static void  siftDown(int index ,Integer[] nums, int heapSize){
        Integer element = nums[index];

        int size = heapSize;
        int half = size >> 1; // 小于half位置的index处的节点都有子节点
        while (index < half){ // 有子节点
            /**
             * 查找最大子节点
             */
            int childIndex = (index<<1) + 1; // 假设最大值在左
            int childElement = nums[childIndex];

            int rightIndex = childIndex + 1;
            if (rightIndex < size){ // 存在右节点
                if (nums[rightIndex] > childElement){ // 右节点最大
                    childElement = nums[rightIndex];
                    childIndex = rightIndex;
                }
            }

            /**
             * 将最大子节点的值设置到目标节点的位置
             */
            if (element < childElement){
                nums[index] = childElement;
                index = childIndex;
            } else {
                break; // 下虑结束
            }
        }
        /// index是最后一个上移元素的位置
        nums[index] = element;
    }



    /**
     * 插入排序: 就像排序扑克牌一样
     * 和选择排序差不多
     */
    public static void insertSort(Integer[] nums){
        int len = nums.length;

        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i ; j++) {
                if (nums[i] < nums[j]){
                    // (j,i-1)这个范围的元素依次向后移动 i处元素插入j处
                   int k = i;
                   int tem = nums[i];
                   while (k > j){
                       nums[k] = nums[k-1];
                       k--;
                   }
                   nums[j] = tem;
                }
            }
        }
    }

    /**
     * 归并排序: 优秀到和以上几个算法不是一个数量级
     *
     *
     * 采用前闭后开区间来计算
     * 0       mid   8
     * 1 2 3 4 5 6 7   mid = （0+8）/ 2 = 4   [0,4) [4,8)
     *
     * 0    mid    7
     * 1 2 3 4 5 6     mid = (0+7) / 2 = 3   [0,3) [3,7)
     */
    public static void mergeSort(Integer[] nums){
        // 分治
        sortHelper(0,nums.length,nums);
    }

    /**
     * 分治部分
     */
    public static void sortHelper(int begin, int end, Integer[] nums) {
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
    public static void mergeHelper(int begin, int mid, int end, Integer[]nums){
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
    public static void swap(int i , int j, Integer[] nums){
        Integer tem = nums[j];
        nums[j] = nums[i];
        nums[i] = tem;
    }

    public static void main(String[] args) {

        Integer[] array1 = Integers.random(100000,100,200000);
        Integer[] array2 = Integers.copy(array1);
        Integer[] array3 = Integers.copy(array1);
        Integer[] array4 = Integers.copy(array1);
        Integer[] array5 = Integers.copy(array1);

//        System.out.println("原始值: " + Arrays.toString(array1));

        TimeTool.check("bubbleSort", ()-> {
            bubbleSort(array1);
        });

        TimeTool.check("selectSort", ()-> {
            selectSort(array2);
        });

        TimeTool.check("insertSort", ()-> {
            insertSort(array3);
        });
        TimeTool.check("mergeSort", ()-> {
            mergeSort(array4);
        });
        TimeTool.check("heapSort", ()-> {
            heapSort(array5);
        });


        Asserts.test(Integers.isAscOrder(array1));
        Asserts.test(Integers.isAscOrder(array2));
        Asserts.test(Integers.isAscOrder(array3));
        Asserts.test(Integers.isAscOrder(array4));
        Asserts.test(Integers.isAscOrder(array5));

    }


}
