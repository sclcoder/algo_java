package com.leetcode.dynamicPrograme;
/**
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
 *
 * 注意你不能在买入股票前卖出股票。
 *
 * 示例 1:
 *
 * 输入: [7,1,5,3,6,4]
 * 输出: 5
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
 *
 * 示例 2:
 *
 * 输入: [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _121_买卖股票的最佳时机 {

    public static void main(String[] args) {
       maxProfit2(new int[]{7,1,5,3,10,4});
    }

    /**
     * 使用动态规划的方式
     * 需要将问题转化一下
     * [7,1,5,3,6,4] 为每天股票的价格
     * 第i天买第j天卖出的利润为i-j天每天的股价差之和
     * 每天股价差数组为 [0,-6,4,-2,3,-2]
     * 那么第i天买入第j天卖出的利润就可以通过该数组来推断出来
     * 所以求最大利润转换为求最大连续子序列的和问题
     */
    public static int maxProfit2(int[] prices) {
        if (prices == null ||  prices.length <= 0) return 0;
        int[] profitPerDay = new int[prices.length];
        profitPerDay[0] = 0;
        for (int i = 1; i < prices.length; i++) {
            profitPerDay[i] = prices[i] - prices[i-1];
//            System.out.println(profitPerDay[i]);
        }
        // dp[i] 记录第i天卖出的时能最大利润
        // dp[i] 分为
        // 1. dp[i-1] > 0  dp[i] = dp[i-1] +  profitPerDay[i]
        // 2. dp[i-1] < 0   dp[i] = profitPerDay[i]
        int[] dp = new int[prices.length];
        dp[0] = profitPerDay[0];
        int maxProfit = dp[0];
        for (int i = 1; i < profitPerDay.length; i++) {
            dp[i] = dp[i-1] < 0 ? profitPerDay[i] : dp[i-1] + profitPerDay[i];
            if (dp[i] > maxProfit){
                maxProfit = dp[i];
            }
        }
        System.out.println(maxProfit);
        return maxProfit;
    }

    /// 这不算动态规划
    public static int maxProfit(int[] prices) {
        // 第i天卖出获得最大利润
        int[]dp = new int[prices.length+1];
        dp[0] = 0;
        int minPrice  = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            minPrice = Math.min(minPrice,prices[i-1]); // 之前最低价
            dp[i] = prices[i] - minPrice;
            System.out.println("dp[" + i +  "]:" + dp[i]);
            maxProfit = Math.max(dp[i],maxProfit);
        }
        System.out.println(maxProfit);
        return Math.max(dp[0],maxProfit);
    }

}
