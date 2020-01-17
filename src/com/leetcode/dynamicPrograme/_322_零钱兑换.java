package com.leetcode.dynamicPrograme;

import java.util.Arrays;

public class _322_零钱兑换 {

    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{2,10,5} , 11));
        System.out.println(coinChange2(new int[]{2,10,5} , 11));
    }

    /**
     * 递归操作: 有点类型回溯
     */
    public static int coinChange(int[] coins, int amount) {
        if (amount == 0 ) return 0;
        int ans = Integer.MAX_VALUE;
        for (int coin : coins) {
            if (amount - coin  < 0) continue; // 小于0的情况说明本次无解 跳出本次循环
            int subP = coinChange(coins,amount-coin);
            if (subP == -1) continue;
            ans = Math.min(subP + 1,ans);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans; // ans==Integer.MAX_VALUE说明本次循环无解amount-coin<0
    }

    /**
     * 动态规划
     */
    public static int coinChange2(int[] coins, int amount){
        int []dp = new int[amount + 1]; // dp数组记录 0--amount 的金额需要的最少张数  具备最优子问题
        Arrays.fill(dp,amount+1);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins){
                if (i - coin < 0) continue;
                dp[i] = Math.min(dp[i] , dp[i-coin] + 1); // dp[i-coin] 记录了这个金额需要的最小张数
//                System.out.println(dp[i]);
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }


    /**
     * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。
     *
     * 示例 1:
     *
     * 输入: coins = [1, 2, 5], amount = 11
     * 输出: 3
     * 解释: 11 = 5 + 5 + 1
     *
     * 示例 2:
     *
     * 输入: coins = [2], amount = 3
     * 输出: -1
     *
     * 说明:
     * 你可以认为每种硬币的数量是无限的。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/coin-change
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
}
