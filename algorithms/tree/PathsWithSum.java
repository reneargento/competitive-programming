package algorithms.tree;

import datastructures.tree.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 08/06/19.
 */
// Counts the number of paths with the target sum in the tree.
// Paths may start anywhere and end anywhere, but must go from parent nodes to child nodes.
public class PathsWithSum {

    // O(n) runtime, where n is the number of nodes in the tree
    // O(n) space
    public static int countPaths(TreeNode<Integer> root, int targetSum) {
        return countPaths(root, targetSum, 0, new HashMap<>());
    }

    private static int countPaths(TreeNode<Integer> node, int targetSum, int currentSum, Map<Integer, Integer> pathCount) {
        if (node == null) {
            return 0;
        }

        currentSum += node.value;
        int sumToGetTarget = currentSum - targetSum;

        int paths = pathCount.getOrDefault(sumToGetTarget, 0);

        // Special case - there is a path from root to this node with sum equal to targetSum
        if (currentSum == targetSum) {
            paths++;
        }

        incrementHashTable(pathCount, currentSum, 1);
        paths += countPaths(node.left, targetSum, currentSum, pathCount);
        paths += countPaths(node.right, targetSum, currentSum, pathCount);
        incrementHashTable(pathCount, currentSum, -1);

        return paths;
    }

    private static void incrementHashTable(Map<Integer, Integer> hashTable, int key, int delta) {
        int count = hashTable.getOrDefault(key, 0);
        count += delta;

        if (count == 0) {
            hashTable.remove(key);
        } else {
            hashTable.put(key, count);
        }
    }

    // Tree
    //          5
    //     3        7
    //   2  4    6    10
    // 1            8   18
    public static void main(String[] args) {
        TreeNode<Integer> treeNode5 = new TreeNode<>(5);
        TreeNode<Integer> treeNode3 = new TreeNode<>(3);
        TreeNode<Integer> treeNode7 = new TreeNode<>(7);
        TreeNode<Integer> treeNode2 = new TreeNode<>(2);
        TreeNode<Integer> treeNode4 = new TreeNode<>(4);
        TreeNode<Integer> treeNode6 = new TreeNode<>(6);
        TreeNode<Integer> treeNode1 = new TreeNode<>(1);
        TreeNode<Integer> treeNode10 = new TreeNode<>(10);
        TreeNode<Integer> treeNode8 = new TreeNode<>(8);
        TreeNode<Integer> treeNode18 = new TreeNode<>(18);

        treeNode5.left = treeNode3;
        treeNode5.right = treeNode7;
        treeNode3.left = treeNode2;
        treeNode3.right = treeNode4;
        treeNode2.left = treeNode1;
        treeNode7.left = treeNode6;
        treeNode7.right = treeNode10;
        treeNode10.left = treeNode8;
        treeNode10.right = treeNode18;

        int pathsCount1 = countPaths(treeNode5, 5);
        System.out.println("Paths with sum 5: " + pathsCount1 + " Expected: 2");

        int pathsCount2 = countPaths(treeNode5, 18);
        System.out.println("Paths with sum 18: " + pathsCount2 + " Expected: 3");

        int pathsCount3 = countPaths(treeNode5, 7);
        System.out.println("Paths with sum 7: " + pathsCount3 + " Expected: 2");
    }

}
