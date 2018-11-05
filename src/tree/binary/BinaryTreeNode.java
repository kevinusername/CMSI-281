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
        doubleTree(n.left);
        doubleTree(n.right);
        BinaryTreeNode oldLeft = n.left;
        n.left = new BinaryTreeNode(n.data);
        n.left.left = oldLeft;
    }

    public static boolean sameTree(BinaryTreeNode n1, BinaryTreeNode n2) {
        if (n1 == null || n2 == null) { return n1 == n2; }
        if (!n1.data.equals(n2.data)) { return false; }
        return sameTree(n1.left, n2.left) && sameTree(n1.right, n2.right);
    }

    public static void main(String[] args) {
        BinaryTreeNode testTree1 = new BinaryTreeNode("2");
        testTree1.left = new BinaryTreeNode("1");
        testTree1.right = new BinaryTreeNode("3");

        // What doubleTree(testTree1) should look like
        BinaryTreeNode testTree2 = new BinaryTreeNode("2");
        testTree2.left = new BinaryTreeNode("2");
        testTree2.left.left = new BinaryTreeNode("1");
        testTree2.left.left.left = new BinaryTreeNode("1");
        testTree2.right = new BinaryTreeNode("3");
        testTree2.right.left = new BinaryTreeNode("3");

        System.out.println(sameTree(testTree1, testTree2));

        testTree1.doubleTree(testTree1);

        System.out.println(sameTree(testTree2, testTree2));
        System.out.println(sameTree(testTree1, testTree1));
        System.out.println(sameTree(testTree1, testTree2));

        testTree1.doubleTree(testTree1);
        testTree2.doubleTree(testTree2);
        System.out.println(sameTree(testTree1, testTree2));

        testTree1.doubleTree(testTree1);
        testTree2.doubleTree(testTree2);
        System.out.println(sameTree(testTree1, testTree2));
    }
}