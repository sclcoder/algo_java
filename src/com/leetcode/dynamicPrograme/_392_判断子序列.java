package com.leetcode.dynamicPrograme;
/**
 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
 *
 * 你可以认为 s 和 t 中仅包含英文小写字母。字符串 t 可能会很长（长度 ~= 500,000），而 s 是个短字符串（长度 <=100）。
 *
 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
 *
 * 示例 1:
 * s = "abc", t = "ahbgdc"
 *
 * 返回 true.
 *
 * 示例 2:
 * s = "axc", t = "ahbgdc"
 *
 * 返回 false.
 *
 * 后续挑战 :
 *
 * 如果有大量输入的 S，称作S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/is-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _392_判断子序列 {
    public static void main(String[] args) {
        System.out.println(isSubsequence2("abc","cahbgdxc"));
    }

    // 双指针
    public static boolean isSubsequence(String s, String t) {
        int i = 0;
        int j = 0;
        while (i < s.length() && j < t.length()){
            if (s.charAt(i) == t.charAt(j)){
                i++;
            }
            j++;
        }
        return i == s.length();
    }

    /**  动态规划 状态转移通过dp-table来思考
     *        a  c  b  d  c  f  d
     *    a   1  1  1  1  1  1  1
     *    b   0  0  1  1  1  1  1
     *    c   0  0  0  0  1  1  1
     */
    public static boolean isSubsequence2 (String s, String t){
        int slen = s.length();
        int tlen = t.length();
        if (slen > tlen) return false;
        if (slen == 0) return true;
        boolean[][] dp = new boolean[slen][tlen];
        for (int i = 0; i < slen; i++) {
            dp[i][0] = false;
        }
        for (int i = 0; i < tlen; i++) {
            dp[0][i] = true;
        }
        dp[0][0] = s.charAt(0) == t.charAt(0);

        for (int i = 1; i < slen; i++) {
            for (int j = 1; j < tlen; j++) {
                if (s.charAt(i) == t.charAt(j)){
                    dp[i][j] =  dp[i-1][j-1];
                } else {
                    dp[i][j] =  dp[i][j-1];
                }
            }
        }
        return dp[slen-1][tlen-1];
    }


}
