package com.ds_algo.h_RBTree;

import com.ds_algo.f_BST.BBST;
import java.util.Comparator;

/**
 * 平衡搜索二叉树之红黑树
 * @param <E> 元素
 * 红黑树必须满足一下5条性质:
 *           1.节点都是Red 或 Black
 *           2.根节点时Black
 *           3.叶子节点(null节点为叶子节点，该节点那是为了满足红黑树的性质假象出来的)都是Black : 注意空节点即null都认为是Black，而且空节点也算路径的一部分
 *           4.Red节点的子节点都是Black
 *             a.Red节点的parent是Black
 *             b.从根节点到叶子节点的所有路径上，不能有两个连续的Red节点
 *           5.从任意节点到叶子节点的所有路径都包含相同个数的Black节点
 *
 *   红黑树的这5条性质可以和4阶B树可以完美对应:
 *           黑色节点个数与一个B树的节点个数相同
 *           黑色节点和他的红色节点融合成一个B树节点
 *
 *   注意的性质：
 *           添加 ：新添加的元素必定添加到叶子节点。
 *           删除 ：真正被删除的节点都在叶子节点。
 */
public class RBTree<E> extends BBST<E> {
    public static final boolean RED = false;
    public static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }


    // 红黑树旋转后不需要做额外操作，这与AVL树不同，AVL树旋转后要更新节点的高度
    @Override
    protected void afterRotate(Node<E> grandNode, Node<E> parentNode, Node<E> childNode) {
        super.afterRotate(grandNode, parentNode, childNode);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        /**
         * 4阶B树和红黑树可以完美对应
         * 添加红黑树节点一共有12中情况
         * 一、有4中情况满足红黑树的性质4 :  parent为Black
         *     a.同时满足4阶B树 b.不需要做额外处理
         *
         * 二、有8中情况不满足红黑树性质4 ： parent为Red 即出现连续的Red节点
         *   1. 有4中情况属于B树不会上溢 ，判定条件 uncle 不是 Red
         *     具体又分为 LL、RR 、RL、LR
         *
         *     LL、RR 修复红黑树性质4需要做的操作:
         *          a. parent 染为 Black , grand 染为 Red
         *          b. grand进行单次旋转 LL时右旋 RR时左旋
         *
         *     RL、LR 修复红黑树性质4需要做的操作:
         *          a. 将自己染Black, grand染 Red
         *          b. 进行双旋操作
         *                  LR : parent左旋 , grand右旋
         *                  RL : parent右旋 , grand左旋
         *
         *   2. 有4中情况属于B树上溢 ，判定条件 uncle 是 Red
         *      具体的4中情况为 LL、RR、RL、LR
         *      LL、RR、RL、LR修复红黑树性质4需要做的操作
         *          a. 将 parent、uncle染为Black
         *          b. grand向上合并: 将grand染Red，当做新添加的节点处理（递归操作）
         *
         *   经过以上步骤红黑树的添加操作结束
         */

        Node<E> parent = node.parent;

        // 添加的节点为根节点或上溢到根节点
        if (parent == null){
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 如果父节点是红色

        // 叔父节点
        Node<E> uncle = parent.sibling();
        Node<E> grand = parent.parent;
        if (isRed(uncle)){ // 叔父节点为Red【B树节点上溢】

            black(parent);
            black(uncle);
            // 将grand染Red并上溢
            afterAdd(red(grand));

        } else { // 叔父节点不是Red
            if (parent.isLeftChild()){ // L
                if (node.isLeftChild()){ // LL
                    red(grand);
                    black(parent);
                    // grand右旋
                    rotateRight(grand);
                } else { // LR
                    black(node);
                    red(grand);
                    rotateLeft(parent);
                    rotateRight(grand);
                }
            } else { // R
                if (node.isLeftChild()){ // RL
                    black(node);
                    red(grand);
                    rotateRight(parent);
                    rotateLeft(grand);
                } else { // RR
                    red(grand);
                    black(parent);
                    // grand左旋
                    rotateLeft(grand);
                }
            }
        }

    }

    /**
     * 删除节点后的调整
     * @param node 删除的节点
     * @param replacement 删除的节点的替代节点
     *
     *  情况分类
     *  一、 如果删除的是Red, 直接删除即可
     *  二、 删除的Black, 又细分为3中情况
     *      1. 有一个两个Red子节点的Black节点:
     *             不可能被直接删除，因为在BST的删除方法中，如果删除度为2的节点一定会找到前驱或后继来代替其删除
     *             这种情况不用考虑
     *      2. 有一个Red子节点的Black节点：Red 左 或 右
     *            判定这种情况的条件为: 用于替代的子节点为Red
     *            恢复操作：
     *            将替代的子节点染为Black
     *
     *      3. Black为叶子节点,发生下溢 (注意此时叶子节点指的为普通的叶子节点，并非null节点)
     *         这种是最为复杂的情况具体分析如下:
     *
     *         删除该Black节点，会导致B树节点下溢
     *             # sibling为Black的情景: 此时要删除的Black节点和sibling在B树中都是独立节点
     *               ## sibling至少有一个Red节点 : 此时需要向sibling处的B树节点借一个节点
     *                  ### Red节点在sibling左边
     *                  ### Red节点在sibling右边
     *                  ### Red节点在sibling左右两边
     *
     *              恢复操作:
     *                  1.进行旋转操作： 具体旋转的情景根据 Red节点的分布进行
     *                  2.旋转之后的节点, 继承parent节点的颜色; 左右两边的节点染为Black(成为独立节点)
     *
     *              ## sibling没有Red节点: 此时不能从sibling节点借，那只能parent下溢（向下合并）
     *              恢复操作:
     *                  1.将sibling染Red, parent染Black : 这是向下合并的操作
     *                  2.如果parent本来就是Black:
     *                      因为这是在sibling为Black的情景，其删除的也是Black, 所以如果此时parent是Black。
     *                      那么说明parent是一个单独的B树节点，其向下合并后，parent.parent也需要继续向下合并
     *                    此时将需要将parent当做被删除的节点处理恢复操作即可: 代码中使用递归实现
     *
     *            # sibling为Red的情景:  sibling为Red那么其左右子节点必定都是Black--这是红黑树性质决定的
     *               此时将红黑树当做B树考虑: sibling为Red时，在B树中，其和parent节点合并作为B树的大节点，而B树中借节点只能从同一层的兄弟节点借。此时应该借的节点为sibling的子节点。
     *               那只要将sibling的子节点变为要删除节点的sibling即可，这样又回到sibling为Black的情况
     *               恢复操作：
     *                  1. 将sibling染Black, parent染Red,进行旋转(具体怎样旋转根据情况来，旋转后要将sibling变为Black)
     *                  2. 又回到sibling为Black的情况
     *
     *  所有情况分析完毕
     *
     */
    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        // 删除 ：真正被删除的节点都在叶子节点(B树的节点)

        // 一、如果删除的是Red, 直接删除即可
        if (isRed(node)) return;

        // 二、删除的Black, 分支较多

        // 1.有一个Red节点的Black,判断条件为: replacement为Red
        if (isRed(replacement)){
            // 将replacement染黑即可
            black(replacement);
            return;
        }

        // 2.删除的是黑色叶子节点
        // # 如果叶子节点又是根节点
        Node<E> parent = node.parent;
        if (parent == null){
//            root = null;
            return;
        }
        // # 黑色叶子节点不是根节点【下溢】
        // ## sibling为Black :
            // 注意此时获取sibling节点的方式 通过node.sibling()是获取不到的，因为parent的指向node的指针已经断开。sibling()内部的isLeftChild()、isRightChild()失效
            // 首先判断node是在左边还是右边: 因为该节点node在BST中已经和parent的left or right指针断开，所以可以通过其left或right是否为null来判断
            // 可能有不断下溢的情况: 此时处理的node节点的left或right指针没断开所以通过node.isLeftChild()进行判断
        boolean isLeft = parent.left == null || node.isLeftChild();
        Node<E> sibling = isLeft ? parent.right : parent.left;
        if (!isLeft){ // node在右边,兄弟节点在左边

            // 这里先处理sibling为Red的情况，因为该情况可以转为sibling为Black的情况
            if (isRed(sibling)){ // sibling是Red
                black(sibling);
                red(parent);
                // 此时sibling在左侧, 对parent进行右旋即可做到node的兄弟节点由sibling改为sibling.right(必然为Black)
                rotateRight(parent);
                // 更换兄弟： 变为sibling是Black的情况
                sibling = parent.left;
            }
            // 来到此处: sibling必然是Black
            if (isBlack(sibling.right) && isBlack(sibling.left)){ // sibling没有子节点(因为null认为是Black,且B树删除只发生在子节点中)
                // sibling没有1个红色子节点，parent要向下跟sibling节点合并,合并操作就是染色~
                // 如果parent本来是Black,其向下合并后,空位也会引发下溢操作: 此时当做新删除的节点处理
                boolean parentBlack = isBlack(parent);
                black(parent); // 向下与sibling合并: parent染Black,sibling染Red
                red(sibling);
                if (parentBlack){
                    afterRemove(parent, null); // parent没有replacement
                }
            } else { // sibling节点至少有1个Red子节点，向sibling节点借元素
                if (isBlack(sibling.left)) { // 因为null被判定为Black,所以这是sibling.right为Red的情况,又因为sibling在右边
                    // 先将sibling左旋: 转换为统一右旋parent的情况
                    rotateLeft(sibling);
                    sibling = parent.left; // 更换sibling
                }
                // 统一处理统一右旋parent的情况
                rotateRight(parent);
                // 中心节点继承parent颜色
                color(sibling, colorOfNode(parent));
                // 左右节点染成Black
                black(sibling.left);
                black(parent);
            }

        } else { // 和上边相反处理即可

            // node在左边,兄弟节点在右边
            // 这里先处理sibling为Red的情况，因为该情况可以转为sibling为Black的情况
            if (isRed(sibling)){ // sibling是Red
                black(sibling);
                red(parent);
                // 此时sibling在右侧, 对parent进行左旋即可做到node的兄弟节点由sibling改为sibling.left(必然为Black)
                rotateLeft(parent);
                // 更换兄弟： 变为sibling是Black的情况
                sibling = parent.right;
            }
            // 来到此处: sibling必然是Black
            if (isBlack(sibling.right) && isBlack(sibling.left)){ // sibling没有子节点(因为null认为是Black,且B树删除只发生在子节点中)
                // sibling没有1个红色子节点，parent要向下跟sibling节点合并,合并操作就是染色~
                // 如果parent本来是Black,其向下合并后,空位也会引发下溢操作: 此时当做新删除的节点处理
                boolean parentBlack = isBlack(parent);
                black(parent); // 向下与sibling合并: parent染Black,sibling染Red
                red(sibling);
                if (parentBlack){
                    afterRemove(parent, null); // parent没有replacement
                }
            } else { // sibling节点至少有1个Red子节点，向sibling节点借元素
                if (isBlack(sibling.right)) { // 因为null被判定为Black,所以这是sibling.left为Red的情况,又因为sibling在左边
                    // 先将sibling右旋: 转换为统一左旋parent的情况
                    rotateRight(sibling);
                    sibling = parent.right; // 更换sibling
                }
                // 统一处理统一左旋parent的情况
                rotateLeft(parent);
                // 中心节点继承parent颜色
                color(sibling, colorOfNode(parent));
                // 左右节点染成Black
                black(sibling.left);
                black(parent);
            }
        }

    }

    @Override
    protected Node<E> createNode(Node<E> parent, E element) {
        return new RBNode<>(parent, element);
    }

    private boolean isRed(Node<E> node){
        return colorOfNode(node) == RED;
    }

    private boolean isBlack(Node<E> node){
        return colorOfNode(node) == BLACK;
    }

    private boolean colorOfNode(Node<E> node){
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    private RBNode<E> color(Node<E> node, boolean color){
        if (node != null) ((RBNode<E>) node).color = color;
        return (RBNode<E>) node;
    }

    private RBNode<E> black(Node<E> node){
        return color(node,BLACK);
    }
    private RBNode<E> red(Node<E> node){
        return color(node, RED);
    }

    public static  class RBNode<E> extends Node<E>{
        // 新创建节点默认为红色,这样可以满足红黑树的第 1、2、3、5性质 性质4不一定满足
        boolean color = RED;
        public RBNode(Node<E> parent, E val) {
            super(parent, val);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + val.toString();
        }
    }
}
