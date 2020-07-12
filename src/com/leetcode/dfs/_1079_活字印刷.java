package com.leetcode.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 1079. 活字印刷
 *
 * 你有一套活字字模 tiles，其中每个字模上都刻有一个字母 tiles[i]。返回你可以印出的非空字母序列的数目。
 * 注意：本题中，每个活字字模只能使用一次。
 *
 * 示例 1：
 * 输入："AAB"
 * 输出：8
 * 解释：可能的序列为 "A", "B", "AA", "AB", "BA", "AAB", "ABA", "BAA"。
 *
 * 示例 2：
 * 输入："AAABBC"
 * 输出：188
 *
 * 提示：
 *     1 <= tiles.length <= 7
 *     tiles 由大写英文字母组成
 */
public class _1079_活字印刷 {
    public static void main(String[] args) {
        System.out.println(numTilePossibilities("AAABBC"));
    }
    /// 直接计数,不输出字符串信息
    static int count = 0;
    public static int numTilePossibilities(String tiles) {
        char[] chars = tiles.toCharArray();
        Arrays.sort(chars);
        int[] used = new int[chars.length];
        dfs(chars,used);
        return count;
    }

    /**            排列问题
     *         A      A      B
     *       A  B    A B    A  A
     *      B   A   B  A   A   A
     */
    public static void dfs(char[] chars, int[] used){
        for (int i = 0; i < chars.length; i++) {
            if (used[i] == 1) continue;
            if (i>0 && chars[i-1] == chars[i] && used[i-1] == 0) continue;
            count++;
            used[i] = 1;
            dfs(chars,used);
            used[i] = 0;
        }
    }

    public static List<String> numTilePossibilities_detail(String tiles) {
         List<String> result = new ArrayList<>();
         char[] chars = tiles.toCharArray();
         Arrays.sort(chars);
         int[] used = new int[chars.length];
         dfs(chars,"",used,result);
         return result;
    }

    /**            排列问题
     *         A      A      B
     *       A  B    A B    A  A
     *      B   A   B  A   A   A
     */
    /// 考虑对其排个序
    public static void dfs(char[] chars, String string,  int[] used , List<String> result){

        for (int i = 0; i < chars.length; i++) {
            if (used[i] == 1) continue;
            if (i>0 && chars[i-1] == chars[i] && used[i-1] == 0) continue;
            string = string + chars[i];
            used[i] = 1;
            result.add(string);
            dfs(chars,string,used,result);
            string = string.substring(0,string.length()-1);
            used[i] = 0;
        }
    }


    /// 以前写的白痴版本
    static List<String> result = new LinkedList<>();
    public static List<String> numTilePossibilities_idot(String tiles) {
        int[] used  = new int[tiles.length()];
        dfs(tiles,"",used);
        return result;
    }

    private static void dfs(String titles, String selStr, int[] used){
        if (selStr.length() != titles.length()){
            for (int i = 0; i < titles.length(); i++) {
                if (used[i] == 1) continue;
                selStr = selStr + titles.charAt(i); // 路径
                used[i] = 1; // 该位置的字使用过了
                if (!result.contains(selStr)) { // 去重
                    result.add(new String(selStr));  // 一个结果
                }
                dfs(titles,selStr,used);  //  缩小选择范围 继续查找
                selStr = selStr.substring(0,selStr.length()-1); // 撤销选择 回溯
                used[i] = 0; // 状态重置
            }
        }
    }

}
