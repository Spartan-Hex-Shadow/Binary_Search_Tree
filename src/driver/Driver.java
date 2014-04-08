package driver;

import proj2.*;

public class Driver {

    public static void main(String[] args)
    {
        HashedBSTs <Node> test1 = new HashedBSTs<Node>(26);

        test1.fileReader("input1.txt");

        test1.printHashCountResults();

        // retrieve all T's (index 19)
        BinarySearchTree <Node> sample = test1.retreiveHashedBSTat(19);

        sample.printTree();

        // answer SHOULD be "tag" with input1.txt
        sample.findCommonAncestor(new Node("This"), new Node("than"));

        test1.findAll(sample, new Node("the"));

    }

}