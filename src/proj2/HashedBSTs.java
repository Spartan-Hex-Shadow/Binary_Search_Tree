package proj2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author Prabhdeep Singh
 * @project 2
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */

/**
 * This class is responsible for the construction,
 * modification and indexing of the trees.
 */
public class HashedBSTs<Ignored> {

    private final ArrayList<BinarySearchTree<Node>> table;

    /**
     * Constructs a new tree table, with a default given
     * size. The amount of trees can still exceed beyond
     * the size.
     *
     * @param size  The default size for the tree. This
     *              must be positive.
     */

    public HashedBSTs(final int size) {
        table = new ArrayList<>(size);                  // Constructs the table with the given size
        for (int i = 0; i < size; i++) {
            table.add(new BinarySearchTree<Node>());
        }
    }

    /**
     * Reads a file and parses out the valid tokens.
     * This will only return alphabetical characters
     * when parsed. Separators, such as hyphens or
     * apostrophes will also be removed without breaking
     * apart the word.
     *
     * @param filename  The file to read.
     */

    public void fileReader(final String filename) {
        try {
            final File file = new File(filename);
            if(!file.exists() || !file.canRead()){          // Checks to see existence of file
                System.err.println("Cannot access file.");
                return;
            }
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String next;
            while ((next = reader.readLine()) != null) {
                next = next.replaceAll("[^a-zA-Z\\s]", ""); // Clear everything we don't need
                final String[] tokens = next.split("\\s+"); // Split on whitespace
                for (final String str : tokens) {
                    if (str.isEmpty()) {
                        continue;
                    }

                    final BinarySearchTree<Node> tree = treeFor(str.charAt(0));
                    final Node n = new Node(str);
                    final Node search = tree.equivalentTo(n);

                    if (search != null) {            // If found, this node has already been added
                        search.incrementFrequency(); // so let's increment the count on it
                    } else {
                        tree.insert(n);              // otherwise we will add it
                    }
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the tree to fit the given character.
     *
     * @param c     The tree for this character,
     *              if the character is valid,
     *              otherwise an
     *              IllegalArgumentException
     *              is thrown.
     * @return      The tree for the character.
     */

    private BinarySearchTree<Node> treeFor(final char c) {
        if (Character.isAlphabetic(c)) {
            return retreiveHashedBSTat(Character.toLowerCase(c) - 'a'); // This depends on that fact that all the
                                                                        // trees, for each character, were already added
        }
        throw new IllegalArgumentException("Character " + c + " does not have a valid map.");
    }

    /**
     * Retrieves the  BinarySearchTree at
     * the valid index. The index is bounded by
     * [0, 26]. If the given index
     * exceeds these bounds, an
     * IndexOutOfBoundsException is thrown.
     *
     * @param index     The index of the tree to get.
     * @return          The tree, if the given index
     *                  is valid.
     */

    public BinarySearchTree<Node> retreiveHashedBSTat(final int index) {
        if (index < 0 || index > table.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size: " + table.size());
        }
        return table.get(index);
    }

    /**
     * Iterates over the trees.
     */

    public void printHashCountResults() {
        for (final BinarySearchTree tree : table) {
            System.out.println(tree.toString());
        }
    }

    /**
     * Finds all the nodes that start with
     * the given Node in the given
     * tree.
     *
     * @param search    The tree to search for
     *                  the nodes that start
     *                  with the given
     *                  node.
     * @param node      The node to use for
     *                  comparison.
     *
     */

    public void findAll(final BinarySearchTree<Node> search, final Node node) {
        if(node == null || search == null ||  search.size() == 0){
            throw new IllegalArgumentException("Invalid arguments passed");
        }
        System.out.printf("Printing the results of nodes that startWith: '%s'\n", node.getWord());
        findStartMatches(search.head(), node);
    }

    /**
     * This method iterates the tree recursively
     * using pre-order traversal.
     *
     * @param root  The starting root.
     * @param word  The node we are searching for.
     */

    private void findStartMatches(final BinaryNode<Node> root, final Node word){
        if(root == null){
            return;
        }
        // Traverse pre-fix order. Check the current node before
        // checking the left and then the right.

        if(root.getValue().getWord().toLowerCase().startsWith(word.getWord().toLowerCase())){
            System.out.println(root);
        }
        findStartMatches(root.getRight(), word);
        findStartMatches(root.getLeft(), word);
    }

}
