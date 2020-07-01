package com.leetcode.dynamicPrograme;
/**
 *
 * 在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？
 * 示例 1:
 *
 * 输入:
 * [
 *   [1,3,1],
 *   [1,5,1],
 *   [4,2,1]
 * ]
 * 输出: 12
 * 解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
 *
 * 提示：
 *
 *     0 < grid.length <= 200
 *     0 < grid[0].length <= 200
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *    礼物数组
 *    [1,3,1],
 *    [1,5,1],
 *    [4,2,1]
 *
 *    dp[row][col]记录走到[row][col]位置时的最大价值
 *    dp  0   1   2
 *    0   1   4   5
 *    1   2   9   10
 *    2   6   11  12
 *    dp[row][col] = max( dp[row-1][col]  , dp[row][col-1] ) +  grid[col][row]
 *
 */
 public class _47_礼物最大价值 {

    public static void main(String[] args) {
        int[][] grid = {
                                {1,2,3},
                                {4,5,6},
                                {7,8,9}
                        };
        System.out.println( maxValue(grid) );
        System.out.println( maxValue2(grid) );

    }

    public static int maxValue(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        dp[0][0] = grid[0][0];
        // 第0行
        for (int col = 1; col < cols; col++) {
            dp[0][col] = dp[0][col-1] + grid[0][col];
        }
        // 第0列
        for (int row = 1; row < rows; row++) {
            dp[row][0] = dp[row-1][0] + grid[row][0];
        }

        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                dp[row][col] = Math.max(dp[row-1][col], dp[row][col-1]) + grid[row][col];
            }
        }

        return dp[rows-1][cols-1];
    }


    public static int maxValue2(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        /// 优化代码。微调dp数组的定义。 dp[i][j] 记录走到i-1,j-1时获得最大价值，最终求dp[rows][cols]
        int[][] dp = new int[rows + 1][cols + 1];
        dp[0][0] = 0;
        for (int row = 1; row <= rows; row++) {
            dp[row][0] = 0;
            for (int col = 1; col <= cols; col++) {
                dp[0][col] = 0;
                dp[row][col] = Math.max(dp[row-1][col], dp[row][col-1]) + grid[row-1][col-1];
            }
        }
        return dp[rows][cols];
    }
}
