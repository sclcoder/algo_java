package com.leetcode.dfs;
import java.util.ArrayList;
import java.util.List;

public class _51_N皇后 {
    /**
     * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     * 上图为 8 皇后问题的一种解法。
     * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
     * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     *
     * 示例:
     *
     * 输入: 4
     * 输出: [
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
     * 解释: 4 皇后问题存在两个不同的解法。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/n-queens
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    public static void main(String[] args) {
        System.out.println(solveNQueens(8));
    }

    static List<List<String>> queens = new ArrayList<>();
    static int num = 0;
    public static List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        dfs(board,0); // 从第0行的开始放置
        return null;
    }

    /**
     * 路径：board 中小于 row 的那些行都已经成功放置了皇后
     * 选择列表：第 row 行的所有列都是放置皇后的选择
     * 结束条件：row 超过 board 的最后一行
     * 作者：labuladong
     * 链接：https://leetcode-cn.com/problems/n-queens/solution/hui-su-suan-fa-xiang-jie-by-labuladong/
     * 来源：力扣（LeetCode）
     */
    private static void dfs(char[][] board, int row){
        if (row == board.length){
          queens.add(printBoard(board));
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    System.out.print(board[i][j] + "   ");
                }
                System.out.println();
            }
            System.out.println("-----------------------——-"+num++);
        } else {
            int n = board[row].length;
            for (int col = 0; col < n; col++) {
                if (isValid(board,row,col)){
                    board[row][col] = 'Q';
                } else {
                    continue;
                }
                dfs(board,row+1);
                board[row][col] = '.';
            }
        }
    }

    /**
     * 该位置是否可以放置Q
     * @return
     */
    private static boolean isValid(char[][] board,int row, int col){
        /**   上 、对角线(左上 右上)
         *    1   0   0   0
         *    0   0   1   0
         *    0   0   0   0
         *    0   0   0   0
         */
        int n = board.length;
        // 上
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q')
                return false;
        }
        // 对角线-左上
        for (int i = row - 1, j = col -1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q')
                return false;
        }

        // 对角线-右上
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q')
                return false;
        }

        return true;
    }
    private static List<String> printBoard(char[][] board){
        List<String> printBoard = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            String rowStr = "";
            for (int j = 0; j < board[i].length; j++) {
                rowStr = rowStr + board[i][j];
            }
            printBoard.add(rowStr);
        }
        return printBoard;
    }
}
