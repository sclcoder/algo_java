package com.ds_algo.z_sorts;

public class SelectSort extends Sort{
    @Override
    protected void sort() {
        int length = array.length;
        for (int end = length-1; end >= 0 ; end--) {
            int maxIndex = 0;
            for (int begin = 1; begin <= end ; begin++) {
                if (cmp(begin,maxIndex) >= 0){
                    maxIndex = begin;
                }
            }
            swap(maxIndex,end);
        }
    }
}
