package com.ds_algo.z_sorts.cmp;

import com.ds_algo.z_sorts.Sort;

public class BubbleSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
        int length = array.length;
        for (int i = length; i >=0 ; i--) {
            for (int j = 1; j < i ; j++) {
                if (cmp(j-1,j) > 0){
                    swap(j-1,j);
                }
            }
        }
    }
}
