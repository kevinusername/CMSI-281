package tree.binary;

public class BinaryTreeNode {

    private String data;
    private BinaryTreeNode left, right;

    BinaryTreeNode(String s) {
        data = s;
        left = null;
        right = null;
    }

    public void add(String s, String child) {
        if (child.equals("L")) {
            left = new BinaryTreeNode(s);
        } else if (child.equals("R")) {
            right = new BinaryTreeNode(s);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public BinaryTreeNode getChild(String child) {
        return (child.equals("L")) ? left : right;
    }

    public String getString() {
        return data;
    }

    public void doubleTree(BinaryTreeNode n) {
        if (n == null) { return; }
        doubleTree(n.getChild("L"));
        doubleTree(n.getChild("R"));

        BinaryTreeNode oldLeft = n.left;
        n.left = new BinaryTreeNode(n.data);
        n.left.left = oldLeft;

    }

    public static boolean sameTree(BinaryTreeNode n1, BinaryTreeNode n2) {
        throw new UnsupportedOperationException();
    }

}