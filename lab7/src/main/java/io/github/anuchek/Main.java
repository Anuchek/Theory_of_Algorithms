package io.github.anuchek;

import io.github.anuchek.btree.BTree;

public class Main {

    public static void main(String[] args) {
        BTree bTree = new BTree(3);
        bTree.insert(8);
        bTree.insert(8);
        bTree.insert(9);
        bTree.insert(9);
        bTree.insert(10);
        bTree.insert(11);
        bTree.insert(11);
        bTree.insert(11);
        bTree.insert(15);
        bTree.insert(20);
        bTree.insert(20);
        bTree.insert(20);
        bTree.insert(20);
        bTree.insert(17);
        bTree.delete(20);

        bTree.display();
        System.out.println();
        bTree.display();
        bTree.containsStr(15);
        bTree.containsStr(220);

        if (bTree.containsBool(20)) {
            System.out.println("Yes");
        } else
            System.out.println("no");

    }
}
