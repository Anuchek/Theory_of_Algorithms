package io.github.anuchek.service;

public class CreateRandomArray {

    public static int[] create(int length) {

        int[] randomArray = new int[length];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = (int) (Math.random() * 1_000_000 - 50_000);
        }
        return randomArray;
    }

}