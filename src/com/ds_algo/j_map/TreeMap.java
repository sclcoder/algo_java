package com.ds_algo.j_map;

import java.util.*;

/**
 * 使用红黑树实现的Map
 * @param <K> key泛型
 * @param <V> value泛型
 */
@SuppressWarnings({"unused","unchecked","UnusedReturnValue"})
public class TreeMap<K,V> implements Map<K,V>{
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private Node<K,V> root;
    private int size;
    private final Comparator<K> comparator;

    public TreeMap(){
        this(null);
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    /*
     * 重写接口方法
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        keyNotNullCheck(key);

        if (root == null){
            root = createNode(key,value,null);
            afterPut(root);
            size++;
            return null;
        } else {
            Node<K,V> parent = root;
            Node<K,V> cur = root;
            int res = 0;
            while (cur != null){
                parent = cur;
                res = compare(key,parent.key);
                if (res < 0){
                    cur = cur.left;
                } else if (res > 0){
                    cur = cur.right;
                } else {
                    V oldValue = cur.value;
                    cur.key = key;
                    cur.value = value;
                    return oldValue; // 退出循环,不然死循环
                }
            }
            Node<K,V> node = createNode(key,value, parent);
            if (res < 0){ // 添加到左节点
                parent.left = node;
            } else { // 添加到右节点
                parent.right = node;
            }
            afterPut(node);
            size++;
            return null;
        }
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node<K,V> node = queue.poll();

            if (valEquals(value, node.value)) return true;

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        traversal(root,visitor);
    }


    /*
     * 核心函数
     */
    /**
     * 中序遍历递归版
     * @param root 根节点
     * @param visitor 遍历器
     */
    private void traversal(Node<K,V> root, Visitor<K,V> visitor){
        if (root == null || visitor.stop) return;
        traversal(root.left,visitor);
        if (visitor.stop) return;
        visitor.stop = visitor.visit(root.key,root.value);
        traversal(root.right,visitor);
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
    private V remove(Node<K,V> node){
        if (node == null) return null;
        size--;
        V oldValue = node.value;
        // 度为2的节点
        if (node.hasTwoChildren()){
            // 找到后继节点
            Node<K,V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<K,V> replacement = node.left != null ? node.left : node.right;
        if (replacement == null){ // node是度为0的节点
            if (node.parent == null){// node是叶子节点并且是根节点
                root = null;
                // 当被删除的节点的所有指向都处理了在处理后序操作
                // 此时的node还保留着parent指针
                afterRemove(node, null);
            } else {// node是叶子节点，但不是根节点
                if (node.parent.left == node){
                    node.parent.left = null;
                } else { // node == node.parent.right
                    node.parent.right = null;
                }
                // 当被删除的节点的所有指向都处理了在处理后序操作
                // 此时的node还保留着parent指针
                afterRemove(node, null);
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
            afterRemove(node,replacement);
        }
        return oldValue;
    }


    /**
     * 右旋
     * @param grandNode 要旋转的node
     *
     *                  简略版对g进行右旋后示意图 具体看ppt
     *              g                   p
     *             /                 /    \
     *            p      ------->   n      g
     *           /
     *          n
     *
     *     需要更改的指针
     *     g.left = p.right
     *     p.right = g;
     *     等等。。。
     */
    protected void rotateRight(Node<K,V> grandNode){
        Node<K,V> parentNode = grandNode.left;
        Node<K,V> childNode = parentNode.right;

        /// 更改left、right指针
        grandNode.left = childNode;
        parentNode.right = grandNode;

        afterRotate(grandNode,parentNode,childNode);
    }

    /**
     * 左旋
     * @param grandNode 要旋转的node
     */
    protected void rotateLeft(Node<K,V> grandNode){
        Node<K,V> parentNode = grandNode.right;
        Node<K,V> childNode = parentNode.left;

        // 更改 left、right指针
        grandNode.right = parentNode.left;
        parentNode.left = grandNode;

        afterRotate(grandNode,parentNode,childNode);
    }

    protected void afterRotate(Node<K,V> grandNode , Node<K,V> parentNode , Node<K,V> childNode){
        if (grandNode.isRightChild()){
            grandNode.parent.right = parentNode;
        } else if (grandNode.isLeftChild()){
            grandNode.parent.left = parentNode;
        } else {
            root = parentNode;
        }

        // 更改parent指针
        if (childNode != null){
            childNode.parent = grandNode;
        }
        parentNode.parent = grandNode.parent;
        grandNode.parent = parentNode;
    }

    private void afterPut(Node<K,V> node) {
        /*
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

        Node<K,V> parent = node.parent;

        // 添加的节点为根节点或上溢到根节点
        if (parent == null){
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 如果父节点是红色

        // 叔父节点
        Node<K,V> uncle = parent.sibling();
        Node<K,V> grand = parent.parent;
        if (isRed(uncle)){ // 叔父节点为Red【B树节点上溢】

            black(parent);
            black(uncle);
            // 将grand染Red并上溢
            afterPut(red(grand));

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
    private void afterRemove(Node<K,V> node, Node<K,V> replacement) {
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
        Node<K,V> parent = node.parent;
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
        Node<K,V> sibling = isLeft ? parent.right : parent.left;
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
                black(sibling.right);
                black(parent);
            }
        }
    }



    /**
     * 查找前驱节点
     * 前驱节点: 中序遍历时的前一个节点
     * 步骤: 考虑到中序遍历的特点就可以思考出来 easy!!!
     * 情况1:     有左子树 node.left != null
     * 查找过程:  根据中序遍历的特点可知，其前驱肯定是左子树中的最后一个节点. 左子树的最后一个节点就是其最右的节点
     *           predecessor = node.left.right.right...
     *           终止条件: right = null;
     *
     * 情况2:    没有左子树 node.left == null && node.parent != null;
     * 查找过程:  没有左子树,那就不能向左子树寻找,由于是寻找前驱节点,所以肯定也不能寻找右子树.所以只能向上寻找即查找父节点。
     *           如果node在父节点的左子树中,那是父节点是该node后驱节点.只有当node在的父节点右子树中时,才能说明找到了前驱。否则没有前驱。
     *           predecessor = node.parent.parent...
     *           终止条件 node在parent的右子树中 或 parent == null(说明没有前驱)
     * 情况3:     node.left == null && node.parent == null;
     * 查找过程:  属于情况2中的一种中间情况 即没有前驱节点
     *
     * 查找node节点的前驱节点
     * @param node 目标节点
     * @return node的前驱节点
     */
    protected Node<K,V> predecessor(Node<K,V> node) {
        if (node == null) return null;
        // 前驱节点在左子树当中（left.right.right.right....）
        Node<K,V> p = node.left;
        if (p != null){
            while (p.right != null){
                p = p.right;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null &&  node.parent.left == node){
            node = node.parent;
        }

        // node.parent == null
        // node == node.parent.right
        return node.parent;
    }


    /**
     * 查找后驱节点
     * 后驱节点: 中序遍历时的后一个节点
     * 步骤：比较难想出来
     * 情况1:     node.right != null
     * 查找过程:  根据中序遍历的特点可知，其后驱肯定是右子树中的第一个节点
     *           predecessor = node.right.left.left...
     *           终止条件: left = null;
     *
     * 情况2:    node.right == null && node.parent != null;
     * 查找过程:  没有右子树,那就不能向右子树寻找,由于是寻找后驱节点,所以肯定也不能寻找左子树.所以只能向上寻找即查找父节点。
     *           如果node在父节点右子树中,那是该节点的前驱节点.只有找到的node在父节点的左子树中,才能说明找到了后驱。否则没有后驱。
     *           predecessor = node.parent.parent...
     *           终止条件 node在parent的左子树中 或 parent == null(说明没有前驱)
     * 情况3:     node.right == null && node.parent == null;
     * 查找过程:  属于情况2中的一种中间情况 即没有后驱节点
     *
     * 查找后继节点
     * @param node 目标节点
     * @return node的后继节点
     */
    protected Node<K,V> successor(Node<K,V> node) {
        if (node == null) return null;
        // 前驱节点在左子树当中（right.left.left.left....）
        Node<K,V> p = node.right;
        if (p != null){
            while (p.left != null){
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node.parent.right == node){
            node = node.parent;
        }
        // node.parent == null
        // node == node.parent.right
        return node.parent;
    }

    /*
     * 抽取的小函数
     */

    private Node<K,V> node(K key){
        Node<K, V> node = root;
        while (node != null) {
            int cmp = compare(key, node.key);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else { // cmp < 0
                node = node.left;
            }
        }
        return null;
    }

    private int compare(K e1, K e2){
        if (comparator != null){
            /// 优先使用比较器
            return comparator.compare(e1,e2);
        } else {
            /// 传入的对象必须是可比较的，这里强转
            return ((Comparable<K>) e1).compareTo(e2);
        }
    }

    private Node<K,V> createNode(K key,V value,Node<K,V>  parent) {
        return new Node<>(key,value,parent);
    }

    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }

    private boolean valEquals(V v1, V v2) {
//        return v1 == null ? v2 == null : v1.equals(v2);
        return Objects.equals(v1, v2);
    }




    /*
     * 辅助函数
     */
    private boolean isRed(Node<K,V> node){
        return colorOfNode(node) == RED;
    }

    private boolean isBlack(Node<K,V> node){
        return colorOfNode(node) == BLACK;
    }

    private boolean colorOfNode(Node<K,V> node){
        return node == null ? BLACK : (node).color;
    }

    private Node<K,V> black(Node<K,V> node){
          return color(node,BLACK);
    }
    private Node<K,V> red(Node<K,V> node){
        return color(node, RED);
    }

    private Node<K,V> color(Node<K,V> node, boolean color){
        if (node != null) (node).color = color;
        return node;
    }


    /**
     * 内部节点类
     * @param <K>
     * @param <V>
     */
    private static class Node<K,V>  {
        // 新创建节点默认为红色,这样可以满足红黑树的第 1、2、3、5性质 性质4不一定满足
        boolean color = RED;
        K key;
        V value;
        public Node<K,V> parent;
        public Node<K,V> left;
        public Node<K,V> right;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf(){
            return this.left == null && this.right == null;
        }
        public boolean hasTwoChildren(){
            return this.left != null && this.right != null;
        }
        public boolean isLeftChild(){
            if (parent != null){
                return parent.left == this;
            }
            return false;
        }

        public boolean isRightChild(){
            if (parent != null){
                return parent.right == this;
            }
            return false;
        }

        // 兄弟节点
        public Node<K,V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }


//        @Override
//        public String toString() {
//            String str = "";
//            if (color == RED) {
//                str = "R_";
//            }
//            return str + val.toString();
//        }
    }
}
