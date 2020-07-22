package com.ds_algo.z_sorts.cmp;

import com.ds_algo.z_sorts.Sort;

public class MergeSort<T extends Comparable<T>> extends Sort<T> {
    // 一次性分配空间
    private T[] tempArray;

    @Override
    protected void sort() {
        // 粗糙版本
//        mergeSort_base();
        // 稍微改进一下
        mergeSort_advance();
    }

    public void mergeSort_advance(){
        tempArray = (T[]) new Comparable[array.length];
        divide2(0, array.length);
    }

    public void divide2(int begin, int end){
        if ((end - begin) < 2) return;
        int mid = (begin + end) >> 1;
        divide2(begin, mid);
        divide2(mid, end);
        merge2(begin,mid,end);
    }

    public void merge2(int begin, int mid, int end){
        /**
         * 合并操作
         *  begin           mid          end
         *   2    4    6     3    5   7
         *
         *  优化思路:
         *  1.先将array[begin,end)中的这些待合并的数据存放到临时数组tempArray[begin,end)中
         *  2.遍历tempArray[begin,mid) 、tempArray[mid+1,end)将数据按照大小放回array[begin,end)区间中
         */
        for (int k = begin; k < end; k++) {
            // 将array[begin,end) 复制到 tempArray[begin,end);
            tempArray[k] = array[k];
        }
        /**
         * 按顺序合并到array[begin,end);
         */
        int i = begin, j = mid;

        for (int k = begin; k < end; k++) { // 归并到array[begin,end)
            if (i >= mid){ // 剩下右边
                array[k] = tempArray[j++];
            } else if (j >= end){ // 剩下左边
                array[k] = tempArray[i++];
            } else if (cmpElement(tempArray[j],tempArray[i]) < 0){
                array[k] = tempArray[j++];
            } else {
                array[k] = tempArray[i++];
            }
        }
    }







    public void mergeSort_base(){
        /**
         * 前闭后开区间
         */
        divide(0,array.length);
    }

    /**
     * 切分操作
     */
    protected void divide(int begin, int end){
        if (end - begin <= 1) return;
        int mid = (begin + end) >> 1;
        divide(begin,mid);
        divide(mid,end);
        merge(begin,mid,end);
    }

    /**
     * 合并操作
     *  begin           mid          end
     *   2    4    6     3    5   7
     *
     */
    protected void merge(int begin, int mid, int end){
        /**
         * 这么泛型 我也没看懂。。。
         *
         * 此处将临时数组的创建放在此处，会创建经常创建数组，影响时间
         */
        T[] tem = (T[]) new Comparable[end-begin];
        int ls = begin;
        int le = mid - 1;
        int rs = mid;
        int re = end - 1;
        int k = 0;

        while (ls <= le && rs <= re){
            /*
               cmp(ls, rs) <= 0  会影响稳定性
             */
            tem[k++] = cmp(ls, rs) <= 0 ? array[ls++] :array[rs++];
        }

        while (ls <= le){
            tem[k++] = array[ls++];
        }

        while (rs <= re){
            tem[k++] = array[rs++];
        }

        for (int i = 0; i < tem.length; i++) {
            array[begin + i] = tem[i];
        }
    }
}
