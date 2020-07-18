package com.leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * 面试题 08.12. 八皇后
 *
 * 设计一种算法，打印 N 皇后在 N × N 棋盘上的各种摆法，其中每个皇后都不同行、不同列，也不在对角线上。这里的“对角线”指的是所有的对角线，不只是平分整个棋盘的那两条对角线。
 *
 * 注意：本题相对原题做了扩展
 *
 * 示例:
 *
 *  输入：4
 *  输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 *  解释: 4 皇后问题存在如下两个不同的解法。
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
 */
public class _面试题0812八皇后 {

    static List<List<String>> res = new ArrayList<>();
    static int count = 0;
    static public List<List<String>> solveNQueens(int n) {
        /// 初始化棋盘
        char[][] board = new char[n][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                board[r][c] = '.';
            }
        }

        dfs(board, 0 , n);
        System.out.println(count);
        return res;
    }

    static public void dfs(char[][] board, int row , int n){
        if (row == n){
            /// 找到一个解
            count++;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                /// 该API会直接将board[i]这行转为字符串
                list.add(String.valueOf(board[i]));
            }
            res.add(list);
            return;
        }

        /// 每一行所面临的的选择
        for (int col = 0; col < board[0].length; col++) {
            if (isValid(board, row, col)){ // 可选位置
                board[row][col] = 'Q';
                dfs(board, row + 1, n);
                board[row][col] = '.';
            } else {
                continue;
            }
        }
    }

    static public boolean isValid(char[][] board, int row, int col){

        /// 正上方 即同一列的检查
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }
        /// 左斜方
        int i = row;
        int j = col;
        while (i >= 0 && j >= 0){
            if (board[i][j] == 'Q') return false;
            i--;
            j--;
        }
        /// 右斜方
        int m = row;
        int n = col;
        while (m >= 0 && n < board[0].length){
            if (board[m][n] == 'Q') return false;
            m--;
            n++;
        }
        return true;
    }

    static public void printBoard(char[][] board){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println(); // 换行
        }
        System.out.println("------华丽的分割线-------");
    }

    static public void printBoard2(String[] board){
        for (int i = 0; i < board.length; i++) {
            System.out.println(board[i]);
        }
    }

    public static void main(String[] args) {
        System.out.println( solveNQueens(8));
    }
}
