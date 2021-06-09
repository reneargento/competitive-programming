package datastructures.tree;

/**
 * Created by Rene Argento on 25/05/19.
 */
public class TreeNode<Value> {

    public Value value;
    public TreeNode<Value> left;
    public TreeNode<Value> right;

    public TreeNode(Value value) {
        this.value = value;
    }

}
