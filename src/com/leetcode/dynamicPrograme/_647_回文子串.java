package com.leetcode.dynamicPrograme;

public class _647_回文子串 {
    public static void main(String[] args) {
        System.out.println(countSubstrings("aba"));
    }

    static int num = 0;
    public static int countSubstrings(String s) {
        /**
         *  dp[j][i]记录 字符串 s[j,i]是否为回文
         *  dp[j][i] 状态转移
         *  1. s[j] != s[i] , dp[j][i] = false;
         *   2. s[j] = s[i]
         *   2.1 若[j+1,i-1]这个区间的长度 小于等于0  dp[j][i] = true
         *   2.2 否则 dp[j][i] = dp[j+1][i-1] -------------状态转移
         *
         */
        int len = s.length();
        boolean [][]dp = new boolean[len][len];
        /**
         * dp[j][i] = dp[j+1][i-1]这个状态转移过程确定i必须在外层循环, 只有这样能保证dp[j+1][i-1]在dp[j][i]之前计算出结果
         */
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(j) == s.charAt(i)){
                    if ((i-1) - (j+1) <= 0){
                        dp[j][i] = true;
                    } else {
                        dp[j][i] = dp[j+1][i-1];
                    }
                } else {
                  dp[j][i] = false;
                }
                if (dp[j][i] == true) {
                    num++;
                }
//                System.out.println(dp[j][i]);
            }
        }
        return num;
    }

    static int count = 0;
    public static int countSubstrings1(String s) {
        int len = s.length();
        for (int i = 0; i <=len; i++) {
            for (int j = 0; j < i; j++) {
                System.out.println(s.substring(j,i));
                if (isValid(s.substring(j,i))){
                    count++;
                }
            }
        }
        return count;
    }
    public static boolean isValid(String s){
        int len = s.length();
        for (int i = 0; i < len / 2; i++) {
            if (s.charAt(i) != s.charAt(len-1-i)) return false;
        }
        return true;
    }
    /**
     * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
     *
     * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。
     *
     * 示例 1:
     *
     * 输入: "abc"
     * 输出: 3
     * 解释: 三个回文子串: "a", "b", "c".
     *
     * 示例 2:
     *
     * 输入: "aaa"
     * 输出: 6
     * 说明: 6个回文子串: "a", "a", "a", "aa", "aa", "aaa".
     *
     * 注意:
     *
     *     输入的字符串长度不会超过1000。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/palindromic-substrings
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
}
