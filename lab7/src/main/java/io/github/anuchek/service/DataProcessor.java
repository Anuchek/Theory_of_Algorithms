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
            tree.containsBool(i);
        }
        long finish = System.nanoTime();
        System.out.println("Searching: " + String.format(Locale.CANADA_FRENCH, "%,d", (finish-start)) + "ns");
    }

    public void testRemove() {
        long start = System.nanoTime();
        for (int i : randomArray) {
            tree.delete(i);
        }
        long finish = System.nanoTime();
        System.out.println("Removing: " + String.format(Locale.CANADA_FRENCH, "%,d", (finish-start)) + "ns");

    }
}
