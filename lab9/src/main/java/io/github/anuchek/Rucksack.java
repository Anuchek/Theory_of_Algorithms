package io.github.anuchek;

import java.util.Arrays;
import java.util.Comparator;

public class Rucksack {
    private final int capacity;
    private double currentValue = 0.0;

    public void rucksackGreedyAlgorithmMaxValue(Item[] items) {
        int currentItem = 0 ;
        int currentWeight = 0 ;
        Arrays.sort(items, Comparator.comparingDouble(Item::valuePerSize).reversed());
        while (currentItem < items.length && currentWeight < capacity) {
            if (currentWeight + items[currentItem].getSize() < capacity) {
                //берем обьект целиком
                currentValue += items[currentItem].getValue();
                currentWeight += items[currentItem].getSize();
            } else {
                // берем частично
                double i = ( (capacity - currentWeight) / (double) items[currentItem].getSize() );
                currentValue += i * items[currentItem].getValue();
                currentWeight = capacity; // full rucksack

            }
            currentItem++;
        }
        //System.out.println(itemsInRucksack.toString());
        System.out.println("Max optimal value: " + currentValue);
    }

    public void rucksackGreedyAlgorithmMaxAmountOfItems(Item[] items){
        int currentItem = 0;
        int currentWeight = 0;
        Arrays.sort(items, Comparator.comparingDouble(Item::getSize));
        //System.out.println(Arrays.toString(items));
        while (currentItem < items.length && currentWeight < capacity && currentWeight + items[currentItem].getSize() <= capacity){
                currentWeight += items[currentItem].getSize();
                currentItem++;
        }
        System.out.println("Max amount of items: " + currentItem);
    }

    public Rucksack(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

}
