package com.ds_algo.z_sorts.cmp;

import com.ds_algo.z_sorts.Sort;

public class InsertSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            T v = array[cur];
            while (cur > 0){
                if (cmpElement(v, array[cur-1]) < 0){
                    array[cur] = array[cur-1];
                    cur--;
                } else {
                    break; /// 漏了会死循环
                }
            }
            array[cur] = v;
        }
    }
}
