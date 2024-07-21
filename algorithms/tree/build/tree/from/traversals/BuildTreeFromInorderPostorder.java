package algorithms.tree.build.tree.from.traversals;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 18/07/24.
 */
// Builds a binary tree given its in-order and post-order traversal.
// Assumes all nodes have different values.
// Time complexity: O(N)
// Based on https://www.geeksforgeeks.org/construct-a-binary-tree-from-postorder-and-inorder/
public class BuildTreeFromInorderPostorder {

    private static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    private static class PostorderIndex {
        int value;

        public PostorderIndex(int value) {
            this.value = value;
        }
    }

    private static Node buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> nodeToIndexInorderMap = new HashMap<>();
        for (int index = 0; index < inorder.length; index++) {
            nodeToIndexInorderMap.put(inorder[index], index);
        }
        PostorderIndex postorderIndex = new PostorderIndex(postorder.length - 1);
        return buildTree(postorder, nodeToIndexInorderMap, postorderIndex, 0, inorder.length - 1);
    }

    private static Node buildTree(int[] postorder, Map<Integer, Integer> nodeToIndexInorderMap,
                                  PostorderIndex postorderIndex, int inorderStartIndex, int inorderEndIndex) {
        if (inorderStartIndex > inorderEndIndex) {
            return null;
        }

        int currentValue = postorder[postorderIndex.value];
        postorderIndex.value--;
        Node node = new Node(currentValue);

        // If node has no children, return
        if (inorderStartIndex == inorderEndIndex) {
            return node;
        }

        int inorderIndex = nodeToIndexInorderMap.get(currentValue);
        node.right = buildTree(postorder, nodeToIndexInorderMap, postorderIndex, inorderIndex + 1, inorderEndIndex);
        node.left = buildTree(postorder, nodeToIndexInorderMap, postorderIndex, inorderStartIndex, inorderIndex - 1);
        return node;
    }

    public static void main(String[] args) {
        int[] inorder = { 4, 8, 2, 5, 1, 6, 3, 7 };
        int[] postorder = { 8, 4, 5, 2, 6, 7, 3, 1 };

        Node root = buildTree(inorder, postorder);

        System.out.print("Traversal: ");
        printPreorder(root);
        System.out.println("\nExpected:  1 2 4 8 5 3 6 7");
    }

    // Check if tree was built correctly
    private static void printPreorder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.value + " ");
        printPreorder(node.left);
        printPreorder(node.right);
    }
}
