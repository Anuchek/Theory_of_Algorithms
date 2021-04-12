package io.github.anuchek.service;

import io.github.anuchek.btree.BTree;

import java.util.ArrayList;
import java.util.Locale;

public class DataProcessor {
    BTree tree;
    int[] randomArray;
    int[] orderedArray;

    public DataProcessor(BTree tree, int[] randomArray, int[] orderedArray) {
        this.tree = tree;
        this.randomArray = randomArray;
        this.orderedArray = orderedArray;
    }

    public void testInsertionOnRandomArray() {
        long start = System.nanoTime();
        for (int i : randomArray) {
            tree.insert(i);
        }
        long finish = System.nanoTime();
        System.out.println("Insertion(random array): " + String.format(Locale.CANADA_FRENCH, "%,d", (finish-start)) + "ns");
    }

    public void testInsertionOnOrderedArray() {
        long start = System.nanoTime();
        for (int i : orderedArray) {
            tree.insert(i);
        }
        long finish = System.nanoTime();
        System.out.println("Insertion(ordered array): " + String.format(Locale.CANADA_FRENCH, "%,d", (finish-start)) + "ns");
    }

    public void testSearch() {
        long start = System.nanoTime();
        for (int i : randomArray) {
            tree.search(i);
        }
        long finish = System.nanoTime();
        System.out.println("Searching: " + String.format(Locale.CANADA_FRENCH, "%,d", (finish-start)) + "ns");
    }

    public void testRemove() {
        long start = System.nanoTime();
        for (int i : randomArray) {
            tree.remove(i);
        }
        long finish = System.nanoTime();
        System.out.println("Removing: " + String.format(Locale.CANADA_FRENCH, "%,d", (finish-start)) + "ns");

    }

    public void testBalancingOnRandomArray(int BTreeDegree) {
        BTree bTree = new BTree(BTreeDegree);
        for (int i : randomArray) {
            bTree.insert(i);
        }
        System.out.println("Average balancing(random array): " + String.format(Locale.CANADA_FRENCH, "%,d", bTree.getAverageTimeOfBalancing()) + "ns");
    }

    public void testBalancingOnOrderedArray(int BTreeDegree) {
        BTree.measurements = new ArrayList<>(1_000_000);
        BTree bTree = new BTree(BTreeDegree);
        for (int i : orderedArray) {
            bTree.insert(i);
        }
        System.out.println("Average balancing(ordered array): " + String.format(Locale.CANADA_FRENCH, "%,d", bTree.getAverageTimeOfBalancing()) + "ns");
    }
}
