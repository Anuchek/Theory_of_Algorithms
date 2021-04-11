package io.github.anuchek.service;

public class CreateOrderedArray {

    public static int[] create(int length) {

        int[] consistentArray = new int[length];
        for (int i = 0; i < consistentArray.length; i++) {
            consistentArray[i] = i;
        }
        return consistentArray;
    }

}
