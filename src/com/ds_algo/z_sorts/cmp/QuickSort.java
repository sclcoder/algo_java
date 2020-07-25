package com.ds_algo.z_sorts.cmp;


import com.ds_algo.z_sorts.Sort;

public class QuickSort<T extends Comparable<T>> extends Sort<T> {
    /**
     * 快速排序原理: 很简单的不要忌惮
     *  6 8 9 4 9 8 2 3 7  以6为轴点
     *  3 2 4 6 9 8 9 8 7  以6为轴点后,将元素左右分开
     * 1. 确定一个轴点pivot
     * 2. 将小于pivot的放到左边 大于pivot放到右边
     * 3. 完成以上两步后, pivot的位置就是所有元素排序后其所处位置
     * 4. 分别对pivot左右两侧的数据进行 123步操作。
     * 当所有的元素都作为轴点后，所有元素所在的位置就是排序后应该所处的位置，即排序完成
     *
     * 快排的算法:
     *
     *  begin               end
     *    6   8 9 4 9 8 2 3 7
     * pivot
     *
     *  步骤一、确定轴点位置
     *  1.以begin处的元素作为轴点 pivot = begin。begin的位置可以存放元素
     *
     *  2.从end开始遍历获取元素end,与pivot进行比较，
     *  a.如果 end > pivot则 end--
     *  b.end <= pivot, 将end位置的值赋值给begin位置,begin++，执行步骤3
     *
     *  3.从begin开始遍历获取元素与pivot进行比较,
     *  a.如果begin < pivot 则begin++
     *  b.begin >= pivot,将begin位置的值赋值给end位置,end--,执行步骤2
     *
     *  4.当begin==end时，所有小于轴点的都在左边，大于轴点的都在右边了。
     *
     *  5.经过以上操作，即可确定pivot轴点所在数组中的位置
     *
     *  步骤二、递归部分
     *  1. 通过轴点的位置将数据分为 左右两部分
     *  对左右两部分数据分别执行 步骤一
     *  2.当只要一个元素时，递归结束
     *
     * 快排的注意点： 快排效率的高点与轴点的选择有很大的关系。最好的状态就是将所有元素均匀的分布在轴点两侧
     *
     * 注意点:
     * 1.轴点的选择问题:如果每次都选择第一个元素。如果数据是这样排序了，快排的效率降低很多。。。O(n^2)
     * 比如: 7 1 2 3 4 5 6
     * 第一次以 7 为轴点 后结果为
     *      6 1 2 3 4 5 7
     * 第二次以 6为轴点
     * 。。。。
     * 依次轴点为 7 6 5 4 3 2 1
     * 可以发现每次，轴点只有一侧有数据，即递归的过程中数据规模只减小1，递归的规模为O(n),如果数据量很大，很可能栈溢出
     * 如果每次都均匀分布在轴点两侧，递归规模降为O(logn),
     *
     * 改进:
     * 随机选择轴点。为了不影响之前的算法，将选择的轴点元素和头部元素交换即可，这样就可以按照之前的方法处理
     *
     */

    @Override
    protected void sort() {
        int end = array.length;
        // [begin, end)
        quickSort(0,end);
    }

    public void quickSort(int begin, int end){
        /**
         * 这里 end - begin < 1; 没有元素的条件
         * 这样是为了在边界情况下，保证pivotIndex都有值，因为有的时候遇到变种快排时要用到pivotIndex
         */
        if (end - begin <= 0) return; /// 没有元素了
//        if (end - begin < 1) return; /// 没有元素了
//        if (end - begin < 2) return; /// 只有一个元素
        int pivotIndex = getPivotIndex(begin,end); // [begin, end]
        quickSort(begin,pivotIndex);
        quickSort(pivotIndex + 1,end);
    }

    public int getPivotIndex(int begin , int end){
        // 随机选择轴点
         swap(begin , begin + (int)Math.random() * (end - begin));
        // 备份轴点数据
        T pivot = array[begin];
        end--; // end 指向最后一个元素
        /**
         * 标记位: true     代表从end处开始比较
         *        false    代表从begin处开始比较
         */
        boolean e2b = true;
        while (begin < end){

            if (e2b == true){
                /**
                 * 从end开始比较
                 */
                if (cmpElement(array[end], pivot) <= 0){
                    array[begin++] = array[end];
                    e2b = false;
                } else {
                    end--;
                }
            }

            if (e2b == false){
                /**
                 * 从begin开始比较
                 */

                if (cmpElement(array[begin], pivot) >= 0){
                    array[end--] = array[begin];
                    e2b = true;
                } else {
                    begin++;
                }
            }
        }
        array[begin] = pivot;
        return begin; // 轴点元素最终的位置
    }
}
