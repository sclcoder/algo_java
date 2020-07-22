package com.ds_algo.z_sorts;

public class BubbleSort extends Sort {
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
