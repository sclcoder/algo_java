package com.leetcode.binaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 再也不用担心各种诡计了。。。
 * https://leetcode-cn.com/problems/binary-tree-postorder-traversal/solution/mo-fang-di-gui-zhi-bian-yi-xing-by-sonp/
 */
public class _00_二叉树前中后序遍历之非递归通用解法 {

    public List<Integer> preorderTraversal_common_preorder(TreeNode root){
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) stack.push(root);
        TreeNode top = null;
        while (!stack.isEmpty()){
            top = stack.pop();
            if (top != null){
                if (top.right != null) stack.push(top.right);
                if (top.left != null) stack.push(top.left);
                stack.push(top);
                stack.push(null); //  一次递归结束,标记一下
            } else {
                top = stack.pop(); // 递归回来后，彻底出栈
                res.add(top.val);
            }
        }
        return res;
    }




    public List<Integer> preorderTraversal(TreeNode root){

        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()){
            TreeNode cur = stack.pop();
            if (cur != null){
                if (cur.right != null) stack.push(cur.right);
                if (cur.left != null) stack.push(cur.left);
                stack.push(cur);
                stack.push(null); //
            } else {
                res.add(stack.pop().val);
            }
        }

        return res;
    }

    public List<Integer> inorderTraversal(TreeNode root){

        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()){
            TreeNode cur = stack.peek(); // 记录栈顶
            stack.pop(); // 弹出栈顶
            if (cur != null){
                if (cur.right != null) stack.push(cur.right);
                stack.push(cur);
                stack.push(null); //
                if (cur.left != null) stack.push(cur.left);
            } else {
                res.add(stack.peek().val);
                stack.pop();
            }
        }

        return res;
    }

    /**
     * '''
     * 后序遍历模拟递归写法:
     * 每个结点会进栈出栈两次, 即[进栈->出栈->进栈(随后入栈一None结点)->出栈(取结点值)].
     * 其中的(随后入栈一None结点)是一个标志, 用于模拟自底向上的递归return时的操作,
     * 即, 每当在出栈中遇到的结点是None的时候, 标志着要打印该结点的值(这里, 因为入栈时的顺序是根None右左, 所以每次打印的都先是这棵子树的左结点->右结点->根)
     * 例如对于二叉树[1,3,2], 后序遍历模拟算法工作流程如下:
     * stack = [1] ->
     * 	    [1(出栈后再次进栈), None, 2, 3] ->
     * 	    [1, None, 2, 3(出栈后再次进栈), (3没有左右孩子, 故不进栈), None] ->
     * 	    [1, None, 2, 3](None出栈) -> [1, None, 2](栈顶3再出栈并伴随打印3操作) ->
     * 	    [1, None, 2(出栈后再次进栈), (2没有左右孩子, 故不进栈), None] ->
     * 	    [1, None, 2](None出栈) -> [1, None](栈顶2再出栈并伴随打印2操作) ->
     * 	    [1](None出栈) -> [](栈顶1再出栈并伴随打印1操作) -> 栈空, 返回后序遍历打印结果[3,2,1]
     * '''
     */
    public List<Integer> postorderTraversal(TreeNode root){

        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()){
            TreeNode cur = stack.peek(); // 记录栈顶
            stack.pop(); // 弹出栈顶
            if (cur != null){
                stack.push(cur);
                stack.push(null);
                if (cur.right != null) stack.push(cur.right);
                if (cur.left != null) stack.push(cur.left);
            } else {
                res.add(stack.peek().val);
                stack.pop();
            }
        }

        return res;
    }

}
