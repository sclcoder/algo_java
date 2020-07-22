package com.ds_algo.z_sorts.cmp;

import com.ds_algo.z_sorts.Sort;

/**
 * 可以看做对插入排序的一种优化
 * 我当时看的是马士兵的视频学习的，解说的比较简单
 * mj的我看了一下ppt,感觉讲的复杂了呀
 *
 * @param <T>
 */
public class ShellSort<T extends Comparable<T>> extends Sort<T> {
    @Override
    protected void sort() {
//        shell_common();
        shell_advance();

    }


    public void shell_common(){
        /**
         * 原始未优化的插入排序：
         */
//        for (int i = 1; i < array.length; i++) {
//            for (int j = i; j > 0  ; j--) {
//                if (cmpElement(array[j],array[j-1]) < 0){
//                    swap(j,j-1);
//                }
//            }
//        }



        /**
         * 改造成希尔排序
         * 二分步长
         */
//        int gap = array.length >> 1;

//        while (gap >= 1){
//            for (int i = gap; i < array.length; i++) {
//                for (int j = i; j - gap >= 0  ; j-= gap) {
//                    if (cmpElement(array[j],array[j-gap]) < 0){
//                        swap(j,j-gap);
//                    }
//                }
//            }
//            gap = gap >> 1;
//        }

        /**
         * h = 3 * h + 1 步长
         */
        int h = array.length / 3;

        while (h >= 1){
            for (int i = h; i < array.length; i++) {
                for (int j = i; j - h >= 0  ; j-= h) {
                    if (cmpElement(array[j],array[j-h]) < 0){
                        swap(j,j-h);
                    }
                }
            }
            h = (h + 1) / 3;
        }
    }


    public void shell_advance() {
        /**
         * 基础优化的插入排序：
         */
//        for (int begin = 1; begin < array.length; begin++) {
//            int cur = begin;
//            T v = array[cur];
//            while (cur > 0){
//                if (cmpElement(v, array[cur-1]) < 0){
//                    array[cur] = array[cur-1];
//                    cur--;
//                } else {
//                    break; /// 漏了会死循环
//                }
//            }
//            array[cur] = v;
//        }


        /**
         * h = 3 * h + 1 步长
         */
        int h = array.length / 3;

        while (h >= 1){
            for (int i = h; i < array.length; i++) {
                int cur = i;
                T v = array[cur];

                while (cur - h >= 0 ){
                    if (cmpElement(v,array[cur-h]) < 0){
                        array[cur] = array[cur-h];
                        cur -= h;
                    } else {
                        break;
                    }
                }
                array[cur] = v;
            }
            h = (h + 1) / 3;
        }


    }


}
