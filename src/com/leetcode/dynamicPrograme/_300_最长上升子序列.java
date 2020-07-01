package com.leetcode.dynamicPrograme;

public class _300_最长上升子序列 {

    public static void main(String[] args) {

    }

    /**
     * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
     *
     * 示例:
     *
     * 输入: [10,9,2,5,3,7,101,18]
     * 输出: 4
     * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
     *
     * 说明:
     *
     *     可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
     *     你算法的时间复杂度应该为 O(n2) 。
     *
     * 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    public int lengthOfLIS1(int[] nums) {
        if (nums == null || nums.length ==0 ) return 0;
        /**
         * dp[i] 记录以nums[i]结尾的最长上升子序列的长度
         * 要上升避免不了需要比较数据
         * 当计算dp[i]时, 需要使用nums[i],与之前的nums[0..j](此处j<i)进行比较。
         * 如果nums[i]>nums[j] 那么 lens[] = dp[j] + 1;
         * 遍历所有的的dp[0..j] 与 len的最大值比较，较大者赋值给dp[i]
         * 如果nums[i]<=nums[j], 因为必须以nums[i] 结尾。所以dp[i]只能等于1了。
         */
        int len = nums.length;
        int maxLen = 1;
        int[] dp = new int[len];
        dp[0] = 1;
        for (int i = 1; i < len; i++) {
            dp[i] = 1;
            for (int j = i-1; j >= 0 ; j--) {
                if (nums[i] > nums[j]){
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
                maxLen = Math.max(maxLen,dp[i]);
            }
        }

        return maxLen;
    }




    /**
     * 解题思路：
     *     状态定义：
     *     dp[i] 的值代表 nums 前 i个数字的最长子序列长度。
     *     转移方程:设 j∈[0,i)，考虑每轮计算新 dp[i]时，遍历 [0,i)列表区间，做以下判断：
     *         1.当 nums[i]>nums[j]时: nums[i]可以接在 nums[j]之后（此题要求严格递增）,此情况下最长上升子序列长度为dp[j]+1
     *         2.当 nums[i]<=nums[j]时: nums[i]无法接在 nums[j]之后，此情况上升子序列不成立，跳过。
     *         上述所有 1.情况下计算出的 dp[j]+1的最大值，为直到i的最长上升子序列长度（即 dp[i] ）。
     *         实现方式为遍历 j时，每轮执行 dp[i]=max(dp[i],dp[j]+1)。
     *         转移方程: dp[i] = max(dp[i], dp[j] + 1) for j in [0, i)。
     *
     *     初始状态：
     *         dp[i] 所有元素置 1，含义是每个元素都至少可以单独成为子序列，此时长度都为 1。
     *     返回值：
     *         返回 dp列表最大值，即可得到全局最长上升子序列长度。
     *
     * 作者：jyd
     * 链接：https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-dong-tai-gui-hua-2/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public int lengthOfLIS(int[] nums) {
     /**
        状态： dp[i]记录以nums[i]结尾的数组中最长上升子序列的长度
        转移： 设j<i 遍历比较 nums[j] 与 nums[i]比较。如果nums[i]>nums[j]说明nums[i]可以接在nums[j]后面,
        形成一个更长的子序列长度为 dp[j]+1 所以dp[i] = max{dp[j]+1, dp[i]};
        初始： dp[0] = 1 dp[i] = 1
     */
        if(nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        int max = dp[0] = 1;
        for(int i = 1; i < nums.length; i++){
            dp[i] = 1; // 初始值一定为1 ,如果不写默认为0，就出错了
            for(int j = 0; j < i; j++){
                if(nums[i] > nums[j]){
                    dp[i]  =  Math.max(dp[j] + 1, dp[i]);
                }
            }
            max = Math.max(max,dp[i]);
        }
        return max;
    }

}
