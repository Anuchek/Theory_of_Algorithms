package io.github.anuchek.btree;

import java.util.ArrayList;

public class BTree implements Tree {
    Node root;
    int MinDeg;
    public static ArrayList<Long> measurements = new ArrayList<>(10_000);

    // Constructor
    public BTree(int deg) {
        this.root = null;
        this.MinDeg = deg;
    }

    public void display() {
        if (root != null) {
            root.traverse();
        }
    }

    // Function to find key
    public Node search(int key) {
        return root == null ? null : root.search(key);
    }

    public void insert(int key) {

        if (root == null) {

            root = new Node(MinDeg, true);
            root.keys[0] = key;
            root.num = 1;
        } else {
            // When the root node is full, the tree will grow high
            if (root.num == 2 * MinDeg - 1) {
                Node s = new Node(MinDeg, false);
                // The old root node becomes a child of the new root node
                s.children[0] = root;
                // Separate the old root node and give a key to the new node
                s.splitChild(0, root);
                // The new root node has 2 child nodes. Move the old one over there
                int i = 0;
                if (s.keys[0] < key) {
                    i++;
                }
                s.children[i].insertNotFull(key);
                root = s;
            } else
                root.insertNotFull(key);
        }
    }

    public void remove(int key) {
        if (root == null) {
            System.out.println("The tree is empty");
            return;
        }
        root.remove(key);

        if (root.num == 0) { // If the root node has 0 keys
            // If it has a child, its first child is taken as the new root,
            // Otherwise, set the root node to null
            if (root.isLeaf)
                root = null;
            else
                root = root.children[0];
        }
    }

    public long getAverageTimeOfBalancing() {
        long sum = 0;
        for (long i : measurements) {
            sum += i;
        }
        return sum / measurements.size();
    }
}