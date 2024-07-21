package algorithms.tree.build.tree.from.traversals;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 18/07/24.
 */
// Builds a binary tree given its in-order and pre-order traversal.
// Assumes all nodes have different values.
// Time complexity: O(N)
// Based on https://www.geeksforgeeks.org/construct-tree-from-given-inorder-and-preorder-traversal/
public class BuildTreeFromInorderPreorder {

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static class PreorderIndex {
        int value;
    }

    private static Node buildTree(int[] inorder, int[] preorder) {
        Map<Integer, Integer> nodeToIndexInorderMap = new HashMap<>();
        for (int index = 0; index < inorder.length; index++) {
            nodeToIndexInorderMap.put(inorder[index], index);
        }
        return buildTree(preorder, nodeToIndexInorderMap, new PreorderIndex(), 0, inorder.length - 1);
    }

    private static Node buildTree(int[] preorder, Map<Integer, Integer> nodeToIndexInorderMap,
                                  PreorderIndex preorderIndex, int inorderStartIndex, int inorderEndIndex) {
        if (inorderStartIndex > inorderEndIndex) {
            return null;
        }

        int currentValue = preorder[preorderIndex.value];
        preorderIndex.value++;
        Node node = new Node(currentValue);

        // If node has no children, return
        if (inorderStartIndex == inorderEndIndex) {
            return node;
        }

        int inorderIndex = nodeToIndexInorderMap.get(currentValue);
        node.left = buildTree(preorder, nodeToIndexInorderMap, preorderIndex, inorderStartIndex, inorderIndex - 1);
        node.right = buildTree(preorder, nodeToIndexInorderMap, preorderIndex, inorderIndex + 1, inorderEndIndex);
        return node;
    }

    public static void main(String[] args) {
        int[] inorder = { 3, 1, 4, 0, 5, 2 };
        int[] preorder = { 0, 1, 3, 4, 2, 5 };

        Node root = buildTree(inorder, preorder);

        System.out.print("Traversal: ");
        printInorder(root);
        System.out.println("\nExpected:  3 1 4 0 5 2");
    }

    // Check if tree was built correctly
    private static void printInorder(Node node) {
        if (node == null) {
            return;
        }
        printInorder(node.left);
        System.out.print(node.value + " ");
        printInorder(node.right);
    }
}
