package io.github.anuchek;

import io.github.anuchek.btree.BTree;
import io.github.anuchek.service.CreateOrderedArray;
import io.github.anuchek.service.CreateRandomArray;
import io.github.anuchek.service.DataProcessor;

public class Main {

    public static void main(String[] args) {

        int[] random = CreateRandomArray.create(100_000);
        int[] consistent = CreateOrderedArray.create(100_000);

        DataProcessor btree = new DataProcessor(new BTree(5), random, consistent);

        System.out.println("\n==============B-tree==============");
        btree.testInsertionOnOrderedArray();
        btree.testInsertionOnRandomArray();
        btree.testSearch();
        btree.testRemove();
    }
}
