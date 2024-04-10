package algorithms.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 09/04/24.
 */
// Given the preorder and inorder traversal of a tree, output the nodes in postorder.
// Time complexity: O(N)
// Based on https://www.geeksforgeeks.org/print-postorder-from-given-inorder-and-preorder-traversals/
public class PostorderFromPreorderInorder {

    public static List<Integer> computePostorder(int[] preorder, int[] inorder) {
        List<Integer> postorder = new ArrayList<>();
        computePostorder(preorder, inorder, postorder, 0, 0, preorder.length);
        return postorder;
    }

    private static void computePostorder(int[] preorder, int[] inorder, List<Integer> postorder,
                                         int preorderStartIndex, int inorderStartIndex, int endIndex) {
        int firstNode = preorder[preorderStartIndex];
        int firstNodeIndex = findNodeIndex(inorder, inorderStartIndex, endIndex, firstNode);
        int leftSubtreeSize = firstNodeIndex - inorderStartIndex;

        if (leftSubtreeSize != 0) {
            // Recurse to left subtree
            computePostorder(preorder, inorder, postorder, preorderStartIndex + 1, inorderStartIndex,
                    firstNodeIndex);
        }
        if (firstNodeIndex != endIndex - 1) {
            // Recurse to right subtree
            computePostorder(preorder, inorder, postorder, preorderStartIndex + leftSubtreeSize + 1,
                    firstNodeIndex + 1, endIndex);
        }
        postorder.add(firstNode);
    }

    private static int findNodeIndex(int[] inorder, int inorderStartIndex, int endIndex, int node) {
        for (int index = inorderStartIndex; index < endIndex; index++) {
            if (inorder[index] == node) {
                return index;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] preorder = { 1, 2, 4, 5, 3, 6 };
        int[] inorder = { 4, 2, 5, 1, 3, 6 };
        List<Integer> postorder = computePostorder(preorder, inorder);

        System.out.print("Postorder: ");
        for (int node : postorder) {
            System.out.print(node + " ");
        }
        System.out.println("\nExpected:  4 5 2 6 3 1");
    }
}
