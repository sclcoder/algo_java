package com.leetcode.dfs;

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
 *
 *
 * 此问题写不对，写不出来，很大部分原因是因为递归运用的不到位！！！！！！！！！
 *
 *
 */
public class _79_单词搜索 {
    public static void main(String[] args) {
        boolean exist = exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "CCBA");
        System.out.println(exist);
    }

    static boolean flag = false;
    public static boolean exist(char[][] board, String word) {
        char[] words = word.toCharArray();
        int rows = board.length;
        int cols = board[0].length;
        int[][] used = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (words[0] == board[i][j]){
                    boolean has = dfs(board,words,i,j,used,0);
                    if (has == false){
                        continue;
                    } else {
                        return true; // 找到一个就行
                    }

                }
            }
        }
        return false;
    }

    public static boolean dfs(char[][] board , char[] words, int row , int col, int[][] used, int start){
        /**
         *  dfs的最本质是遍历所有的情况。那么此题应该怎样遍历呢？其实在推演的过程中已经使用过怎样遍历了
         *  就是通过(row,col)对其进行上下左右式的遍历，而不是经典排列组合时的一个方向的遍历，现在是四个方向
         *  因为每次都能选择周围的元素, 所以存在这样的情景: 选择了A后又选择右边的B,那么选择B后的下一次选择时可能会再次选到A.
         *  这时就需要搞一个used[][]数组，记录已经选择的元素。
         *   使用start记录递归深度,每次递归都会选择一个元素。
         */
        if (start == words.length) {
            /// 找到一个符合条件的，使用flag作为标记，这样在dfs时可以避免继续寻找其他合适的值。即剪枝节省时间
            flag = true;
            return true;
        }

        if (    col < 0 ||
                row < 0 ||
                col == board[0].length ||
                row == board.length ||
                words[start] != board[row][col] ||
                used[row][col] == 1
        ) return false;

        if (flag == false){ /// 还没有找到一个合适的值就dfs继续搜索
            /**
             * 来到此处说明是未越界的情况下得到一个合适的值即,该值和word中的字母匹配
             */
            used[row][col] = 1; /// 记录一下
            /// 深度优先搜索 上下左右
            boolean top = dfs(board, words, row+1, col, used, start+1);
            boolean down = dfs(board, words, row-1, col, used, start+1);
            boolean left = dfs(board, words, row, col+1, used, start+1);
            boolean right = dfs(board, words, row, col-1, used, start+1);
            used[row][col] = 0; /// 状态重置
            if (top || down || left || right) return true; /// 有一个和符合的就可以返回ture
            return false;
        } else { /// 已经找到一个符合的值,那么其他的dfs直接返回true即可
            return true;
        }
    }










    /**
     * 判断该位置是否是有效位置
     * @param row  当前行
     * @param col  当前列
     * @param board 字母表
     * @return
     */
    public static boolean isValidPosition(int row, int col, char[][] board){
        if(row == board.length|| col == board[0].length || row < 0 || col < 0) return false;
        return true;
    }

    public static boolean exist_wrong(char[][] board, String word) {
        char[] words = word.toCharArray();
        int rows = board.length;
        int cols = board[0].length;
        int[][] used = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (words[0] == board[i][j]){
                    if (dfs(board,words,i,j, used , 0)) return true;
                }
            }
        }

        return false;
    }

    public static boolean dfs_wrong(char[][] board , char[] words, int row , int col, int[][] used, int start){
        /// dfs的最本质是遍历所有的情况。那么此题应该怎样遍历呢？其实在推演的过程中已经使用过怎样遍历了
        /// 就是通过(row,col)对其进行上下左右式的遍历，而不是经典排列组合时的一个方向的遍历，现在是四个方向
        /// 因为每次都能选择周围的元素, 所以存在这样的情景: 选择了A后又选择右边的B,那么选择B后的下一次选择时可能会再次选到A.
        /// 这时就需要搞一个used[][]数组，记录已经选择的元素。
        /// 使用start记录递归深度,每次递归都会选择一个元素。
        if (start == words.length){ /// 找到单词
            return true;
        }
        /**
         *   自己写了半天，结果错的离谱： 为什么会错成这样子，说明理解的还不够透彻。这个错误解答留在此处，反思吧
         *   本质是递归的理解不够透彻。
         *
         */
        if (words[start] != board[row][col]) return false;

        /// 上下左右遍历
        if (col < board[0].length - 1) { /// 右

            /**
             *  错误的原因: 此处因为每次不论下一个字母是否匹配都记录为used
             *  如果下一个不匹配,记录为used,在回溯时会将其重置为未使用这没问题
             *  如果下一个匹配,记录为used,在回溯阶段就不应该重置了。。。
             */
            used[row][col + 1] = 1;
            dfs_wrong(board, words, row, col + 1, used, start + 1);
            used[row][col + 1] = 0;
        }
        if (col > 0) { /// 左
            used[row][col - 1] = 1;
            dfs_wrong(board, words, row, col - 1, used, start + 1);
            used[row][col - 1] = 0;
        }
        if (row < board.length - 1) { /// 下
            used[row + 1][col] = 1;
            dfs_wrong(board, words, row + 1, col, used, start + 1);
            used[row + 1][col] = 0;
        }
        if (row > 0) { /// 上
            used[row - 1][col] = 1;
            dfs_wrong(board, words, row - 1, col, used, start + 1);
            used[row - 1][col] = 0;
        }

        return false;
    }



    public static boolean exist_old(char[][] board, String word) {
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
            /**
             * 我就是不明白这里为什么要返回 true;  不返回不行吗？这题我感觉比八皇后难多了。。。。。。
             * 我要练习这样的题目，我要搞定他们，妈的
             */
            return true;
        }
        // 四个方向都不能选择时 状态重置回溯
        used[row][col] = 0;
        return false;
    }
}

