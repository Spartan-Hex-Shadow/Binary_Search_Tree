package proj2;

/**
 * @author Prabhdeep Singh
 * @project 2
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */

/**
 * A basic node implementation to contain a specific
 * value of type T. T must implement
 * Comparable, so that the BinarySearchTree
 * can add elements.
 * Every instance also houses the right and left nodes.
 * These will be used for traversing the tree.
 */
public class BinaryNode<T extends Comparable<? super T>> {

    private T value;                    // T value
    private BinaryNode<T> left;         // BinaryNode left
    private BinaryNode<T> right;        // BinaryNode right

    /**
     * Constructs a new BinaryNode with the current value.
     *
     * @param value The value for this node to hold.
     */

    public BinaryNode(final T value) {
        this.value = value;             // Initializes object to argument
    }

    /**
     * Reassigns the value of the left node. This node will be
     * less than the current one when compared.
     *
     * @param node  The node to set to the left branch.
     */

    public void setLeft(final BinaryNode<T> node) {
        this.left = node;
    }  // Reassigns the value of the left node

    /**
     * Reassigns the value of the right node. This node will be
     * greater than the current one when compared.
     *
     * @param node  The node to set to the right branch.
     */

    public void setRight(final BinaryNode<T> node) {
        this.right = node;
    } // Reassigns the value of the right node

    /**
     * Reassigns the value this node holds.
     *
     * @param value The new value for this node.
     */

    protected void setValue(final T value){
        this.value = value;
    }         // Reassigns the value that the node holds

    public BinaryNode<T> getLeft() {
        return left;
    }                        // Returns left BinaryNode

    public BinaryNode<T> getRight() {
        return right;
    }                      // Returns right BinaryNode

    public T getValue() {
        return value;
    }                                  // Returns the value

    /**
     * Calls upon the underlying value to get the String
     * value of this node.
     *
     * @return  A String representation of this node.
     */

    public String toString() {
        return value.toString();                                           // Returns the toString of the value
    }
}
