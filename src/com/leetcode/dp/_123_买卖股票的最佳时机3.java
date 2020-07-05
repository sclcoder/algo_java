package com.leetcode.dp;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/
 * <p>
 * 123. 买卖股票的最佳时机 III
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
 * 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 示例 1:
 * 输入: [3,3,5,0,0,3,1,4]
 * 输出: 6
 * 解释: 在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
 * 随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
 * <p>
 * 示例 2:
 * <p>
 * 输入: [1,2,3,4,5]
 * 输出: 4
 * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 * 注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
 * 因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * <p>
 * 示例 3:
 * <p>
 * 输入: [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这个情况下, 没有交易完成, 所以最大利润为 0。
 * <p>

 *
 *
 */

public class _123_买卖股票的最佳时机3 {

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7,1,5,3,6,4}));
    }

    /**
     * 时间复杂度O(n)
     *
     *     把整个股票价格数组以某一天作为两次交易的分界点，分别求每一个部分的最大利润，然后将两边的最大利润加起来，就是至多两次交易的最大利润
     * 具体来说：
     *     首先构建前半部分的利润表，方式和121. 买股票的最佳时机（仅一次交易）中的方法一样，更新最小买入价格，然后求每天卖出的最大利润
     *     然后构建后半部分的利润表，为了将构建两个利润表在一次遍历中完成，我们采用从后往前填充后半部分最大利润表。
     *         方法：从后往前更新最大卖出价格 max, 计算在每天买入的最大利润，这样就可以算出后半部分的利润
     *
     *     股价              ——> 7   1   5  3   6   4
     *     卖出能获取的最大利润    0   0   4  4   5   5
     *
     *     股价                  7   1   5  3   6   4  <--
     *     买入能获得的最大利润    5   5   3  3   0   0
     *
     *     两者交汇的时间点就是那天卖出后能获得的最大利润 和 在那天以及之后再次买入股票时的能获得的最大利润()
     *     最后遍历一遍，求两个利润表和的最大值，就是我们所求的至多两次交易的最大利润。
     *
     * 作者：xfzhao
     * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/solution/mai-gu-piao-de-zui-jia-shi-ji-iii-yi-kan-jiu-dong-/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     */

    static public int maxProfit(int[] prices){
        if (prices == null || prices.length == 0) return 0;
        int len = prices.length;
        int minPrice1 = prices[0];
        int maxProfit1 = 0;
        int maxPrice2 = prices[len-1];
        int maxProfit2 = 0;
        int[] profits1 = new int[len]; // 第一次卖出利润表
        int[] profits2 = new int[len]; // 第二次买入利润表

        for (int i = 0; i < len  ; i++) {
            // 第一次卖出时能获取的最大利润
            minPrice1 =  prices[i] < minPrice1 ? prices[i] : minPrice1;
            maxProfit1 = Math.max(maxProfit1, prices[i] - minPrice1);
            profits1[i] = maxProfit1;
        }

        for (int i = 0; i < len; i++) {
            System.out.print(profits1[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < len  ; i++) {
            // 第二次买入时能获取的最大利润
            maxPrice2 = prices[len-1-i] >= maxPrice2 ? prices[len-1-i]: maxPrice2;
            maxProfit2 = Math.max(maxProfit2,maxPrice2 - prices[len-1-i]);
            profits2[len-1-i] = maxProfit2;
        }
        for (int i = 0; i < len; i++) {
            System.out.print(profits2[i] + " ");
        }
        System.out.println();

        // 计算最大利润
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < len ; i++) {
            res = Math.max(res,profits1[i] + profits2[i]);
        }

        return res;
    }


    /**
     *  * 我的思路:
     *  * 每天股价                             [3,3,5, 0,0,3, 1,4]
     *  * 每天都卖出前一天的股票的收益           [0,0,2,-5,0,3,-2,3]
     *  * dp[i]记录第i天卖出时能获得的最大收益。 时间复杂度O(n²)
     */
    static public int maxProfit1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        // 每天都交易的利润数组
        int[] profitPerDay = getProfitPerDay(prices);
        int len = profitPerDay.length;
        int max = 0;
//        int origin;

        // 第二次买入股票时间点，作为分割时间点，分别记录前后的两次交易能获取的最大利润
        for (int sellDay = 0; sellDay < len; sellDay++) {
            // 没必要更改profitPerDay[sellDay] = 0的值，因为同一天可以卖出后再买入的
            // 注意:下次遍历前要将profitPerDay的原始值重新赋值回去
//            origin = profitPerDay[sellDay];
            // 注意此时需要修改利润数组的利润,sellDay这天的利润应该成为0；
//            profitPerDay[sellDay] = 0;

            int[] la = new int[sellDay];
            int[] ra = new int[len - sellDay];

            for (int i = 0; i < sellDay; i++) {
                la[i] = profitPerDay[i];
            }
            for (int i = 0; i < len - sellDay; i++) {
                ra[i] = profitPerDay[sellDay + i];
            }

            max = Math.max(getMaxProfit(la) + getMaxProfit(ra), max);
//            profitPerDay[sellDay] = origin;
        }
        return max;
    }

    static public int[] getProfitPerDay(int[] prices) {
        int len = prices.length;
        int[] profitPerDay = new int[len];
        for (int i = 1; i < len; i++) {
            profitPerDay[i] = prices[i] - prices[i - 1];
        }
        return profitPerDay;
    }

    static public int getMaxProfit(int[] profitPerDay) {
        if (profitPerDay == null || profitPerDay.length == 0) return 0;
        int len = profitPerDay.length;
        /**
         *  dp[i]记录第i卖出时能获取的最大利润。
         *  if dp[i-1] > 0 , dp[i] = dp[i-1] + profitPerDay[i]
         *  else dp[i] = profitPerDay[i]
         *  最终找到dp[]中的最大值就是第一次交易能获取的最大利润
         */
        int[] dp = new int[len];
        dp[0] = profitPerDay[0];
        int max = 0;
        for (int i = 1; i < len; i++) {
            dp[i] = dp[i - 1] > 0 ? dp[i - 1] + profitPerDay[i] : profitPerDay[i];
            max = Math.max(max, dp[i]);
        }
        return max;
    }


}
