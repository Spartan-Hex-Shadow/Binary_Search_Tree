package proj2;

/**
 * @author Prabhdeep Singh
 * @project 2
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */

/**
 * This class is used to store
 * data in a way, such that searching of the
 * nodes can be achieved in O(nlog(n))
 * time, compared to the regular O(n)
 * time complexity.
 *
 * The data added is stored into a seperate node
 * system, denoted by BinaryNode. This
 * will handle the left and right nodes, making
 * use of recursive functions.
 */

public class BinarySearchTree<T extends Comparable<? super T>> {

    private BinaryNode<T> root;            // instance variable of BinaryNode
    private int size = 0;                  // sets the size to 0

    /**
     * Retrieves the root of this tree. The root
     * will only ever be null if the tree is empty.
     *
     * @return The root of the tree.
     */

    protected BinaryNode<T> head() {
        return root;                        // returns the root
    }

    /**
     * Checks to see if there exists a node in
     * this tree that contains the given value.
     * Note, that no null values will be stored
     * in this tree.
     *
     * @param t The value to search for.
     * @return true if the value was
     *         found in the tree.
     */

    public boolean contains(final T t) {
        return equivalentTo(t) != null;     // returns a boolean if the value is or is not found in the tree
    }

    /**
     * Used to check if there exists a value in
     * the tree with equivalent values and if
     * one exists, it is returned. This is more
     * useful if you need to modify the value
     * and the only reference is held inside
     * the tree.
     *
     * @param t The value to match.
     * @return The value if it is found,
     *         otherwise, null
     */

    public T equivalentTo(final T t) {
        if (t == null || root == null) {     // Checks to see if the value or the root is null
            return null;                     // then returns null
        }
        return equivalentFromRoot(root, t);  // else returns if the value is found
    }

    /**
     * Recursively tries to find a value that
     * when compared to the given value, returns
     * a comparison of 0. If this is true, it
     * is seen that they are equivalent.
     *
     * @param node The node to start searching
     *             from.
     * @param t    The value to look for.
     * @return The matching value.
     */

    private T equivalentFromRoot(final BinaryNode<T> node, final T t) {
        if (node == null) {                   // If the node is null
            return null;                      // we didn't find it
        }
        final int comp = t.compareTo(node.getValue());
        if (comp == 0) {
            return node.getValue();           // Found the node we want
        } else if (comp > 0) {
            return equivalentFromRoot(node.getRight(), t);
        } else {
            return equivalentFromRoot(node.getLeft(), t);
        }
    }

    /**
     * This functions adds a new value to the
     * tree after finding the correct placement
     * for them. Note, that this will ensure
     * that the tree does not contain any
     * duplicates.
     *
     * @param t     The value to add to the tree.
     */

    public void insert(final T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        final BinaryNode<T> node = new BinaryNode<>(t);
        if (root == null) {
            root = node;
            size++;

        }
        else if(insert(node, root)){
            size++;

        }
    }

    /**
     * Recursively tries to add the node to the
     * tree. Given a defined root, it will
     * attempt to find the correct location.
     *
     * @param node  The node to add.
     * @param root  The root to search from.
     * @return      true if the node
     *              is not a duplicate.
     */

    private boolean insert(final BinaryNode<T> node, final BinaryNode<T> root) {
        final int comparator = node.getValue().compareTo(root.getValue());
        if (comparator > 0) {                       // Correct location is less than the current location
            if (root.getRight() == null) {
                root.setRight(node);
                return true;
            } else {
                return insert(node, root.getRight());
            }
        } else if (comparator < 0) {               // Correct location is greater than the current location
            if (root.getLeft() == null) {
                root.setLeft(node);
                return true;
            } else {
                return insert(node, root.getLeft());
            }
        }
        return false; // Fall-through to avoid the addition of repeating elements.
    }

    /**
     * Removes a node and if needed finds the
     * next best element to replace it.
     *
     * @param t     The value to remove from the
     *              tree.
     * @return      The value removed.
     */

    public T remove(final T t) {
        if (size <= 0) {
            throw new IndexOutOfBoundsException("Tree is empty");
        }
        final T value = root.getValue();
        root = remove(t, root);
        size--;
        return value;
    }

    /**
     * Recursively tries to remove the value from
     * the tree. If no value is found, it will
     * ensure that the size does not drop 0. This
     * is used, due to the fact that the recursive
     * sequence cannot accurately detect when it
     * failed to remove one.
     *
     *
     * @param t     The value to attempt to remove.
     * @param root  The node to start searching from.
     * @return      The BinaryNode that
     *              contained the value.
     */

