package com.leetcode.priorityQueue;


import java.util.*;

/**
 * 347. 前 K 个高频元素
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 * 示例 1:
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 *
 * 示例 2:
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *
 * 提示：
 *
 *     你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 *     你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 *     题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
 *     你可以按任意顺序返回答案。
 */
public class _347前K个高频元素 {

    /**
     * 基于桶排序: O(n + k)
     * 1.首先依旧使用哈希表统计频率，统计完成后，创建一个数组，
     * 2.将频率作为数组下标，对于出现频率不同的数字集合，存入对应的数组下标即可。
     */
    public static int[] topKFrequent_bucketSort(int[] nums, int k) {

        /// 元素对应的频次
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length ; i++) {
            int key = nums[i];
            if (map.containsKey(key)){
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }

        /// 将频率作为数组下标，对于出现频率不同的数字集合，存入对应的数组下标即可。
        /// 桶的最大长度即数组长度，即每个元素出现的频次都是1 。
        // 长度为nums.length + 1是因为每个元素出现的次数至少为1。所以该数组的有效位置从1开始，而最大值为nums.length。
        // 综合以上两个条件,以频率作为数组下标，至少需要创建nums.length + 1大小的空间
        List<Integer>[] buckets = new List[nums.length + 1];

        for (Integer key : map.keySet()) {
            // 出现的频率作为下标
            int i = map.get(key);
            if (buckets[i] == null){
                buckets[i] = new ArrayList();
            }
            buckets[i].add(key);
        }

        List<Integer> tem = new ArrayList();

        for (int i = buckets.length - 1; i > 0  && tem.size() < k; i--) {
            if (buckets[i] == null) continue;
            tem.addAll(buckets[i]);
        }

        int[] res = new int[tem.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = tem.get(i);
        }

        return res;

    }


    /**
     *  方案一: 借用小顶堆 - JDK现有数据结构优先级队列PriorityQueue
     *  O(nlogn)
     *  1.将数组中的出现的元素及频次存放到 hashMap中，以元素为key,出现频率为value
     *  2.将元素按照频次的大小存放到大小为K的优先级队列中
     *  3.取出队列中的元素就是前K个频次最高的元素
     */
    public static int[] topKFrequent_heapSort(int[] nums, int k) {

        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length ; i++) {
            int key = nums[i];
            if (map.containsKey(key)){
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return map.get(o1) - map.get(o2);
            }
        });

        for (Integer key : map.keySet()) {
            if (queue.size() < k){
                queue.add(key);
            } else {
                if (map.get(key) > map.get(queue.peek())){
                    queue.poll();
                    queue.add(key);
                }
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = queue.poll();
        }

        return res;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1,1,1,2,2,3};

//        int[] res = topKFrequent_bucketSort(nums ,2);
        int[] res = topKFrequent_heapSort(nums ,2);

        System.out.println(Arrays.toString(res));

    }
}

/**
 * 看到的最佳解答
 */

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        // 利用哈希表记录数字出现的次数
        MyHashMap map = new MyHashMap(nums.length);
        for (int num : nums) {
            map.increase(num);
        }

        List<MyEntry> entries = map.entries;
        // 小顶堆：按数字出现的次数升序排序数字
        PriorityQueue<MyEntry> priorityQueue = new PriorityQueue<>(k, (n1, n2) -> n1.value - n2.value);
        for (int i = 0; i < k; i++) {
            priorityQueue.add(entries.get(i));
        }
        for (int i = k; i < entries.size(); i++) {
            MyEntry entry = entries.get(i);
            if (entry.value > priorityQueue.peek().value) {
                // 如果当前数字出现的次数比堆顶数字出现的次数多，将堆顶元素从堆中移除，当当前元素添加到堆中
                priorityQueue.poll();
                priorityQueue.add(entry);
            }
        }

        int[] ans = new int[k];
        Iterator<MyEntry> iterator = priorityQueue.iterator();
        for (int i = 0; i < k; i++) {
            ans[i] = iterator.next().key;
        }
        return ans;
    }
}

/**
 * 基于桶和二叉搜索树的HashMap
 */
class MyHashMap {

    public MyEntry[] buckets;
    public List<MyEntry> entries;

    public MyHashMap(int size) {
        buckets = new MyEntry[size < 17 ? 17 : size];
        entries = new ArrayList<>(size);
    }

    public void increase(int key) {
        int hash = key < 0 ? -key : key;// 使用key的绝对值作为哈希值
        int bucketIndex = hash % buckets.length;//哈希函数：哈希值 % 桶的数量
        MyEntry entry = buckets[bucketIndex];
        if (entry == null) {
            buckets[bucketIndex] = new MyEntry(key);
            entries.add(buckets[bucketIndex]);
            return;
        }

        MyEntry parent = null;
        while (entry != null) {
            if (entry.key == key) {
                // 指定的key已经在MyHashMap中了，直接将其值加一
                entry.value += 1;
                return;
            }

            parent = entry;
            entry = hash < entry.hash ? entry.left : entry.right;
        }

        if (hash < parent.hash) {
            parent.left = new MyEntry(key);
            entries.add(parent.left);
        } else {
            parent.right = new MyEntry(key);
            entries.add(parent.right);
        }
    }

}

class MyEntry {

    public int key;     // 数字
    public int value;   // 数字出现的次数
    public int hash;
    public MyEntry left;
    public MyEntry right;

    public MyEntry(int key) {
        this.key = key;
        this.hash = key < 0 ? -key : key;
        this.value = 1;
    }

}
