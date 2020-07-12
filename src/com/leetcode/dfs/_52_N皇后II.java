package com.leetcode.dfs;

/**
 * 52. N皇后 II
 *
 * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 *
 * 上图为 8 皇后问题的一种解法。
 *
 * 给定一个整数 n，返回 n 皇后不同的解决方案的数量。
 *
 * 示例:
 *
 * 输入: 4
 * 输出: 2
 * 解释: 4 皇后问题存在如下两个不同的解法。
 * [
 *  [".Q..",  // 解法 1
 *   "...Q",
 *   "Q...",
 *   "..Q."],
 *
 *  ["..Q.",  // 解法 2
 *   "Q...",
 *   "...Q",
 *   ".Q.."]
 * ]
 * 提示：
 *     皇后，是国际象棋中的棋子，意味着国王的妻子。皇后只做一件事，那就是“吃子”。
 *     当她遇见可以吃的棋子时，就迅速冲上去吃掉棋子。当然，她横、竖、斜都可走一或七步，可进可退。（引用自 百度百科 - 皇后 ）
 */

public class _52_N皇后II {
    public static int totalNQueens(int n) {
        int result =  0;
        // 初始化棋盘
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        /**
         * 直接的想法是暴力解法: 因为每行只能选一个n行有 n^n 中粗略的方案, 从中判断合理的情况。
         * 需要n层循环才能遍历覆盖所有的情况。所以想到递归，然后通过剪枝操作排除不符合条件的情况
         * 使用dfs 深度优先搜索
         * dfs(board,n){   // n代表递归深度
         *     dfs(board , n+1)
         * }
         */

        dfs(board, 0, n);
        System.out.println("方案数量: " + count);
        return result;
    }

    /**
     * 深度优先搜可以在棋盘board放置Q的位置
     * @param board 代表棋盘 . 代表不能放置皇后 Q 代表可以放置皇后
     * @param row 代表第row行数据, 本质为递归深度
     * @param n  棋盘的维度
     */
    static int count;
    public static void dfs(char[][] board, int row , int n){
        if (row == n){
            count++;
            /// 将结果打印出来
            printBorad(board);
        }
        int cols = board[0].length;
        for (int col = 0; col < cols; col++) {
            // 剪枝: 核心
            if (!isValid(row, col, board)) continue;
            // 第row的第i列放置皇后
            board[row][col] = 'Q';
            dfs(board, row+1 , n); // 递归
            board[row][col] = '.'; // 回溯
        }
    }

    /**
     * 判断该位置是否能够放置皇后
     * @return
     *
     *  .   Q   .   .   .   .
     *  .   .   .   .   .   .
     *  .   .   Q   .   .   .
     *  .   .   .   .   .   .
     *  .   .   .   .   .   .
     *  .   .   .   .   .   .
     */
    public static boolean isValid(int row, int col, char[][] board){
        /**
         * 根据规则,编写剪枝代码
         * 1.同一行的只能放置一个Q,该剪枝条件可以直接忽略,以为我们就是每次从一行中挑选一个位置,这行一开始所有位置都是可以放置的
         * 2.同一列只能有一个Q,该条件决定在row行的col能否放置Q。
         * 3.斜线上只能有一个Q,分为左斜线、右斜线
         * 因为从上向下放置, 所以条件2、3都只需要判断上方位置即可!
         *
         */
        /// 正上方: (row,col) 判断 该列 col 在 [0,row)范围是否放置过Q
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }

        /// 左斜线
        int i = row-1; int j = col-1;
        while (i>=0 && j>= 0){
            if (board[i][j] == 'Q') return false;
            i--;
            j--;
        }
        /// 右斜线
        int maxC = board[0].length;
        int m = row - 1; int n = col + 1;
        while (m >= 0 && n < maxC){
            if (board[m][n] == 'Q') return false;
            m--;
            n++;
        }

        return true;
    }

    public static void printBorad(char[][] board){
        int rows = board.length;
        int cols = board[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + "    ");
            }
            System.out.println();
        }
        System.out.println("----------------华丽的分割线----------------");
    }

    public static void main(String[] args) {
        System.out.println(totalNQueens(8));
    }

}
