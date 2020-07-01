package com.leetcode.dynamicPrograme;

/**
 * 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列。
 * <p>
 * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
 * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。
 * <p>
 * 若这两个字符串没有公共子序列，则返回 0。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入：text1 = "abcde", text2 = "ace"
 * 输出：3
 * 解释：最长公共子序列是 "ace"，它的长度为 3。
 * <p>
 * 示例 2:
 * <p>
 * 输入：text1 = "abc", text2 = "abc"
 * 输出：3
 * 解释：最长公共子序列是 "abc"，它的长度为 3。
 * <p>
 * 示例 3:
 * <p>
 * 输入：text1 = "abc", text2 = "def"
 * 输出：0
 * 解释：两个字符串没有公共子序列，返回 0。
 * <p>
 * 提示:
 * 1 <= text1.length <= 1000
 * 1 <= text2.length <= 1000
 * 输入的字符串只含有小写英文字符。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-common-subsequence
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * j   0  1  2  3  4  5  6
 * i  dp  a  b  c  b  d  e  f
 * 0  b   0  1  1  1  1  1  1
 * 1  c   0  1  2  2  2  2  2
 * 2  e   0  1  2  2  2  3  3
 * 3  d   0
 * 4  a   1
 * 5  c
 * 6  d
 * 7  f
 * <p>
 * dp[i][j] 记录以text1[i]、text2[j]结尾时两个字符串的最长公共子序列大小
 * if text1[i] == text2[j] , dp[i][j] = dp[i-1][j-1]  + 1;
 * if text1[i] != text2[j] , dp[i][j] = Math.max(dp[i-1][j] , dp[i][j-1])
 * 通过递推关系知道: 后边的值依赖前边的计算结果，需要首先计算最dp[0][0]的值
 *
 *  解析:
 *  1、为什么 if text1[i] == text2[j] , dp[i][j] = dp[i-1][j-1]  + 1;
 *  因为 text1[i] == text2[j]时 说明这个字符可以直接加到公共子串中了计数+1。没必要再判断dp[i-1][j] or dp[i][j-1])的情况，因为这两种情况
 *  可能匹配到最后一个字符也可能匹配不到，但是无论其是否能匹配到，我们已经能够确定dp[i][j]可以一定能匹配到最后一个字符了只需要计数+1即可。所以可直接通过dp[i-1][j-1]推导。
 *  2、text1[i] != text2[j]时为什么要Math.max(dp[i-1][j] , dp[i][j-1])
 *  两者的最后一个字符没有匹配，当这样能说明两者的最后一个字符没在公共子串内吗？显然不能这样做，因为如果text1[i]这个字符可能和text2(0..j)中的其他字符
 *  匹配。同理text2也是如此。
 *
 */
public class _1143_最长公共子序列 {
    public static void main(String[] args) {
        System.out.println(longestCommonSubsequence2("abcdeb", "aceb"));
    }

    public static int longestCommonSubsequence2(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) return 0;
        char[] cs1 = text1.toCharArray();
        int len1 = cs1.length;
        char[] cs2 = text2.toCharArray();
        int len2 = cs2.length;
        int[][] dp = new int[len1][len2];
        dp[0][0] = cs1[0] == cs2[0] ? 1 : 0;
        for (int i = 1; i < len1; i++) dp[i][0] = cs1[i] == cs2[0] ? 1 : dp[i - 1][0];
        for (int j = 1; j < len2; j++) dp[0][j] = cs1[0] == cs2[j]  ? 1 : dp[0][j - 1];

        for (int i = 1; i < len1; i++) {
            for (int j = 1; j < len2; j++) {
                if (cs1[i] == cs2[j]){
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return dp[len1 - 1][len2 - 1];
    }
        public static int longestCommonSubsequence (String text1, String text2){
            /**
             *  dp[i][j]记录以text1的前i个字符和text2的前j个字符最长公共子序列的长度
             *  状态转移:
             *  t1[i-1] == t2[j-1] 则d[i][j] = d[i-1][j-1]+1
             *  t1[i-1] != t2[j-1] 则d[i][j] = max{d[i][j-1] , d[i-1][j]}
             *  初始化: d[0][j] = 0 , d[i][0] = 0
             */
            char[] cs1 = text1.toCharArray();
            int len1 = cs1.length;
            char[] cs2 = text2.toCharArray();
            int len2 = cs2.length;
            int[][] dp = new int[len1 + 1][len2 + 1];
            dp[0][0] = 0;
            for (int i = 1; i <= len1; i++) {
                dp[i][0] = 0;
                for (int j = 1; j <= len2; j++) {
                    dp[0][j] = 0;
                    if (cs1[i - 1] == cs2[j - 1]) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            return dp[len1][len2];
        }
    }




