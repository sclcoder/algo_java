package com.leetcode.dfs;


import java.util.ArrayList;
import java.util.List;

/**
 * 面试题 08.07. 无重复字符串的排列组合
 *
 * 无重复字符串的排列组合。编写一种方法，计算某字符串的所有排列组合，字符串每个字符均不相同。
 *
 * 示例1:
 *
 *  输入：S = "qwe"
 *  输出：["qwe", "qew", "wqe", "weq", "ewq", "eqw"]
 *
 * 示例2:
 *
 *  输入：S = "ab"
 *  输出：["ab", "ba"]
 *
 * 提示:
 *
 *     字符都是英文字母。
 *     字符串长度在[1, 9]之间。
 */
public class _面试题8007_无重复字符串的排列组合 {

    static List<String> list = new ArrayList<>();
    public static String[] permutation(String S) {
        char[] ch = S.toCharArray();
        StringBuilder sb = new StringBuilder();
        int[] used = new int[ch.length];
        dfs(ch,used,sb);

        /// 结果格式处理
        String[] res = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public static void dfs(char[] ch , int[]used, StringBuilder sb){
        if (sb.length() == ch.length){ /// 递归结束
            list.add(sb.toString());
            return;
        }
        /// 每次面临的选择
        for (int i = 0; i < ch.length; i++) {
            if (used[i] == 1) continue;
            sb.append(ch[i]);
            used[i] = 1;
            dfs(ch,used,sb);
            sb.deleteCharAt(sb.length()-1);
            used[i] = 0;
        }
    }

}
