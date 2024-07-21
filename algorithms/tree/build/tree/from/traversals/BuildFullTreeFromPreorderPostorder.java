package algorithms.tree.build.tree.from.traversals;

/**
 * Created by Rene Argento on 18/07/24.
 */
// Builds a full binary tree given its pre-order and post-order traversal.
// Assumes all nodes have different values.
// Note: It is not possible to build a general binary tree from pre-order and post-order traversals.
// This method only works if we know that the tree is a full binary tree.
// Time complexity: O(N)
// Based on https://www.geeksforgeeks.org/full-and-complete-binary-tree-from-given-preorder-and-postorder-traversals/
public class BuildFullTreeFromPreorderPostorder {

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

    private static Node buildTree(int[] preorder, int[] postorder) {
        return buildTree(preorder, postorder, new PreorderIndex(), 0, postorder.length - 1);
    }

    private static Node buildTree(int[] preorder, int[] postorder, PreorderIndex preorderIndex,
                                  int lowPostorderIndex, int highPostorderIndex) {
        if (preorderIndex.value >= preorder.length || lowPostorderIndex > highPostorderIndex) {
            return null;
        }
        // The first node in pre-order traversal is the root
        Node root = new Node(preorder[preorderIndex.value]);
        preorderIndex.value++;

        if (lowPostorderIndex == highPostorderIndex
                || preorderIndex.value >= preorder.length) {
            return root;
        }

        int postorderIndex;
        // Search the next element of pre-order in post-order
        for (postorderIndex = lowPostorderIndex; postorderIndex <= highPostorderIndex; postorderIndex++) {
            if (postorder[postorderIndex] == preorder[preorderIndex.value]) {
                break;
            }
        }

        // Divide post-order array into two subtrees
        if (postorderIndex <= highPostorderIndex) {
            root.left = buildTree(preorder, postorder, preorderIndex, lowPostorderIndex, postorderIndex);
            root.right = buildTree(preorder, postorder, preorderIndex, postorderIndex + 1, highPostorderIndex - 1);
        }
        return root;
    }

    public static void main(String[] args) {
        int[] preorder = { 1, 2, 4, 8, 9, 5, 3, 6, 7 };
        int[] postorder = { 8, 9, 4, 5, 2, 6, 7, 3, 1 };

        Node root = buildTree(preorder, postorder);

        System.out.print("Traversal: ");
        printInorder(root);
        System.out.println("\nExpected:  8 4 9 2 5 1 6 3 7");
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
