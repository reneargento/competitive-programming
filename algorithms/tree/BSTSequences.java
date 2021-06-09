package algorithms.tree;

import datastructures.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 25/05/19.
 */
// Computes all possible insertion orders to build a binary search tree
public class BSTSequences {

    // O(2^n) runtime, where n is the number of nodes
    // O(2^n) space
    private static List<LinkedList<Integer>> getBSTSequences(TreeNode<Integer> node) {
        List<LinkedList<Integer>> allSequences = new ArrayList<>();

        // Base case
        if (node == null) {
            allSequences.add(new LinkedList<>());
            return allSequences;
        }

        LinkedList<Integer> prefix = new LinkedList<>();
        prefix.add(node.value);

        // Generate left and right sequences
        List<LinkedList<Integer>> leftSubtreeSequences = getBSTSequences(node.left);
        List<LinkedList<Integer>> rightSubtreeSequences = getBSTSequences(node.right);

        for (LinkedList<Integer> leftSubtreeSequence : leftSubtreeSequences) {
            for (LinkedList<Integer> rightSubtreeSequence : rightSubtreeSequences) {
                List<LinkedList<Integer>> weavedSequences = new ArrayList<>();
                weaveSequences(prefix, leftSubtreeSequence, rightSubtreeSequence, weavedSequences);
                allSequences.addAll(weavedSequences);
            }
        }

        return allSequences;
    }

    private static void weaveSequences(LinkedList<Integer> prefix, LinkedList<Integer> sequence1,
                                       LinkedList<Integer> sequence2, List<LinkedList<Integer>> weavedSequences) {
        // Base case: If any sequence is empty
        if (sequence1.isEmpty() || sequence2.isEmpty()) {
            LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
            result.addAll(sequence1);
            result.addAll(sequence2);
            weavedSequences.add(result);
            return;
        }

        // Move first element of sequence 1 to the end of prefix, recurse, restore element
        int sequence1Head = sequence1.removeFirst();
        prefix.addLast(sequence1Head);
        weaveSequences(prefix, sequence1, sequence2, weavedSequences);
        prefix.removeLast();
        sequence1.addFirst(sequence1Head);

        // Move first element of sequence 2 to the end of prefix, recurse, restore element
        int sequence2Head = sequence2.removeFirst();
        prefix.addLast(sequence2Head);
        weaveSequences(prefix, sequence1, sequence2, weavedSequences);
        prefix.removeLast();
        sequence2.addFirst(sequence2Head);
    }

    //         5
    //     3      7
    //   2  4   6
    public static void main(String[] args) {
        TreeNode<Integer> treeNode5 = new TreeNode<>(5);
        TreeNode<Integer> treeNode3 = new TreeNode<>(3);
        TreeNode<Integer> treeNode7 = new TreeNode<>(7);
        TreeNode<Integer> treeNode2 = new TreeNode<>(2);
        TreeNode<Integer> treeNode4 = new TreeNode<>(4);
        TreeNode<Integer> treeNode6 = new TreeNode<>(6);

        treeNode5.left = treeNode3;
        treeNode5.right = treeNode7;
        treeNode3.left = treeNode2;
        treeNode3.right = treeNode4;
        treeNode7.left = treeNode6;

        List<LinkedList<Integer>> bstSequences = getBSTSequences(treeNode5);

        System.out.println("Sequences");
        for (List<Integer> sequence : bstSequences) {
            StringJoiner sequenceValues = new StringJoiner(" ");

            for (int value : sequence) {
                sequenceValues.add(String.valueOf(value));
            }
            System.out.println(sequenceValues);
        }
    }

}
