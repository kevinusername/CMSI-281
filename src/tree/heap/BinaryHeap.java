package tree.heap;

import java.util.ArrayList;

class BinaryHeap {

    ArrayList<Integer> heap;

    BinaryHeap() {
        heap = new ArrayList<Integer>();
    }

    /*
     * Continues to bubble values up the tree until we
     * find a node that is greater than it
     */
    public void bubbleUp(int index) {
        if (index == 0) {return;}

        int parent = getParent(index);

        if (heap.get(parent) < heap.get(index)) {
            Integer temp = heap.get(index);
            heap.set(index, heap.get(parent));
            heap.set(parent, temp);
            bubbleUp(parent);
        }
    }

    public void insert(Integer toInsert) {
        heap.add(toInsert);
        bubbleUp(heap.size() - 1);
    }

    // Traversal helpers
    public int getParent(int index) {
        return (index - 1) / 2;
    }

    public int getChild(int index, char child) {
        int result = (index * 2) + 1;
        if (child == 'R') {
            result++;
        }
        return result;
    }

    public void print() {
        for (int i = 0; i < heap.size(); i++) {
            System.out.println(heap.get(i));
        }
    }

    public ArrayList<Integer> getSortedElements() {
        int count = heap.size();
        BinaryHeap cloneHeap = new BinaryHeap();
        cloneHeap.heap = new ArrayList<>(heap);
        while (count > 1) {
            int tempRoot = cloneHeap.heap.get(0);
            cloneHeap.heap.set(0, cloneHeap.heap.get(count - 1));
            cloneHeap.heap.set(count - 1, tempRoot);
            for (int i = 1; i < count - 1; i++) {
                cloneHeap.bubbleUp(i);
            }
            count--;
        }
        return cloneHeap.heap;
    }

    public static void main(String[] args) {
        BinaryHeap testHeap = new BinaryHeap();
        testHeap.insert(50);
        testHeap.insert(25);
        testHeap.insert(20);
        testHeap.insert(8);

        BinaryHeap testHeap2 = new BinaryHeap();
        testHeap2.insert(101);
        testHeap2.insert(51);
        testHeap2.insert(235);
        testHeap2.insert(32);
        testHeap2.insert(1);
        testHeap2.insert(534);
        testHeap2.insert(65);

        System.out.println(testHeap.getSortedElements().toString());
        System.out.println(testHeap2.getSortedElements().toString());
    }
}