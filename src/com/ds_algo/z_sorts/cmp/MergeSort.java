package com.ds_algo.z_sorts.cmp;

import com.ds_algo.z_sorts.Sort;

public class MergeSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        /**
         * 前闭后开区间
         */
        divid(0,array.length);
    }

    /**
     * 切分操作
     */
    protected void divid(int begin, int end){
        if (end - begin <= 1) return;
        int mid = (begin + end) >> 1;
        divid(begin,mid);
        divid(mid,end);
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
