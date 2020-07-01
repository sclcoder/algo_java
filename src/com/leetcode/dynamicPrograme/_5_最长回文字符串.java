package com.leetcode.dynamicPrograme;

public class _5_最长回文字符串 {
    public static void main(String[] args) {
        String string = "cbbd";
//        System.out.println(longestPalindrome(string));
//        System.out.println(longestPalindrome1(string));
        System.out.println(longestPalindromeFinal(string));
    }

    /**    1、以这种二维矩阵(数组)能表示出所有子串的起始位置[i,j]即表示所有的子串信息 其中j>=i
     *      j    0    1    2    3     4
     *   i  dp   a    b    a    s     d
     *   0   a   T    F    T    F     F
     *   1   b        T    F    F     F
     *   2   a             T    F     F
     *   3   s                  T     F
     *   4   d                        T

     */
    public static String longestPalindromeFinal(String s){
        if (s == null || s.length() == 0) return s;
        char[] cs = s.toCharArray();
        /**
         * dp[i][j] 记录 cs(i,j)这个子串是否是回文子串
         * 如果 j-i<=1 那么 dp[i][j] = cs[i] == cs[j];
         * 如果 j-i>1 则  dp[i][j] = cs[i]==cs[j] && dp[i+1][j-1]
         * 注意: dp[i][j]的推导与dp[i+1][j-1]相关。遍历时主要先后顺序。此处i的遍历应该从大到小
         * 题目要求求解最长回文子串，只需要记录其起始位置和长度即可
         */
        boolean[][] dp = new boolean[cs.length][cs.length];
        int maxLen = 1;
        int begin = 0;
        for (int i = cs.length; i >= 0; i--) {
            for (int j = i; j < cs.length; j++) {
                if (j-i<=1){
                    dp[i][j] = cs[i] == cs[j];
                } else {
                    dp[i][j] = cs[i]==cs[j] && dp[i+1][j-1];
                }
                int len = j-i+1;
                if (dp[i][j] && len > maxLen){
                   maxLen = len;
                   begin = i;
                }
            }
        }

        return new  String(cs,begin,maxLen);
    }

    /**
     * “动态规划”最关键的步骤是想清楚“状态如何转移”，事实上，“回文”是天然具有“状态转移”性质的：
     * 一个回文去掉两头以后，剩下的部分依然是回文（这里暂不讨论边界）。
     *
     * 依然从回文串的定义展开讨论：
     * 1、如果一个字符串的头尾两个字符都不相等，那么这个字符串一定不是回文串
     * 2、如果一个字符串的头尾两个字符相等，才有必要继续判断下去。
     * （1）如果里面的子串是回文，整体就是回文串
     * （2）如果里面的子串不是回文串，整体就不是回文串。
     * 即在头尾字符相等的情况下，里面子串的回文性质据定了整个子串的回文性质，这就是状态转移。因此可以把“状态”定义为原字符串的一个子串是否为回文子串。
     *
     * 作者：liweiwei1419
     * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring/solution/zhong-xin-kuo-san-dong-tai-gui-hua-by-liweiwei1419/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public static String longestPalindrome1(String s){
        int len = s.length();
        // dp[i][j] 表示子串 s[i, j] 是否为回文子串。
        boolean [][] dp = new boolean[len][len];
        int start = 0;
        int max = 0;
        for (int j = 0; j < len; j++) {
            for (int i = 0; i <= j; i++) {
                if (s.charAt(i) == s.charAt(j)){
                    if ( i==j || (j - 1) - (i + 1) <= 0){  // [i+1, j-1] 区间 如 ... a b a ...    ... a a ...
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i+1][j-1]; //  dp[i+1][j-1]已经在dp[i][j]之前初始化了
                    }
                } else {
                    dp[i][j] =  false;
                }
                System.out.println("("+i + "," + j + ")" + " 字符串: " + s.substring(i,j+1) + " 是否回文: "+ dp[i][j]);
                if (dp[i][j] == true ){ //是回文
                    if (j-i+1 > max){
                        max = j-i+1;
                        start = i;
                    }
                }
            }
        }

        return s.substring(start,start + max);
    }

    /**
     *  暴力解法
     */
    public static String longestPalindrome(String s) {
        String ans = "";
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String subS = s.substring(i,j);
                if (isPalindrome(subS)){
                    if (ans.length() < subS.length()){
                        ans = subS;
                    }
                }
            }
        }

        return ans;
    }

    public static boolean isPalindrome(String s){
        int len = s.length();
        /**
         *   [0 , mid) 前闭后开
         *   a b b a
         *   0 1 2 3
         *   a b c b a
         *   0 1 2 3 4
         */
        for (int i = 0; i < len / 2; i++) {
            if (s.charAt(i) != s.charAt(len-1-i)) return false;
        }
        return true;
    }

}
