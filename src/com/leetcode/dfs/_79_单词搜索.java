package com.leetcode.dfs;

public class _79_单词搜索 {
    public static void main(String[] args) {
        boolean exist = exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABAB");
        System.out.println(exist);
    }
    public static boolean exist(char[][] board, String word) {
        int row = board.length;
        int col = board[0].length;
        int[][] used = new int[board.length][board[0].length];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 查找第一个回溯位置
                if (word.charAt(0) == board[i][j]){
                     if (dfs(i,j,board,word,used,0)) return true;
                     // 找到一个解就返回true,如果这个位置的没有解,从下个匹配的位置找
                }
            }
        }
        // 如果所有的位置都没有解返回false
        return false;
    }
    private static boolean dfs(int row,int col,char[][] board, String word, int[][]used, int start){
        if (start == word.length()) return true;

        // 不合理路径
        if (    col == board[0].length ||
                row == board.length    ||
                col < 0                ||
                row < 0                ||
                used[row][col] == 1    ||
                board[row][col] != word.charAt(start)){
            return false;
        }
        // 来到此处说明为合理路径
        used[row][col] = 1;
        // 左下右上是否合理
        if (    dfs(row,col+1,board,word,used,start+1) ||
                dfs(row+1,col,board,word,used,start+1) ||
                dfs(row,col-1,board,word,used,start+1) ||
                dfs(row-1,col,board,word,used,start+1)) {
            return true;
        }
        // 四个方向都不能选择时 状态重置回溯
        used[row][col] = 0;
        return false;
    }
}


    /**
     * 给定一个二维网格和一个单词，找出该单词是否存在于网格中。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     * 示例:
     * board =
     * [
     *   ['A','B','C','E'],
     *   ['S','F','C','S'],
     *   ['A','D','E','E']
     * ]
     *
     * 给定 word = "ABCCED", 返回 true.
     * 给定 word = "SEE", 返回 true.
     * 给定 word = "ABCB", 返回 false.
     *
     *  题目理解问题: 每个网格指的是每个字母,单词必须在网格中连续
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/word-search
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
