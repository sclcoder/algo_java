package com.leetcode.dynamicPrograme;

/**
 * https://leetcode-cn.com/problems/longest-continuous-increasing-subsequence/
 * 给定一个未经排序的整数数组，找到最长且连续的的递增序列，并返回该序列的长度。
 * 示例 1:
 * 输入: [1,3,5,4,7]
 * 输出: 3
 * 解释: 最长连续递增序列是 [1,3,5], 长度为3。
 * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为5和7在原数组里被4隔开。
 *
 * 示例 2:
 *
 * 输入: [2,2,2,2,2]
 * 输出: 1
 * 解释: 最长连续递增序列是 [2], 长度为1。
 *
 *
 *
 * 注意：数组长度不会超过10000。
 */
public class _674_最长连续递增序列 {
    public int findLengthOfLCIS(int[] nums) {
        /// 真正的动态规划应该这样: dp[i][j] 记录s[i,j]这个区间是否为连续递增区间。最终找到最长区间即可。
        /// 此题和最长回文子串长度类似解法
        /**
         *  dp[i][j] 记录nums[i,j]这个区间是否是连续递增区间
         *      j    0   1   2   3   4
         *  i   dp   1   3   5   4   7
         *  0   1    T   T   T   F   F
         *  1   3        T   T   F   F
         *  2   5            T   F   F
         *  3   4                T   T
         *  4   7                    T
         *  j-i+1 = 1;
         *   dp[i][j] = true
         *  j-i+1 = 2
         *  dp[i][j] ==  nums[j] > nums[i]
         *  j-i+1 > 2
         *  dp[i][j] = dp[i+1][j-1] && nums[i] < nums[i+1] &&  nums[j-1] < nums[j]
         */
        if (nums == null || nums.length == 0) return 0;
        int numsLen = nums.length;
        boolean[][] dp = new boolean[numsLen][numsLen];
//        int begin = 0;
        int maxLen = 1;
        for (int i = numsLen; i >= 0; i--) {
            for (int j = i; j < numsLen ; j++) {
                int len = j - i + 1;
                if (len == 1){
                    dp[i][j] = true;
                } else if (len == 2){
                    dp[i][j] = nums[j] > nums[i];
                } else {
                    dp[i][j] = dp[i+1][j-1] && nums[i] < nums[i+1] &&  nums[j-1] < nums[j];
                }
                if (dp[i][j] == true &&  len > maxLen){
                    maxLen = Math.max(len,maxLen);
//                    begin = i;
                }
            }
        }


        return maxLen;
    }
}
