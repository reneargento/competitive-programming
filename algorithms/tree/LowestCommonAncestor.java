package algorithms.tree;

import datastructures.tree.TreeNode;

/**
 * Created by Rene Argento on 23/05/19.
 */
public class LowestCommonAncestor {

    private static class Result {
        private TreeNode<Integer> node;
        private boolean found;

        Result(TreeNode<Integer> node, boolean found) {
            this.node = node;
            this.found = found;
        }
    }

    // O(n) runtime, where n is the number of nodes
    // O(n) space
    // 1- If both nodes are in the subtree: return ancestor
    // 2- If only one is in the subtree: return the node found
    // 3- If none is in the subtree: return null
    public static TreeNode<Integer> getFirstCommonAncestor(TreeNode<Integer> root, TreeNode<Integer> node1,
                                                           TreeNode<Integer> node2) {
        Result result = getFirstCommonAncestorHelper(root, node1, node2);
        if (result.found) {
            return result.node;
        }
        return null;
    }

    public static Result getFirstCommonAncestorHelper(TreeNode<Integer> root, TreeNode<Integer> node1,
                                                      TreeNode<Integer> node2) {
        if (root == null) {
            return new Result(null, false);
        }

        if (root == node1 && root == node2) {
            return new Result(root, true);
        }

        Result leftSubtree = getFirstCommonAncestorHelper(root.left, node1, node2);
        Result rightSubtree = getFirstCommonAncestorHelper(root.right, node1, node2);

        // Base cases
        if (root == node1 || root == node2) {
            if (leftSubtree.node != null || rightSubtree.node != null) {
                return new Result(root, true);
            } else {
                return new Result(root, false);
            }
        }

        // 1- If found both nodes
        if (leftSubtree.found) {
            return leftSubtree;
        }
        if (rightSubtree.found) {
            return rightSubtree;
        }
        if (leftSubtree.node != null && rightSubtree.node != null) {
            return new Result(root, true);
        }

        // 2- If found only one node
        if (leftSubtree.node != null) {
            return leftSubtree;
        }
        if (rightSubtree.node != null) {
            return rightSubtree;
        }

        // 3- If did not find any nodes
        return new Result(null, false);
    }

    //         5
    //     3      7
    //   2  4   6  -4
    // 1  8
    public static void main(String[] args) {
        TreeNode<Integer> node5 = new TreeNode<>(5);
        TreeNode<Integer> node3 = new TreeNode<>(3);
        TreeNode<Integer> node7 = new TreeNode<>(7);
        TreeNode<Integer> node2 = new TreeNode<>(2);
        TreeNode<Integer> node4 = new TreeNode<>(4);
        TreeNode<Integer> node1 = new TreeNode<>(1);
        TreeNode<Integer> node6 = new TreeNode<>(6);
        TreeNode<Integer> node8 = new TreeNode<>(8);
        TreeNode<Integer> nodeMinus4 = new TreeNode<>(-4);

        node5.left = node3;
        node5.right = node7;
        node3.left = node2;
        node3.right = node4;
        node2.left = node1;
        node2.right = node8;
        node7.left = node6;
        node7.right = nodeMinus4;

        TreeNode firstCommonAncestor1 = getFirstCommonAncestor(node5, node1, node8);
        System.out.println("First common ancestor 1 and 8: " +
                (firstCommonAncestor1 != null ? firstCommonAncestor1.value : null) + " Expected: 2");

        TreeNode firstCommonAncestor2 = getFirstCommonAncestor(node5, node1, node4);
        System.out.println("First common ancestor 1 and 4: " +
                (firstCommonAncestor2 != null ? firstCommonAncestor2.value : null) + " Expected: 3");

        TreeNode firstCommonAncestor3 = getFirstCommonAncestor(node5, node8, nodeMinus4);
        System.out.println("First common ancestor 8 and -4: " +
                (firstCommonAncestor3 != null ? firstCommonAncestor3.value : null) + " Expected: 5");

        TreeNode firstCommonAncestor4 = getFirstCommonAncestor(node5, node6, nodeMinus4);
        System.out.println("First common ancestor 6 and -4: " +
                (firstCommonAncestor4 != null ? firstCommonAncestor4.value : null) + " Expected: 7");

        TreeNode firstCommonAncestor5 = getFirstCommonAncestor(node5, node3, node4);
        System.out.println("First common ancestor 3 and 4: " +
                (firstCommonAncestor5 != null ? firstCommonAncestor5.value : null) + " Expected: 3");

        TreeNode firstCommonAncestor6 = getFirstCommonAncestor(node5, node6, node6);
        System.out.println("First common ancestor 6 and 6: " +
                (firstCommonAncestor6 != null ? firstCommonAncestor6.value : null) + " Expected: 6");

        TreeNode firstCommonAncestor7 = getFirstCommonAncestor(node5, node2, new TreeNode<>(10));
        System.out.println("First common ancestor 2 and 10: " +
                (firstCommonAncestor7 != null ? firstCommonAncestor7.value : null) + " Expected: null");
    }

}
