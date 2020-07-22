package com.ds_algo.z_sorts.cmp;

import com.ds_algo.z_sorts.Sort;

public class InsertSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
//        insert_common();
        insert_advance();
    }

    /**
     * 普通版的插入排序
     */
    public void  insert_common(){
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0  ; j--) {
                if (cmpElement(array[j],array[j-1]) < 0){
                    swap(j,j-1);
                }
            }
        }
    }

    /**
     * 优化版的插入排序
     */
    public void  insert_advance(){

        for (int begin = 1; begin < array.length; begin++) {
            int cur = begin;
            T v = array[cur];
            while (cur - 1 >= 0){
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

    /**
     * 使用二分法优化的插入排序
     */
    public void  insert_binary_search(){

    }
}
