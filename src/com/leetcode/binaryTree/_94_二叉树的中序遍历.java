package com.leetcode.binaryTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/** https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 * 给定一个二叉树，返回它的中序 遍历。
 *
 * 示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [1,3,2]
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 */
public class _94_二叉树的中序遍历 {

    /**
     * 递归解法
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root,res);
        return res;
    }
    public void  helper(TreeNode node, List<Integer> list){
        if (node != null){
            if (node.left != null){
                helper(node.left, list);
            }
            list.add(node.val);
            if (node.right != null){
                helper(node.right, list);
            }
        }
        return;
    }


    /**
     * 非递归解法
     * 需要自己画图辅助分析才行
     * 其实和递归的调用是同一个过程，只不过中间需要自己建栈保存数据
     * 思考过程可以考虑着递归的中序遍历过程
     *
     *            3
     *         2     4
     *      5   6  7   8
     *
     *   5  2  6  3  7  4 8
     */

    public List<Integer> inorderTraversal_stack(TreeNode root){
        if (root == null) return null;
        List<Integer> res = new ArrayList<Integer>();
        TreeNode cur = root;
        Stack<TreeNode> stack = new Stack<>();
        while (cur != null  || !stack.isEmpty()){
            if (cur != null){ // cur != null 时，不断的入栈
                stack.add(cur);
                cur = cur.left;
            } else { // 说明此时 cur == null 但!stack.isEmpty(), 此时就应该出栈了
                cur = stack.pop();
                res.add(cur.val);
                cur = cur.right;
            }
        }
        return res;
    }


    public List<Integer> inorderTraversal_stack_2(TreeNode root){
        if (root == null) return null;
        List<Integer> res = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (!stack.isEmpty() || cur != null){
            while (cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            res.add(cur.val);
            cur = cur.right;  // 拿到一个节点就要将其left节点一直入栈
        }
        return res;
    }

    public List<Integer> inorderTraversal_stack_3(TreeNode root){
        if (root == null) return null;
        List<Integer> res = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (true){
            if (cur != null){
                stack.push(cur);
                cur = cur.left;
            } else if (!stack.isEmpty()){
                cur = stack.pop();
                res.add(cur.val);
                cur = cur.right;
            } else {
                break;
            }
        }
        return res;
    }



    }
