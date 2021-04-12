package io.github.anuchek.service;

import io.github.anuchek.btree.BTree;

public class BTreeTester {
    public static void test(int numberOfTests, int BTreeDegree){
        int[] random = CreateRandomArray.create(100_000);
        int[] consistent = CreateOrderedArray.create(100_000);

        DataProcessor bTree = new DataProcessor(new BTree(BTreeDegree), random, consistent);
        System.out.println("\n==============B-tree==============");
        for (int i = 0; i < numberOfTests; i++){
            System.out.println("Test №" + i);
            bTree.testInsertionOnOrderedArray();
            bTree.testInsertionOnRandomArray();
            bTree.testSearch();
            bTree.testRemove();
            bTree.testBalancingOnOrderedArray(BTreeDegree);
            bTree.testBalancingOnRandomArray(BTreeDegree);
        }
    }
}
