package com.leetcode.dynamicPrograme;

public class _53_最大子序列的和 {
    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }

    /**
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     *
     * 示例:
     *
     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     *
     * 进阶:
     *
     * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/maximum-subarray
     */




    /**
     * 解题思路：
     * 示例: [a, b , c, d , e]
     * 解答这类题目, 省略不掉遍历, 因此我们先从遍历方式说起
     * 通常我们遍历子串或者子序列有三种遍历方式
     *     以某个节点为开头的所有子序列: 如 [a]，[a, b]，[ a, b, c] ... 再从以 b 为开头的子序列开始遍历 [b] [b, c]。
     *     根据子序列的长度为标杆，如先遍历出子序列长度为 1 的子序列，在遍历出长度为 2 的 等等。
     *     以子序列的结束节点为基准，先遍历出以某个节点为结束的所有子序列，因为每个节点都可能会是子序列的结束节点，因此要遍历下整个序列，如: 以 b 为结束点的所有子序列: [a , b] [b] 以 c 为结束点的所有子序列: [a, b, c] [b, c] [ c ]。
     * 第一种遍历方式通常用于暴力解法, 第二种遍历方式 leetcode (5. 最长回文子串 ) 中的解法就用到了。
     * 第三种遍历方式 因为可以产生递推关系, 采用动态规划时, 经常通过此种遍历方式, 如 背包问题, 最大公共子串 , 这里的动态规划解法也是以 先遍历出 以某个节点为结束节点的所有子序列 的思路
     * 对于刚接触动态规划的, 我感觉熟悉第三种遍历方式是需要抓住的核心
     * 因为我们通常的惯性思维是以子序列的开头为基准，先遍历出以 a 为开头的所有子序列，再遍历出以 b 为开头的...但是动态规划为了找到不同子序列之间的递推关系，恰恰是以子序列的结束点为基准的，这点开阔了我们的思路。
     * 我在网上看不少解答时，直接阅读其代码，总是感觉很理解很吃力，因为好多没有写清楚，一些遍历到底代表什么意思，看了许久仍不知所以然，下面的代码中摘录了 维基中的解释，感觉比较清楚，供大家理解参考。
     * 链接：https://leetcode-cn.com/problems/maximum-subarray/solution/xiang-xi-jie-du-dong-tai-gui-hua-de-shi-xian-yi-li/
     */
    public static int maxSubArray(int[] nums) {
        // 动态规划: dp[i]存放以nums[i]结尾的子数组的最大和
        // 如果dp[i-1]>0则 dp[i] = dp[i-1] + nums[i] 否则 dp[i] = nums[i]
        int [] dp = new int[nums.length + 1];
        int max = dp[0] = nums[0];
        for(int i = 1 ; i < nums.length; i++){
            if(dp[i-1] > 0){
                dp[i] = dp[i-1] + nums[i];
            } else {
                dp[i] = nums[i];
            }
            if(dp[i] > max){
                max = dp[i];
            }
        }
        return max;
    }
}
