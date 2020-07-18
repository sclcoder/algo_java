package com.leetcode.dfs;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 面试题 08.08. 有重复字符串的排列组合
 *
 * 有重复字符串的排列组合。编写一种方法，计算某字符串的所有排列组合。
 *
 * 示例1:
 *
 *  输入：S = "qqe"
 *  输出：["eqq","qeq","qqe"]
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
public class _面试题0808有重复字符串的排列组合 {
    static List<String> list = new ArrayList<>();
    static public String[] permutation(String S) {

        char[] chars = S.toCharArray();
        /**
         * 排序很重要。妈的代码都背过了吧，结果没排序，看来还是靠记忆解决的问题，草，努力吧
         * 这里通过画出选择树，在不排序的情况下，解决该问题，比提前排序的复杂度高一些，但是容易想到
         */
//        Arrays.sort(chars);
        int[] used = new int[chars.length];
        StringBuilder sb = new StringBuilder();
        dfs(used,chars,sb);
        System.out.println(list);
        // 格式处理
        String[] res = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    static public void dfs(int[] used , char[] chars, StringBuilder sb){
        if (sb.length() == chars.length){
            list.add(sb.toString());
            return;
        }
        /**
         *                                              java
         *                j                                     a                      v                     a
         *        a       v        a                        j   v   a              j   a   a             j   a   v
         *      v   a   a   a    a   v
         *      a   v   a   a    v   a
         *  java\jaav\jvaa\jvaa\jaav\java
         *
         *  通过一个选择树可以发现问题。当有重复字母时,出现了重复的树
         *  这里通过一个技巧，这个技巧就是先排序后再处理，不过如果不看题解，我很难想到要先排序。
         *
         *  如果不先排序，那么在同一层遇到相同的字母时应该跳过，这个代码应该怎么写呢？
         *
         *
         *
         */
        /// 每次面临的选择
        for (int i = 0; i < chars.length; i++) {
            if (used[i] == 1) continue;
            /**
             *  used[j] == 0 且 chars[j] == cur 说明在同一层 此时遇到相等的元素需要剪枝，因为该分支和之前的分支重复了
             *  used[j] == 1 且 chars[j] == cur 不是同一层 此时即便遇到相等的元素也是不能剪枝的 比如第二层的a 和 第三层的a
             */
            char cur = chars[i];
            boolean valid = true;
            for (int j = 0; j < i; j++) {
                if ( used[j] == 0 && chars[j] == cur){
                    /// 这个cur不能要
                    valid = false;
                }
            }
            if (!valid) continue;
            sb.append(chars[i]);
            used[i] = 1;
            dfs(used, chars, sb);
            sb.deleteCharAt(sb.length()-1);
            used[i] = 0;
        }
    }

    public static void main(String[] args) {
        System.out.println(permutation("java"));
    }
}
