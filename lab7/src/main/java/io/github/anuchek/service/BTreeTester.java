package io.github.anuchek.service;

import io.github.anuchek.btree.BTree;

public class BTreeTester {
    public static void test(int numberOfTests, int BTreeDegree, int NumberOfElements){
        int[] random = CreateRandomArray.create(NumberOfElements);
        int[] consistent = CreateOrderedArray.create(NumberOfElements);

        DataProcessor bTree = new DataProcessor(new BTree(BTreeDegree), random, consistent);
        System.out.println("\n==============B-tree==============");
        System.out.println("Number of elements in arrays: \n Random Array: " + NumberOfElements + "\n OrderedArray: " + NumberOfElements + "\n" );
        for (int i = 1; i < numberOfTests + 1; i++){
            System.out.println("Test №" + i);
            bTree.testInsertionOnOrderedArray();
            bTree.testInsertionOnRandomArray();
            bTree.testSearch();
            bTree.testRemove();
            bTree.testBalancingOnOrderedArray(BTreeDegree);
            bTree.testBalancingOnRandomArray(BTreeDegree);
            System.out.println();
        }
    }
}
