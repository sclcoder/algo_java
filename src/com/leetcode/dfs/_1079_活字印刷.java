package com.leetcode.dfs;

import java.util.LinkedList;
import java.util.List;

public class _1079_活字印刷 {
    public static void main(String[] args) {
        numTilePossibilities("AABC");
        System.out.println(result);
        System.out.println(result.size());
    }
    static List<String> result = new LinkedList<>();
    public static List<String> numTilePossibilities(String tiles) {
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
