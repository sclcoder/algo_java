package com.ds_algo.f_BST;

import java.util.Comparator;

/**
 * 真二叉树：所有节点的度要么是0 要么是2
 * 满二叉树：所有节点的度要么是0 要么是2 且叶子节点在最后一层
 * 完全二叉树：叶子节点最后二层 且最后一层的叶子节点左对齐
 */

/**
 *  * 二叉搜索树:
 *  任意节点的值都大于(或小于)其左子树所有节点的值
 *  任意节点的值都小于(或大于)其右子树所有节点的值
 *  左右子树也是一个搜索二叉树
 */
@SuppressWarnings({"unused","unchecked"})
public class BST<E> extends BinaryTree<E> {

    private final Comparator<E> comparator;

    public BST(Comparator<E> comparator) {
        /**
         * 二叉搜索树: 需要比较大小。这里使用比较器这种方式比使用遵守Comparable接口更加灵活。
         * 1、如果使用 <E extends Comparable> 这种方式, 那么BST存储一个元素对象时,就只能是从小到大或从大到小
         * 因为在对象实现Comparable接口时,比较方式已经写死了。
         * 2、如果使用比较器, 可以创建两个比较器，一个从小到大，一个从大到小就不会存在方案一的弊端
         */
        this.comparator = comparator;
    }

    public BST() {
        this(null);
    }


    /**
     * 添加node之后的调整
     * @param node 新添加的节点
     */
    protected void afterAdd(Node<E> node) { }
    /**
     * 删除node之后的调整
     * @param node 删除的节点
     */
    protected void afterRemove(Node<E> node) { }

    /**
     * 添加新值
     * @param element 值
     */
    public void add(E element){
        elementEmptyCheck(element);
        if (root == null){
            root = createNode(null,element);
            afterAdd(root);
        } else {
            Node<E> parent = root;
            Node<E> cur = root;
            int res = 0;
            while (cur != null){
                parent = cur;
                res = compare(element,parent.val);
               if (res < 0){
                   cur = cur.left;
               } else if (res > 0){
                   cur = cur.right;
               } else {
                   cur.val = element;
                   return; // 退出循环,不然死循环
               }
            }
            Node<E> node = createNode(parent, element);
            if (res < 0){ // 添加到左节点
                parent.left = node;
            } else { // 添加到右节点
                parent.right = node;
            }
            afterAdd(node);
        }
        size++;
    }

    /**
     * 删除元素
     * @param element 元素值
     */
    public void remove(E element){
        remove(node(element));
    }

    /**
     * 删除节点
     * @param node 节点
     *
     *        7
     *    3      11
     *  1   4   9
     *  情况分类:
     *  1. 删除度为0的节点(叶子节点)
     *  2. 删除只有度为1的节点
     *  3. 删除有度为2的节点
     */
    private void remove(Node<E> node){
        if (node == null) return;
        size--;

        // 度为2的节点
        if (node.hasTwoChildren()){
            // 找到后继节点
            Node<E> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.val = s.val;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement == null){ // node是度为0的节点
            if (node.parent == null){// node是叶子节点并且是根节点
                root = null;
                // 当被删除的节点的所有指向都处理了在处理后序操作
                // 此时的node还保留着parent指针
                afterRemove(node);
            } else {// node是叶子节点，但不是根节点
                if (node.parent.left == node){
                    node.parent.left = null;
                } else { // node == node.parent.right
                    node.parent.right = null;
                }
                // 当被删除的节点的所有指向都处理了在处理后序操作
                // 此时的node还保留着parent指针
                afterRemove(node);
            }
        } else { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null){ // node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }
            // 当被删除的节点的所有指向都处理了在处理后序操作
            // 此时的node还保留着parent指针
            afterRemove(node);
        }

    }



    /**
     * 是否包含元素
     * @param element 元素
     * @return 是否包含元素
     */
    public boolean contains(E element) {
        return node(element) != null;
    }
    /**
     * 根据元素找到节点
     * @param element 元素值
     * @return 节点
     */
    public Node<E> node(E element){
        elementEmptyCheck(element);
        Node<E> cur = root;
        while (cur != null){
            int res = compare(element, cur.val);
            if (res < 0){
                cur = cur.left;
            }
            if (res > 0){
                cur = cur.right;
            }
            if (res == 0) return cur;
        }
        return null;
    }

    private int compare(E e1, E e2){
        if (comparator != null){
            /// 优先使用比较器
            return comparator.compare(e1,e2);
        } else {
            /// 传入的对象必须是可比较的，这里强转
            return ((Comparable<E>) e1).compareTo(e2);
        }
    }

}
