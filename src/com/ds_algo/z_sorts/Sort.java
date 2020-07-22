package com.ds_algo.z_sorts;

import java.text.DecimalFormat;
import java.util.Arrays;

public abstract class Sort {
    protected Integer[] array;
    private int compareCount;
    private int swapCount;
    private long time;
    private DecimalFormat fmt = new DecimalFormat("#.00");


    /**
     * 非抽象方法: 子类可以直接调用
     * @param array
     */
    public void sort(Integer[] array){
        if (array == null || array.length <2) return;
        this.array = array;
        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    public void swap(int i1, int i2){
        Integer tem =array[i1];
        array[i1] = array[i2];
        array[i2] = tem;
        swapCount++;
    }

    /**
     * 返回值等于0，代表 array[i1] == array[i2]
     * 返回值小于0，代表 array[i1] < array[i2]
     * 返回值大于0，代表 array[i1] > array[i2]
     */
    public int cmp(int i1, int i2){
        compareCount++;
        return array[i1] - array[i2];
    }

    public int cmpElement(Integer v1, Integer v2){
        compareCount++;
        return v1 - v2;
    }


    /**
     * 抽象方法: 子类必须重写
     */
    protected abstract void sort();

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(compareCount);
        String swapCountStr = "交换：" + numberString(swapCount);
//        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";
    }


    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }
}
