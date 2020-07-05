package com.leetcode.dfs;

import java.util.*;

public class _401_二进制手表 {
    /**
     *  二进制手表顶部有 4 个 LED 代表小时（0-11），底部的 6 个 LED 代表分钟（0-59）。
     * 每个 LED 代表一个 0 或 1，最低位在右侧。
     *  8 4 2 1          时钟
     *  32 16 8 4 2 1    分钟
     *
     *  例如，上面的二进制手表读取 “3:25”。
     *
     * 给定一个非负整数 n 代表当前 LED 亮着的数量，返回所有可能的时间。
     *
     * 案例:
     * 输入: n
     * 返回: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/binary-watch
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public static void main(String[] args) {
        int n = 3;
        /**
         * led灯亮 就好比一个数字的二进制的该位为1 不亮为0
         * 思路一:  遍历 每天的每一分钟 计算每一分钟的那个时刻其 二级制中1的个数 如果个数为n ,那么符合条件
         */
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 60; j++) {
                int bitCount = Integer.bitCount(i) + Integer.bitCount(j);
                if (bitCount == n){
                    System.out.println(i+" : "+j);
                }
            }
        }
        System.out.println("------------------------------------------------");

        binaryWatch(n);

    }

    /**
     *  思路二: 回溯
     *  枚举所有符合条件的亮灯数量 思路为正面刚而不像思路一从侧面进攻
     *  比如 N = 3; 则枚举所有符合条件的情况并计算时间是否合理（剪枝）
     *  在枚举的过程中需要运营DFS策略
     *  这个过程可以模型化为找出亮灯数N的所有组合,很明显可以使用回溯算法
     *  假如 N = 3
     *  一共有10个灯    0 0 0 0 0 0 0 0 0 0
     *  有多少中情况呢  1 1 1 0 0 0 0 0 0 0  .......
     */

    static int[] leds = new int[10];
    static List<String> res = new ArrayList<>();
    public static void binaryWatch(int num){
        // 记录每位点亮与否 默认0
        int onNum = 0; // 点亮数
        int cur = 0; // 游标 记录点亮位置
        dfs(onNum,num,cur);
        Collections.sort(res);
        System.out.println(res);
    }
    /*递归 回溯 dfs*/
    private static void dfs(int onNum, int num, int cur){
        if(onNum == num){
            // 剪枝处理
            int hour = leds[0]*8 + leds[1]* 4 + leds[2]*2 + leds[3];
            int minute = leds[4]*32 + leds[5]*16 + leds[6]*8 + leds[7]*4 + leds[8]*2 + leds[9];
            if(hour < 12 &&  minute < 60){
                String time = hour +  ":" + ((minute < 10) ? ("0" + minute) : minute);
                res.add(time);
            }

            // 递归过程
            for (int i = 0; i < leds.length; i++) {
                System.out.print(leds[i]);
            }
            System.out.println();
        } else {
            // 点亮足够的灯
            for (int i = cur; i < leds.length; i++) {
                leds[i] = 1; // 点亮
                // 递归
                dfs(onNum+1,num,i+1);
                // 回溯
                leds[i] = 0;
            }
        }
    }

    /** 递归回溯过程
     * 1100000000
     * 1010000000
     * 1001000000
     * 1000100000
     * 1000010000
     * 1000001000
     * 1000000100
     * 1000000010
     * 1000000001
     * 0110000000
     * 0101000000
     * 0100100000
     * 0100010000
     * 0100001000
     * 0100000100
     * 0100000010
     * 0100000001
     * 0011000000
     * 0010100000
     * 0010010000
     * 0010001000
     * 0010000100
     * 0010000010
     * 0010000001
     * 0001100000
     * 0001010000
     * 0001001000
     * 0001000100
     * 0001000010
     * 0001000001
     * 0000110000
     * 0000101000
     * 0000100100
     * 0000100010
     * 0000100001
     * 0000011000
     * 0000010100
     * 0000010010
     * 0000010001
     * 0000001100
     * 0000001010
     * 0000001001
     * 0000000110
     * 0000000101
     * 0000000011
     *
     *
     */
}
