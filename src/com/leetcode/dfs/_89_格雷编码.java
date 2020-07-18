package com.leetcode.dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * 89. 格雷编码
 *
 * 格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个位数的差异。
 *
 * 给定一个代表编码总位数的非负整数 n，打印其格雷编码序列。即使有多个不同答案，你也只需要返回其中一种。
 *
 * 格雷编码序列必须以 0 开头。
 *
 *
 *
 * 示例 1:
 *
 * 输入: 2
 * 输出: [0,1,3,2]
 * 解释:
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 *
 * 对于给定的 n，其格雷编码序列并不唯一。
 * 例如，[0,2,3,1] 也是一个有效的格雷编码序列。
 *
 * 00 - 0
 * 10 - 2
 * 11 - 3
 * 01 - 1
 *
 * 示例 2:
 *
 * 输入: 0
 * 输出: [0]
 * 解释: 我们定义格雷编码序列必须以 0 开头。
 *      给定编码总位数为 n 的格雷编码序列，其长度为 2n。当 n = 0 时，长度为 20 = 1。
 *      因此，当 n = 0 时，其格雷编码序列为 [0]。
 *
 *
 *              n = 3
 *               0 1
 *
 *          0            1
 *       0    1        0   1
 *     0  1  0 1     0 1  0 1
 * 000 001 010 011 100 101 110 111
 *
 * 这个结果是错误的 : 因为 001 和 010 有两位的不同
 * 需要保证每次选择时 都和上一次只有1位不同， 即每次只替换一个字符， 以上的推演过程中 001 和 010 就有两位不同
 *
 * 重新推演          n=2
 *                 0 1
 *
 *            0               1
 *         0    1          1    0
 *       0  1  1  0      0  1  1  0
 *    000 001 011 010   110 111 101 100
 *   看在当前节点时，1个数的奇偶性，偶数个1就先放0后放1，奇数个1就先放1后放0
 *
 *   规律就是 每层都是按照 01 10的规律添加一下元素
 *
 *
 *             0               1
 *          1    0          0     1
 *        1  0  0  1     1  0   0   1
 *       011 010 000 001 101 100 110 111
 *   规律就是 每层都是按照 10 01的规律添加一下元素
 *
 */
public class _89_格雷编码 {

    static List<Integer> res1 = new ArrayList<>();
    static public List<Integer> grayCode(int n) {
        if (n == 0){
            res1.add(Integer.valueOf(0));
            return res1;
        }
        dfs1(n,0,"");
        return res1;
    }
    static public void dfs1(int n, int count, String s){
        if (n == 0) {
            System.out.println(s);
            res1.add(Integer.valueOf(s,2));
            return;
        }

        if (count % 2 == 0){
            /**
             * 这么写自然带有回溯功能。 因为同一层的s是同一个值 , 分情况添加 0 1 不会拼接
             * s在传递过程中 每次的s的内容都会被重置，即指向的地址每次都变更
             * 效率更低一些,因为每次递归都是相当于在创建一个新的对象
             *
             *
             * 这和使用StringBuilder不同，在使用StringBuilder时 sb这个对象的指向一直没变，每次都是在操作sb这个对象
             * 相比于使用String，sb效率高一些，因为每次递归仅仅在操作sb指向的对象而已，并未像String方案那样每次创建新对象
             *
             *
             */
            dfs1(n-1, count, s+0);
            dfs1(n-1, count + 1, s+1);
        } else {
            dfs1(n-1, count + 1, s+1);
            dfs1(n-1, count, s+0);
        }
    }











    static List<Integer> res = new ArrayList<>();
    static public List<Integer> grayCode_fist(int n) {
        if (n == 0){
            res.add(Integer.valueOf(0));
            return res;
        }
        StringBuilder sb = new StringBuilder();
        dfs(n,0,sb);
        return res;
    }

    /**
     *
     * @param n
     * @param count 该分支上1的数量， 作为选择01顺序的判断条件
     * @param sb
     */
    static public void dfs(int n,int count, StringBuilder sb){
     if (n == 0){
         System.out.println(sb.toString());
         res.add(Integer.valueOf(sb.toString(), 2));
         return;
     }
        /**
         * 每层都是按照 01 10的规律添加一下元素
         *
         * 同一层的 append 顺序保证为 0 1 1 0
         * 同一个分支上怎么拼接？妈的这道题我看的头疼
         * 规律是这样.
         *
         * https://leetcode-cn.com/problems/gray-code/solution/jian-dan-de-si-lu-44ms-by-dannnn/
         * 不看了，我头疼。。。。。。。
         *
         *              0               1
         *           0    1          1    0
         *         0  1  1  0      0  1  1  0
         *      000 001 011 010   110 111 101 100
         *
         * 我终于找到了一个我能记住的规律了。。。。fuck
         * 看在当前节点时，1个数的奇偶性，偶数个1就先放0后放1，奇数个1就先放1后放0
         */

        /**
         * 该分支上1的总数
         *   偶数个1 添加顺序为0、1
         *   奇数个1 添加顺序为1、0
         */
        if (count % 2 == 0){
            sb.append(0);
            dfs(n-1,count,sb);
            sb.deleteCharAt(sb.length()-1);

            sb.append(1);
            dfs(n-1,count+1,sb);
            sb.deleteCharAt(sb.length()-1);

        } else {
            sb.append(1);
            dfs(n-1,count+1,sb);
            sb.deleteCharAt(sb.length()-1);

            sb.append(0);
            dfs(n-1,count,sb);
            sb.deleteCharAt(sb.length()-1);
        }

    }

    public static void main(String[] args) {
        System.out.println(grayCode(2));
    }
}

