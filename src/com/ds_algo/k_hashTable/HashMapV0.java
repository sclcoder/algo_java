package com.ds_algo.k_hashTable;

import com.ds_algo.j_map.Map;
import com.tool.binaryTree.printer.BinaryTreeInfo;
import com.tool.binaryTree.printer.BinaryTrees;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;


/**
 * 版本0: 不能扩容
 * 哈希表实现Map:
 * 1. 哈希算法的实现
 * 2. 每个桶存放的为红黑树节点
 * 3. 注意 对顺序没有要求
 */
@SuppressWarnings({"unchecked","unused"})
public class HashMapV0<K,V> implements Map<K,V>{
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private final Node<K,V>[] table;
    public HashMapV0(){
        // 容量设计为2^n 方便使用&与key的hashCode进行约束
        int DEFAULT_CAPACITY = 1 << 4;
        table = new Node[DEFAULT_CAPACITY];
    }

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
        if (size == 0) return;
        Arrays.fill(table, null);
        size = 0;
    }

    /* ———————————————两个查找位置的核心函数—————————————————————— */

    /* 注意: 判断两个key是否相等的唯一标准equals,其他的相等都不能作为两个key相等的充分条件 */
    @Override
    public V put(K key, V value) {
        int index = index(key);
        // 取出index位置的红黑树根节点
        Node<K,V> root = table[index];
        if (root == null){
            root = createNode(key,value,null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        } else {
            Node<K,V> parent = root;
            Node<K,V> cur = root;
            // 需要通过key来进行查找
            K k1 = key;
            /// 之前简单以为在同一个桶中hash值就一样了。大错特错: hash值不一样，其index可能一样，最终在同一个桶中
//            int h1 = cur.hash  // 之前这里出错了。。。找了好久的bug。。。草
            int h1 = k1 == null ? 0 : k1.hashCode();
            // 比较结果
            int res = 0;
            // 记录扫描时的结果
            Node<K,V> scanResult;
            // 标记递过程中是否已经搜索过-避免大量的重复搜索，优化搜索过程
            boolean searched = false;
            while (cur != null){
                parent = cur;
                K k2 = cur.key;
                int h2 = cur.hash;
                // 注意: 这里不使用h1-h2是为了防止int类型溢出造成错误结果。
                if (h1 < h2){
                    res = -1; // 标记为 向右查找 cur = cur.left;
                } else if (h1 > h2){
                    res = 1;  // 标记为 向左查找 cur = cur.right;
                } else if (Objects.equals(k1,k2)){
                    /* 判断两个key是否相等的唯一标准equals,其他的相等都能算两个key相等 */
                    // key的哈希值相等且equals。覆盖旧值
                    res = 0; // 标记为 找到 : 覆盖旧值
                } else if (k1 != null && k2!= null &&
                           k1.getClass() == k2.getClass() &&
                           k1 instanceof Comparable &&
                           (res = ((Comparable) k1).compareTo(k2)) != 0){ // 具备可比较性
                     // 注意：compare的相等不能作为判断key相等的标准，标准只有equals这一个
                     // res不为0有前进方向,do nothing
                } else if (!searched){ // 没有搜索过
                    // key的hash相等、不equals、不具备可比较性
                    // 扫描来查看是否已经存在这个key
                    if (cur.left != null && (scanResult = node(cur.left, k1)) != null ||
                        cur.right != null && (scanResult = node(cur.right, k1)) != null){
                        // 已经存在这个key(k1)
                        cur = scanResult; // 为了后序覆盖scanResult这个值
                        res = 0;
                    } else { // 说明在cur以后的节点没有这k1
                        // 说明已经将所有子树扫描过了，下次递归时没必要进行再次扫描
                        searched = true;
                        // 那就可以放心的使用内存地址的比较来确定添加Node的方向
                        // 这里相减： 因为内存地址都是正数肯定不会溢出的放心减
                        res = System.identityHashCode(k1) - System.identityHashCode(k2);
                    }
                } else {
                    // 之前的扫描说明没有相等的key,可以直接使用内存地址的大小决定前进方向
                    res = System.identityHashCode(k1) - System.identityHashCode(k2);
                }

                if (res < 0){
                    cur = cur.left;
                } else if (res > 0){
                    cur = cur.right;
                } else {
                    V oldValue = cur.value;
                    cur.key = key;
                    cur.value = value;
                    cur.hash = h1;
                    return oldValue; // 退出循环,不然死循环
                }
            }
            Node<K,V> node = createNode(key,value, parent);
            if (res < 0){ // 添加到左节点
                parent.left = node;
            } else { // 添加到右节点
                parent.right = node;
            }
            size++;
            afterPut(node);
            return null;
        }
    }


    /**
     * 在红黑树中递归查找key1对应的节点
     * @param node 查找的起始节点
     * @param k1 给定的k1
     * @return 找到的节点
     */
    private Node<K,V> node(Node<K,V> node, K k1){
        // k1的hash值
        int h1 = k1 == null ? 0 : k1.hashCode();

        Node<K, V> result; // 存储查找结果

        int cmp;
        while (node != null) {
            K k2 = node.key;
            // k2的hash值
            int h2 = node.hash;
            // 先比较哈希值
            if (h1 > h2) {
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) {
                // 两个key是否相等的唯一标准equals
                return node;
            } else if (k1 != null && k2 != null
                    && k1.getClass() == k2.getClass()
                    && k1 instanceof Comparable
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                // 两个key的hash值相等，但两个key不相等的情况: 那么先通过是否可Comparable来处理后向左和还是向右找的问题
                // cmp相等的情况: 没法通过比较决定搜索方向了，只能扫描了
                node = cmp > 0 ? node.right : node.left;
            } else if (node.right != null &&  (result = node(node.right, k1)) != null) {
                // 如果又不能Comparable: 那就没办法了，只能暴力扫描了，这里使用递归处理
                return result;
            } else if (node.left != null && (result = node(node.left, k1)) != null) {
                return result;
			} else {
				return null;
			}
        }
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
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
        if (size == 0) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) return true;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;

        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) return;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }


    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index = " + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object string(Object node) {
                    return node;
                }

                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>)node).right;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>)node).left;
                }
            });
            System.out.println("---------------------------------------------------");
        }
    }


    /*-------------- 生成哈希表的索引函数 --------------*/

    /**
     * 根据key生成对应的索引（在桶数组中的位置）
     * @param key key
     * @return 该key生成的索引值
     */
    private int index(K key){
        if (key == null) return 0;
        int hashCode = key.hashCode();
        return  (hashCode ^ (hashCode >>> 16)) & (table.length-1);
    }

    /**
     * 根据node生成对应的索引（在桶数组中的位置）
     * @param node node
     * @return 该node生成的索引值
     */
    private int index(Node<K, V> node) {
        // 为了避免多次计算node.key的hash值,将其hash值记录在node节点中
        // 并不确定外界计算的hash值给定的比较合理，这里进行扰动计算一下
        return ((node.hash) ^ (node.hash >>> 16)) & (table.length-1);
    }


    /*-------------------- 查找节点的核心函数  ------------------*/
    /**
     * 根据key获取对应的Node
     * @param key key
     * @return Node
     */
    private Node<K,V> node(K key){
        int index = index(key);
        Node<K, V> root = table[index]; // 红黑树的节点
        return  root == null ? null : node(root, key);
    }


    /*---------------------红黑树相关核心函数-------------------------------*/

    /*
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
            node.hash = s.hash;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<K,V> replacement = node.left != null ? node.left : node.right;
        // 获取node在table中的index
        int index = index(node.key);
        if (replacement == null){ // node是度为0的节点
            if (node.parent == null){// node是叶子节点并且是根节点
                table[index] = null;
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
                table[index] = replacement;
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
    private void afterPut(Node<K,V> node) {

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

    /*
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


    /*
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

    private void rotateLeft(Node<K,V> grandNode){
        Node<K,V> parentNode = grandNode.right;
        Node<K,V> childNode = parentNode.left;

        // 更改 left、right指针
        grandNode.right = parentNode.left;
        parentNode.left = grandNode;

        afterRotate(grandNode,parentNode,childNode);
    }

    private void afterRotate(Node<K,V> grandNode , Node<K,V> parentNode , Node<K,V> childNode){
        if (grandNode.isRightChild()){
            grandNode.parent.right = parentNode;
        } else if (grandNode.isLeftChild()){
            grandNode.parent.left = parentNode;
        } else {
            // grandNode是root节点
            table[index(grandNode)] = parentNode;
        }

        // 更改parent指针
        if (childNode != null){
            childNode.parent = grandNode;
        }
        parentNode.parent = grandNode.parent;
        grandNode.parent = parentNode;
    }

    /*
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
    private Node<K,V> predecessor(Node<K,V> node) {

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


    /*
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
    private Node<K,V> successor(Node<K,V> node) {
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



    /* ---------------------- 辅助函数 ----------------------*/

    private Node<K,V> createNode(K key, V value, Node<K,V> parent) {
        return new Node<>(key,value,parent);
    }

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
        int hash; // 该节点key的hash值
        K key;
        V value;
        public Node<K,V> parent;
        public Node<K,V> left;
        public Node<K,V> right;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.hash = key == null ? 0 : key.hashCode();
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

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }
}
