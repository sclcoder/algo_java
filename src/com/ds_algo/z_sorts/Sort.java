package com.ds_algo.z_sorts;

import com.ds_algo.z_sorts.cmp.SelectSort;

import java.text.DecimalFormat;

public abstract class Sort<T extends Comparable<T>> implements Comparable<Sort<T>> {
    protected T[] array;
    private long compareCount;
    private long swapCount;
    private long time;
    private DecimalFormat fmt = new DecimalFormat("#.00");


    /**
     * 非抽象方法: 子类可以直接调用
     * @param array
     */
    public void sort(T[] array){
        if (array == null || array.length <2) return;
        this.array = array;
        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    public void swap(int i1, int i2){
        T tem =array[i1];
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
        return array[i1].compareTo(array[i2]);
    }

    public int cmpElement(T v1, T v2){
        compareCount++;
        return v1.compareTo(v2);
    }


    /**
     * 抽象方法: 子类必须重写
     */
    protected abstract void sort();

    /**
     * 实现Comparable接口
     * 自定义 Sort对象的排序
     */
    @Override
    public int compareTo(Sort o) {
        int result = (int)(time - o.time);
        if (result != 0) return result;
        result = (int) (swapCount - o.swapCount);
        if (result != 0) return result;
        result = (int)(compareCount - o.compareCount);
        return result;
    }


    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(compareCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
                + stableStr + " \t"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";
    }

    private boolean isStable() {
//        if (this instanceof RadixSort) return true;
//        if (this instanceof CountingSort) return true;
//        if (this instanceof ShellSort) return false;
        if (this instanceof SelectSort) return false;
        Student[] students = new Student[20];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(i * 10, 10);
        }
        sort((T[]) students);
        for (int i = 1; i < students.length; i++) {
            int score = students[i].score;
            int prevScore = students[i - 1].score;
            if (score != prevScore + 10) return false;
        }
        return true;
    }


    private String numberString(long number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }
}
