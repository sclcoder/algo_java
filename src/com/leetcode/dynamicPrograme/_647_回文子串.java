package com.leetcode.dynamicPrograme;
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
public class _647_回文子串 {

    public static void main(String[] args) {
//        System.out.println(countSubstrings("abavfdfdfg"));
//        countSubStrings2("abcba");
        System.out.println(getMaxpalindromic("abcddfeg"));
    }

    /**  动态规划 状态转移通过dp-table来思考
     *     j   0  1  2   3   4   5
     * i  dp   a  b  c   b   c   d
     * 0   a   T  F  F   F   F   F
     * 1   b      T  F   T   F   F
     * 2   c         T   F   T   F
     * 3   b             T   F   F
     * 4   c                 T   F
     * 5   d                     T
     *
     * dp[i][j] 记录 s[i,j]这个子串是否为回文串
     * 记录子串长度为 len = j-i+1
     * 如果 len <= 2 那么 dp[i][j] = s[i] == s[j]
     * len > 2 则 dp[i][j] = s[i]==s[j] && dp[i+1][j-1]
     *
     *  由依赖关系可知  i 依赖 i+1 , j依赖j-1 所以需要需要首先计算的边界值为 i的最大值 j的最小值
     *  由因为j需要大于等于i，所以遍历代码如下
     *  所以遍历方式为  由下向上 由左到右的方式
     *  for (int i = cs.length - 1; i >=0 ; i--) {
     *        for (int j = i; j < cs.length; j++)  {
     *        }
     *  }
     */
    /**
     * 获取最大回文子串
     * @param  s 需要计算的参数
     * @return 回文子串
     */
    public static String getMaxpalindromic (String s){
        char[] cs = s.toCharArray();
        if (cs.length <= 1) return s;
        int maxLen = 1;
        int beginInx = 0;
        boolean dp[][] = new boolean[cs.length][cs.length];
        for (int i = cs.length - 1; i >=0 ; i--) {
            for (int j = i; j < cs.length; j++)  {
                int len = j-i+1; // 子串cs[i, j]的长度
                dp[i][j] = cs[i] == cs[j] && ( len <= 2 ||  dp[i+1][j-1]);
                if (dp[i][j] && len > maxLen){ // cs[i,j]是回文串
                    beginInx = i;
                    maxLen = len;
                }
            }
        }
        return new String(cs,beginInx,maxLen);
    }







    public static void countSubStrings2(String s){
        char[] chars = s.toCharArray();
        // dp[i][j] 记录 chars[i,j]这个子串是否为回文串
        // 状态转移方程： s[i] != s[j] dp[i][j] = false
        // s[i] = s[j] 时 如果 (j-i)<= 2,dp[i][j] = true; 如果(j-i)>2 ,dp[i][j] = dp[i+1][j-1]
        // 可以进一步简化逻辑 :  (j-i)<=2, dp[i][j] = s[i]==s[j]
        //                     (j-i)>2 ,dp[i][j] = dp[i+1][j-1] && s[i]==s[j]
        boolean [][] dp = new boolean[chars.length][chars.length];
        for (int i = chars.length - 1; i >= 0 ; i--) {
            for (int j = i; j < chars.length; j++) {
                dp[i][j] = chars[i] == chars[j] && (j-i <= 1 || dp[i+1][j-1]);
//                System.out.print(dp[i][j]);
            }
        }
        for (int i = 0; i < chars.length; i++) {
            for (int j = i; j < chars.length; j++) {
                System.out.print(dp[i][j] + "   ");
            }
            System.out.println();
            System.out.println("#############");
        }
    }

    static int num = 0;
    public static int countSubstrings(String s) {
        /**
         *  dp[j][i]记录 字符串 s[j,i]是否为回文
         *  dp[j][i] 状态转移
         *   1. s[j] != s[i] , dp[j][i] = false;
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
                System.out.println(dp[j][i]);
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

}