    private BinaryNode<T> remove(final T t, final BinaryNode<T> root) {
        if (root == null) {
            size++;  // Not found. Resize to maintain size
            return null;
        }
        if (t == null) {
            return null;
        }
        final int comparator = t.compareTo(root.getValue());
        if (comparator < 0) {
            root.setLeft(remove(t, root.getLeft()));         // Node we want is to the left
        } else if (comparator > 0) {
            root.setLeft(remove(t, root.getLeft()));         // Node we want is to the right
        } else if (root.getLeft() != null && root.getRight() != null) {
            root.setValue(findMin(root.getRight()).getValue());          // Find the best replacement
            root.setRight(remove(root.getValue(), root.getRight()));     // which is the lowest value on the
                                                                         // right subtree
        } else {
            if (root.getLeft() != null) {
                return root.getLeft();
            } else {
                return root.getRight();
            }
        }
        return root;
    }

    /**
     * Returns the next best replacement for the
     * removal of the tree, if both sub-nodes
     * of the removed node are non-null.
     *
     * @param t     The node to find a replacement.
     * @return      The next best node.
     */

    private BinaryNode<T> findMin(BinaryNode<T> t) {
        if (t == null) {
            return null;
        } else if (t.getLeft() == null) {
            return t;
        }
        return findMin(t.getLeft()); // Lowest is always to the left, to the left
    }

    /**
     * Searches for a common ancestor. Note that
     * is is given that a node will be an ancestor
     * of itself, otherwise there might be issues
     * if the root is one of the given values.
     *
     * @param t1    The first child.
     * @param t2    The second child.
     */

    public void findCommonAncestor(final T t1, final T t2) {
        final T equalT1 = equivalentTo(t1);  // Search to see if they exist in the tree first
        final T equalT2 = equivalentTo(t2);
        if(equalT1 == null || equalT2 == null){   // Checks to see if one of the given nodes is null
            System.out.println("One of the given nodes is not in this tree.");
            return;
        }
        System.out.println("The results of the common ancestor is: ");
        System.out.printf("%s and %s\n", equalT1, equalT2); // Prints the results

        T common = commonAncestorFrom(root, t1, t2); // Gaurenteed to be in the tree at this point
        System.out.println("is: " + common);
    }

    /**
     * Checks to see if the given node immediately
     * contains the children node.
     *
     * @param root  The given node.
     * @param t1    The first child.
     * @param t2    The second child.
     * @return      The common ancestor, if found.
     */

    private T commonAncestorFrom(final BinaryNode<T> root, final T t1, final T t2) {
        if (root == null) {                                          // if root is null
            return null;                                             // returns null
        }
        final int comp1 = t1.compareTo(root.getValue());
        final int comp2 = t2.compareTo(root.getValue());
        final int sign = comp1 * comp2; // We just need the sign.
        if (sign <= 0) { // The children separate into sub trees here
            return root.getValue(); // returns the values
        } else {
            if (comp1 > 0) {
                return commonAncestorFrom(root.getRight(), t1, t2);  // LCA greater than current node
            } else {
                return commonAncestorFrom(root.getLeft(), t1, t2);   // LCA less than current node
            }
        }
    }

    public int size() {
        return size;
    }                             // Returns the size

    public boolean isEmpty() {
        return size != 0;
    }                 // Checks the size

    /**
     * Performs an in-order printTree of the tree.
     */

    public void printTree() {
        printTree(root);
    }                       // Prints the tree

    /**
     * Prints out the root value.
     */

    public void printRoot(){ System.out.println(root);}          // Prints the root

    /**
     * Recursively prints the tree given a beginning
     * node using the in-order traversal.
     *
     * @param node  The starting node.
     */

    private void printTree(final BinaryNode<T> node) {
        if (node == null) {
            return;
        }
        printTree(node.getLeft());    // Prints the left part of the node
        System.out.println(node); // Prints the node
        printTree(node.getRight());   // Prints the right part of the node
    }

    /**
     * Prints out information of this tree. This will
     * be including the root and the amount of nodes
     * in a format such as:
     * "This tree starts with root and has 10 nodes"
     * if the tree is non-empty, otherwise:
     * "This tree starts with has no nodes".
     * Format and spelling was provided per program
     * requirements.
     *
     * @return  The string representation of this tree.
     */

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder(65);
        result.append("This tree starts with ");             // Common to all prints
        result.append(root == null ? "" : root + " and ");   // lets make sure we get the right output
        result.append("has ");
        result.append(size == 0 ? "no" : size);              // Replace general size with 'no'
        result.append(" nodes");
        return result.toString();
    }

}
