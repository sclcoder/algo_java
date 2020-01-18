package com.leetcode.dynamicPrograme;

public class _5_最长回文字符串 {
    public static void main(String[] args) {
        String string = "abasdfasdf";
        System.out.println(longestPalindrome(string));
        System.out.println(longestPalindrome1(string));
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
    /**
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     *
     * 示例 1：
     *
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     *
     * 示例 2：
     *
     * 输入: "cbbd"
     * 输出: "bb"
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
}
