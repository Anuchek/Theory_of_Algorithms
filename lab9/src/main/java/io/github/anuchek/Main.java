package io.github.anuchek;

public class Main {
    public static void main(String[] args) {
        final Item item1 = new Item("item 1",25, 5);
        final Item item2 = new Item("item 2",12, 4);
        final Item item3 = new Item("item 3",3, 3);
        final Item item4 = new Item("item 4",32, 2);
        final Item[]items = {item1, item2, item3, item4};

        Rucksack rucksack1 = new Rucksack(0);
        rucksack1.rucksackGreedyAlgorithmMaxValue(items);
        rucksack1.rucksackGreedyAlgorithmMaxAmountOfItems(items);
    }
}
